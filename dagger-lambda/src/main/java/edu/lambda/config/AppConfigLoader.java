package edu.lambda.config;

import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;


@Log4j2
public class AppConfigLoader {

    private static final List<ConfigSource> configSources = List.of(new FileConfigSource(), new SysEnvConfigSource());

    @SneakyThrows
    public static AppConfig get() {
        log.debug("Loading configuration");

        final var applicationProperties = new HashMap<String, String>();
        configSources.stream().map(ConfigSource::get).forEach(applicationProperties::putAll);

        final var config = new AppConfig();
        BeanUtils.populate(config, reloadProperties(applicationProperties));
        log.debug("Loaded configuration: {}", config);
        return config;
    }

    private static Map<String, String> reloadProperties(Map<String, String> applicationProperties) {
        return applicationProperties.entrySet().stream()
                .map(reloadProperty(applicationProperties))
                .collect(toMap(Entry::getKey, Entry::getValue));
    }

    private static Function<Entry<String, String>, Entry<String, String>> reloadProperty(Map<String, String> applicationProperties) {
        return propertyEntry -> {
            final var originalValue = propertyEntry.getValue();

            if (originalValue.contains("${")) {

                final var searchedPlaceholders = collectPlaceholders(originalValue);
                final var newValues = getValuesForPlaceholders(applicationProperties, searchedPlaceholders);
                final var newValue = rebuildValue(originalValue, searchedPlaceholders, newValues);

                return Map.entry(propertyEntry.getKey(), newValue);
            }
            return propertyEntry;
        };
    }

    private static String rebuildValue(String originalValue, String[] searchedPlaceholders, List<String> newValues) {
        var newValue = originalValue;

        for (int i = 0; i < searchedPlaceholders.length; i++) {
            newValue = StringUtils.replace(newValue, "${" + searchedPlaceholders[i] + "}", newValues.get(i));
        }
        return newValue;
    }

    private static List<String> getValuesForPlaceholders(Map<String, String> applicationProperties, String[] searchedPlaceholders) {
        return Arrays.stream(searchedPlaceholders)
                .map(searchedPlaceholder -> {
                    if (searchedPlaceholder.contains(":")) {
                        final var destructedKeys = searchedPlaceholder.split(":");
                        final var defaultValue = Optional.of(destructedKeys)
                                .filter(keys -> keys.length == 2)
                                .map(keys -> keys[1])
                                .orElse(searchedPlaceholder);

                        return applicationProperties.getOrDefault(destructedKeys[0], defaultValue);
                    }
                    return applicationProperties.getOrDefault(searchedPlaceholder, searchedPlaceholder);
                })
                .collect(Collectors.toList());
    }

    private static String[] collectPlaceholders(String originalValue) {
        return StringUtils.substringsBetween(originalValue, "${", "}");
    }

    interface ConfigSource {
        Map<String, String> get();
    }

    private static class FileConfigSource implements ConfigSource {
        private static final String CONFIG_FILE_PATH_PATTERN = "application-%s.properties";
        private static final String PROFILE_ENV_NAME = "ACTIVE_PROFILE";

        @Override
        public Map<String, String> get() {
            return Optional.ofNullable(System.getenv(PROFILE_ENV_NAME))
                    .map(this::toConfigFile)
                    .filter(File::exists)
                    .map(this::loadPropertiesFromFilePath)
                    .orElse(emptyMap());
        }

        private File toConfigFile(String appProfileName) {
            final var configFilePath = String.format(CONFIG_FILE_PATH_PATTERN, appProfileName);
            return new File(this.getClass().getClassLoader().getResource(configFilePath).getPath());
        }

        @SneakyThrows
        private Map<String, String> loadPropertiesFromFilePath(File configFile) {
            log.debug("Loading properties from config file {}", configFile.getPath());
            final var properties = new Properties();
            properties.load(new FileReader(configFile));
            return Maps.fromProperties(properties);
        }
    }

    private static class SysEnvConfigSource implements ConfigSource {
        @Override
        public Map<String, String> get() {
            log.debug("Loading properties from environment variables");
            return System.getenv();
        }
    }
}

