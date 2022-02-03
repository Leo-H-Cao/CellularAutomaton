package cellsociety.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads in constants from the properties file
 * Constants can be accessed in a public static manner from this class
 *
 * @author Leo Cao
 */
public class PropertiesLoader {

    public static double fireP;
    public static double fireF;
    public static double fIdeal;

    public static final String PROP_FILE_NAME = "config.properties";
    public static final String EXCEPTION_MESSAGE = "property file '" + PROP_FILE_NAME + "' not found in the classpath";

    public void getPropValues() throws IOException {
        InputStream inputStream = null;

        try {
            Properties prop = new Properties();

            inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException(EXCEPTION_MESSAGE);
            }

            fireP = Double.parseDouble(prop.getProperty("fireP"));
            fireF = Double.parseDouble(prop.getProperty("fireF"));
            fIdeal = Double.parseDouble(prop.getProperty("fIdeal"));

//            String result = "fireP = " + fireP + ", fireF = " + fireF + ", fIdeal = " + fIdeal;
//            System.out.println(result);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            if(inputStream !=null){
                inputStream.close();
            }
        }
    }
}
