package main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public interface PropertiesLoader {
    static Properties getProperties() {
        String path ="";
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(path);
        if (path.contains("ant")){
            path += "\\..\\out\\ascii\\";
        } else {
            path += "\\src\\locale\\";
        }
        System.out.println(path);
        File file = new File(path);
        URL[] urls = new URL[0];
        try {
            urls = new URL[]{file.toURI().toURL()};
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ClassLoader loader = new URLClassLoader(urls);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("russian", Locale.getDefault(), loader);

        Properties properties = new Properties();
        boolean isPathChanged = false;
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("META-INF/data.properties"));
        } catch (IOException | NullPointerException e) {
            isPathChanged = true;
            System.out.println(resourceBundle.getString("ChangedFilePath"));
        }
        if (isPathChanged) {
            try {
                 path = new File(".").getCanonicalPath();
                properties.load(new FileReader(path + "/../out/build/WEB-INF/classes/META-INF/data.properties"));
            } catch (IOException | NullPointerException e) {
                System.out.println(resourceBundle.getString("CannotAccessData"));
            }
        }
        return properties;
    }
}
