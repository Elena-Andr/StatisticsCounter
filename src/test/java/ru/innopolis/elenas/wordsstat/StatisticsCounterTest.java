package ru.innopolis.elenas.wordsstat;


import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StatisticsCounterTest {
    private static Logger logger = LoggerFactory.getLogger(StatisticsCounterTest.class);
    private StatisticsCounter statisticsCounter = new StatisticsCounter();

    @BeforeClass
    public static void beforeTest(){}

    @Before
    public void before() {
        this.statisticsCounter = new StatisticsCounter();
    }

    @Test
    public void processResourcesSuccessTest(){
        Resource resource = mock(Resource.class);
        when(resource.getResourceAsString()).thenReturn("вуаулщмк мшоумшб шмокш...омшок тшокшоп 000)");
        when(resource.getSource()).thenReturn("test.txt");

        statisticsCounter.setResources(new Resource[] {resource});
        statisticsCounter.processResources();

        Map<String, Integer> results = statisticsCounter.getWordsStatistic();

        assertTrue("Statistic is correct", results.size() == 5);
    }

    @Test
    public void processResourcesFailedTest(){
        Resource resource = mock(Resource.class);
        when(resource.getResourceAsString()).thenReturn("вуаулщмкt мшоумшб шмокш...омшок тшокшоп 000");
        when(resource.getSource()).thenReturn("test.txt");

        statisticsCounter.setResources(new Resource[] {resource});
        statisticsCounter.processResources();

        Map<String, Integer> results = statisticsCounter.getWordsStatistic();

        assertTrue("Gathering stat. failed", results.size() == 0);
    }

    @After
    public void after(){}

    @AfterClass
    public static void afterTest(){}
}
