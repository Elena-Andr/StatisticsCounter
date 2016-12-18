package ru.innopolis.elenas.wordsstat;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args){

        StatisticsCounter statisticsCounter = new StatisticsCounter();

        List<Resource> resourceList = new ArrayList<>();

        for(int i = 0; i < args.length; i++){
            Resource resource = new FileResource(args[i]);
            resourceList.add(resource);
        }

        statisticsCounter.setResources(resourceList.toArray(new Resource[resourceList.size()]));
        statisticsCounter.processResources();
        statisticsCounter.showReport();
        statisticsCounter.printReportToLog();
    }
}
