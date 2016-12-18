package ru.innopolis.elenas.wordsstat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main object for processing resources, which is intended for counting statistics
 * for the array of resources
 */
public class StatisticsCounter {
    private static final Logger log = LoggerFactory.getLogger(StatisticsCounter.class);

    private Resource[] resources;
    private volatile Map<String, Integer> wordsStatistic;
    private List<Thread> threads;
    private volatile boolean isCancelled;

    {
        wordsStatistic = new HashMap<>();
        threads = new ArrayList<>();
        isCancelled = false;
    }

    /**
     * Method sets the resources for processing
     * @param resources - input resources array
     */
    public void setResources(Resource[] resources){
        this.resources = resources;
    }

    /**
     * Method processes the resources
     */
    public void processResources() {
        try {
            for(int i = 0; i < resources.length; i++){
                Thread thread = new TextProcessor(this, resources[i]);
                thread.start();
                threads.add(thread);
            }

            //wait for all threads to stop
            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).join();

                if(isCancelled){
                    wordsStatistic.clear();
                    log.error("Processing was interrupted");
                }
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Method prints the statistic results to the console
     */
    public void showReport(){
        for (Map.Entry<String, Integer> wordEntry : wordsStatistic.entrySet()) {
            System.out.println("Word: " + wordEntry.getKey() + "; Stat: " + wordEntry.getValue());
        }
    }

    /**
     * Method prints the statistic results to the log
     */
    public void printReportToLog() {
        for (Map.Entry<String, Integer> wordEntry : wordsStatistic.entrySet()) {
            log.info("Word: " + wordEntry.getKey() + "; Stat: " + wordEntry.getValue());
        }
    }

    Map<String, Integer> getWordsStatistic() {
        return wordsStatistic;
    }

    void setCancelled(boolean arg){
        isCancelled = arg;
    }

    boolean getCancelled(){
        return isCancelled;
    }

}
