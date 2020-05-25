package main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public interface PropertiesLoader {
    static Properties getProperties(){
        Properties properties = new Properties();
        boolean isPathChanged = false;
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("META-INF/data.properties"));
        } catch (IOException | NullPointerException e) {
            isPathChanged = true;
            System.out.println("Возможно, путь к файлам был изменён в следствие того, что вы используете Ant.");
        }
        if (isPathChanged) {
            try {
                String path = new File(".").getCanonicalPath();
                properties.load(new FileReader(path + "/../out/build/WEB-INF/classes/META-INF/data.properties"));
            } catch (IOException | NullPointerException e) {
                System.out.println("Невозможно получить доступ к данным.");
            }
        }
        return properties;
    }
}
