package org.utexas.ida.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*******
 * Author Brent Porter 2023
 * <a href="https://stackoverflow.com/questions/8285595/reading-properties-file-in-java">...</a>
 ********/

public class ReadInProperties {

    public String[] getProperties(){
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("conf/upstreamUpload.properties"));
            String username = (String) prop.get("username");
            String password = (String) prop.get("password");
            String bucket = (String) prop.get("bucket");
            return new String[]{username, password, bucket};
        } catch (IOException e) {
            System.out.println("Could not find the properties file");
            return new String[]{"Could not find the properties file"};
        }
    }
}
