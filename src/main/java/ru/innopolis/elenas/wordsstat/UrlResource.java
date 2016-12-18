package ru.innopolis.elenas.wordsstat;

import jdk.nashorn.api.scripting.URLReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

/**
 * Object represents url resources
 */
public class UrlResource implements Resource {
    private static final Logger log = LoggerFactory.getLogger(UrlResource.class);

    private URL resourceUrl;

    public UrlResource(URL resourceUrl){
        this.resourceUrl = resourceUrl;
    }

    @Override
    public String getResourceAsString(){

        StringBuilder stringBuilder = new StringBuilder();

        try {
            try (BufferedReader bufferedReader = new BufferedReader(new URLReader(resourceUrl))) {

                String line = bufferedReader.readLine();

                while (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
            }
            } catch (IOException e) {
            log.error(e.getMessage(), e);
            }
        return stringBuilder.toString();
    }

    @Override
    public String getSource() {
        return resourceUrl.getPath();
    }
}
