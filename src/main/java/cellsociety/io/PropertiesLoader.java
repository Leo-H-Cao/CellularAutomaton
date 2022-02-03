package cellsociety.io;

import cellsociety.game.Game;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Loads in constants from the properties file
 * Constants can be accessed through public static HashMap
 *
 * @author Leo Cao
 */
public class PropertiesLoader {

    public static final String EXCEPTION_MESSAGE = "property file '%s' not found in the classpath";

    public static HashMap<String, String> properties = new HashMap<>();

    public void getPropValues(String propFileName) throws IOException {
        InputStream inputStream = null;

        try {
            Properties prop = new Properties();

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException(String.format(EXCEPTION_MESSAGE, propFileName));
            }

            for(Entry<Object, Object> entry: prop.entrySet()){
                properties.put((String) entry.getKey(), (String) entry.getValue());
            }

//            String result = "fireP = " + fireP + ", fireF = " + fireF + ", fIdeal = " + fIdeal;
//            System.out.println(result);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            inputStream.close();
        }
    }
}
