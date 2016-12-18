package ru.innopolis.elenas.wordsstat;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class ValidatorTest {
    private static final Logger log = LoggerFactory.getLogger(ValidatorTest.class);
    private Validator validator;

    @Before
    public void before(){
        this.validator = new CyrillicValidator();
    }

    @Test
    public void validateSuccessTest(){
        assertEquals(true, validator.validate("моиагмшу"));
        assertEquals(true, validator.validate("моиЕЕНгмшу"));
        assertEquals(true, validator.validate("ЙФЯЧЧЫЦ"));
    }

    @Test
    public void validateFailedTest(){
        assertEquals(false, validator.validate("qwerty"));
        assertEquals(false, validator.validate("09876"));
        assertEquals(false, validator.validate("ЙФЯЧЧЫЦy"));
    }

}
