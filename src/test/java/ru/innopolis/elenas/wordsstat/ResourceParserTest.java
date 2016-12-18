package ru.innopolis.elenas.wordsstat;


import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResourceParserTest {
    private static final Logger log = LoggerFactory.getLogger(ResourceParserTest.class);

    private ResourceParser resourceParser;
    private StatisticsCounter statisticsCounter;

    @Before
    public void before(){
        this.resourceParser = new CyrillicResourceParser();
        this.statisticsCounter = new StatisticsCounter();
    }

    @Test
    public void getItemsFromResourceSuccess(){

        try {
            Resource resource = mock(Resource.class);
            when(resource.getResourceAsString()).thenReturn("вуаулщмк мшоумшб шмокш...омшок тшокшоп 000)");
            when(resource.getSource()).thenReturn("test1.txt");

            String[] results = resourceParser.getItemsFromResource(statisticsCounter, resource);

            assertEquals(5, results.length);
        } catch (IllegalArgumentException ex){
            this.statisticsCounter.setCancelled(true);
            log.error(ex.getMessage(), ex);
        }
    }

    @Test(expected = IllegalArgumentException.class )
    public void getItemsFromResourceFailed(){
        Resource resource = mock(Resource.class);
        when(resource.getResourceAsString()).thenReturn("вуаулщмк мшоумшб шмокшt...омшок тшокшоп 000)");
        when(resource.getSource()).thenReturn("test2.txt");

        String[] results = resourceParser.getItemsFromResource(statisticsCounter, resource);
    }
}
