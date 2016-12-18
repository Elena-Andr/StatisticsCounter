package ru.innopolis.elenas.wordsstat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CyrillicResourceParser implements ResourceParser {
    private static final Logger log = LoggerFactory.getLogger(CyrillicResourceParser.class);

    //punctuation mark and digits are allowed in the text
    private static final String REGEX_DELIMITER = "[\uFEFF]|\\s+|\\p{Punct}+|[0-9]+";
    private CyrillicValidator cyrillicValidator = new CyrillicValidator();

    @Override
    public String[] getItemsFromResource(StatisticsCounter statisticsCounter, Resource resource){

        List<String> words = new ArrayList<>();

        String resourceString = resource.getResourceAsString();
        String[] allWords = resourceString.split(REGEX_DELIMITER);
        List<String> allWordsList = new ArrayList<>(Arrays.asList(allWords));
        allWordsList.removeIf(""::equals);

        for(String wordCandidate : allWordsList){

            if(statisticsCounter.getCancelled()){
                break;
            }

            if(cyrillicValidator.validate(wordCandidate)){
                words.add(wordCandidate);
            }
            else {
                throw new IllegalArgumentException("Incorrect data in input source "
                        + resource.getSource()
                        + ": " + ": \"" + wordCandidate
                        + "\" does not match");
                }
            }

        return words.toArray(new String[0]);
    }
}
