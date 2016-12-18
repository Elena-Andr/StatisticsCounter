package ru.innopolis.elenas.wordsstat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

class TextProcessor extends Thread {
    private static final Logger log = LoggerFactory.getLogger(TextProcessor.class);

    private StatisticsCounter statisticsCounter;
    private Resource resource;

    TextProcessor(StatisticsCounter statisticsCounter, Resource resource) {
        this.statisticsCounter = statisticsCounter;
        this.resource = resource;
    }

    @Override
    public void run() {

        String[] words = null;

        try {
            CyrillicResourceParser parser = new CyrillicResourceParser();
            words = parser.getItemsFromResource(statisticsCounter, resource);

        } catch (IllegalArgumentException ex){
            this.statisticsCounter.setCancelled(true);
            log.error(ex.getMessage(), ex);
        }

        if(statisticsCounter.getCancelled()){
            return;
        }

        Map<String, Integer> statForCurrentText = getStatisticsForResource(words);

        synchronized (statisticsCounter.getWordsStatistic()) {
            mergeMaps(statisticsCounter.getWordsStatistic(), statForCurrentText);
        }
    }

    private void mergeMaps(Map<String, Integer> mainWordsStatistics, Map<String, Integer> statForCurrentText) {
        statForCurrentText.forEach((k, v) -> mainWordsStatistics.merge(k, v, Integer::sum));
    }

    private Map<String, Integer> getStatisticsForResource(String[] words){
        Map<String, Integer> wordsStat = new HashMap<>();

        for(int i = 0; i < words.length; i++) {
            wordsStat.put(words[i], wordsStat.getOrDefault(words[i], 0) + 1);
        }

        return wordsStat;
    }
}
