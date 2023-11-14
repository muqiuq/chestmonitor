package ch.uisa.minecraft.chestmonitor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

public class Config {




    public static void Load() throws IOException {
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("plugins/chestmonitor.properties")) {

            if (input == null) {
                Global.logger.log(Level.SEVERE, "Sorry, unable to find chestmonitor.properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out



        } catch (IOException ex) {
            Global.logger.log(Level.SEVERE, "Could not load config file", ex);
            throw ex;
        }
    }





}
