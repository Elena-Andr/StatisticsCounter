package ru.innopolis.elenas.wordsstat;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;

/**
 * Object represents file resources
 */
public class FileResource implements Resource {
    private static final Logger log = LoggerFactory.getLogger(FileResource.class);

    private String textFilePath;

    /**
     * Constructs the FileResource object
     * @param textFilePath - path to the input file resource
     */
    public FileResource(String textFilePath) {
        this.textFilePath = textFilePath;
    }

    /**
     *
     * @return
     */
    @Override
    public String getResourceAsString(){
        log.info("Processing text file : {}", textFilePath);
        StringBuilder stringBuilder = new StringBuilder();

        try {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFilePath))) {

                String line = bufferedReader.readLine();

                while (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
            }
        }catch (IOException e) {
                log.error(e.getMessage(), e);
        }

        return stringBuilder.toString();
    }

    @Override
    public String getSource() {
        return textFilePath;
    }
}
