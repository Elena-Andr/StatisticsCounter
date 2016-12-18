package ru.innopolis.elenas.wordsstat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CyrillicValidator implements Validator {
    private static final Logger log = LoggerFactory.getLogger(CyrillicValidator.class);
    private static final String REGEX_CYRILLIC = "[\\p{IsCyrillic}]+";

    @Override
    public boolean validate(String candidate) {
        return candidate.matches(REGEX_CYRILLIC);
    }
}
