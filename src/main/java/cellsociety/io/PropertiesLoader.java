package cellsociety.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads in constants from the properties file
 * Constants can be accessed in a public static manner from this class
 *
 * @author Zack Schrage
 */
public class PropertiesLoader {

    public static double fireP;
    public static double fireF;
    public static double fIdeal;

    /**
     * Code attributed to:
     * https://crunchify.com/java-properties-file-how-to-read-config-properties-values-in-java/
     */
    public void readPropValues() throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            fireP = Double.parseDouble(prop.getProperty("fireP"));
            fireF = Double.parseDouble(prop.getProperty("fireF"));
            fIdeal = Double.parseDouble(prop.getProperty("fIdeal"));

        } catch (Exception e) {

        }

    }

}
