package io.github.christechs.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.christechs.config.annotations.ConfigCategory;
import io.github.christechs.config.annotations.ConfigHidden;
import io.github.christechs.config.annotations.ConfigProperty;
import io.github.christechs.config.annotations.ConfigRange;
import io.github.christechs.psm.config.PSMConfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    public static final List<ConfigCategoryData> CATEGORIES = new ArrayList<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static File configFile;

    public static void init(File file) {
        configFile = file;
        load();
        buildUiModel();
    }

    public static void load() {
        if (configFile.exists() && configFile.length() > 0) {
            try (FileReader reader = new FileReader(configFile)) {
                PSMConfig loadedConfig = GSON.fromJson(reader, PSMConfig.class);
                if (loadedConfig != null) {
                    PSMConfig.INSTANCE = loadedConfig;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (PSMConfig.INSTANCE == null) {
            PSMConfig.INSTANCE = new PSMConfig();
        }
        save();
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(PSMConfig.INSTANCE, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void buildUiModel() {
        CATEGORIES.clear();
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        try {
            for (Field catField : PSMConfig.class.getDeclaredFields()) {
                if (!catField.isAnnotationPresent(ConfigCategory.class)) continue;

                catField.setAccessible(true);

                ConfigCategory ann = catField.getAnnotation(ConfigCategory.class);
                ConfigCategoryData categoryData = new ConfigCategoryData(catField.getName(), ann.name());

                Object categoryInstance = catField.get(PSMConfig.INSTANCE);

                for (Field propField : catField.getType().getDeclaredFields()) {
                    if (propField.isAnnotationPresent(ConfigHidden.class)) continue;
                    if (!propField.isAnnotationPresent(ConfigProperty.class)) continue;

                    propField.setAccessible(true);

                    ConfigProperty propAnn = propField.getAnnotation(ConfigProperty.class);
                    ConfigRange rangeAnn = propField.getAnnotation(ConfigRange.class);

                    MethodHandle getter = lookup.unreflectGetter(propField);
                    MethodHandle setter = lookup.unreflectSetter(propField);

                    ConfigOption<?> option = new ConfigOption<>(
                            propField.getName(),
                            propAnn.name(),
                            propAnn.description(),
                            () -> invokeGetter(getter, categoryInstance),
                            (val) -> invokeSetter(setter, categoryInstance, val)
                    );

                    if (rangeAnn != null) {
                        option.setBounds(rangeAnn.min(), rangeAnn.max());
                    }

                    categoryData.add(option);
                }
                CATEGORIES.add(categoryData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T invokeGetter(MethodHandle getter, Object instance) {
        try {
            return (T) getter.invoke(instance);
        } catch (Throwable t) {
            return null;
        }
    }

    private static <T> void invokeSetter(MethodHandle setter, Object instance, T value) {
        try {
            setter.invoke(instance, value);
            save();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}