package org.erc.netflix.export;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Configuration object
 */
public class Config {
   
    private Properties properties;

    /**
     * 
     */
    public Config(){
        properties = new Properties();
        try(Reader reader = Files.newBufferedReader(Paths.get("config.properties"))){
            properties.load(reader);
        } catch (IOException e) {
            properties = null;
        }
        
    }

    /**
     * @param key
     * @param defValue
     * @return
     */
    public String get(String key,String defValue){
        if(properties!=null){
            return properties.getProperty(key, defValue);
        }
        return defValue;
    }
}
