package utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Class for getting properties from e2e.properties file
 */
public class PropertiesManager {
    private static Map<String, Properties> runProps = new HashMap<String, Properties>();
    
    public static String get(String file, String parameter) {
        if (!runProps.containsKey(file)) {
            try {
                runProps.put(file, getProperties(file));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return runProps.get(file).getProperty(parameter);
    }
    
    private static Properties getProperties(String file) throws Exception {
        Properties prop = new Properties();
        prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(file));
        
        return prop;
    }
}
