package org.example;

import java.lang.reflect.Field;

public class Exercise3 {

    public static void ValidationEngine() throws IllegalAccessException {
        SystemConfig systemConfig = new SystemConfig(1,100);

        Field[] fields = systemConfig.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(RangeCheck.class)) {
                RangeCheck range = field.getAnnotation(RangeCheck.class);
                int min = range.min();
                int max = range.max();

                field.setAccessible(true);

                try {
                    int value = field.getInt(systemConfig);
                    if(field.getName().equals("maxThreads")){
                        if(value < min || value > max){
                            throw new ConfigValidationException("maxThreads Range Should be between 1 - 6");
                        }
                    }

                    if(field.getName().equals("timeoutSeconds")){
                        if(value < min || value > max){
                            throw new ConfigValidationException("TimeoutSeconds Range Should be 100 - 5000");
                        }
                    }

                } catch (ConfigValidationException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("maxThreads and TimeoutSeconds are in correct range");

    }
}
