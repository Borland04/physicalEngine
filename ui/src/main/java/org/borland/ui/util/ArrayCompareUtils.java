package org.borland.ui.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayCompareUtils {

    private static Logger logger = LogManager.getLogger(ArrayCompareUtils.class);

    // TODO: unit-tests
    /**
     * Compare two lists. They are equal, if both of them includes the same elements in the same amount.
     * Order is not important
     * @param listA
     * @param listB
     * @param <T> type of items in list
     * @return true if lists are equal(Omitting order). False otherwise
     */
    public static <T> boolean areUnorderedListsEqual(List<T> listA, List<T> listB) {
        logger.trace("Compare list {} with {}", listA, listB);

        Map<T, Integer> freqsA = getItemsFrequencies(listA);
        Map<T, Integer> freqsB = getItemsFrequencies(listB);

        logger.debug("listA frequencies map: {}", freqsA);
        logger.debug("listB frequencies map: {}", freqsB);

        boolean areListsEqual = areItemsFrequenciesEqual(freqsA, freqsB);
        logger.debug("Are lists equal: {}", areListsEqual);
        return areListsEqual;
    }

    private static <T> Map<T, Integer> getItemsFrequencies(List<T> items) {
        HashMap<T, Integer> result = new HashMap<>();

        for(T item: items) {
            int prevFreq = result.getOrDefault(item, 0);
            result.put(item, prevFreq + 1);
        }

        logger.trace("List {}, frequencies map: {}", items, result);
        return result;
    }

    private static <T> boolean areItemsFrequenciesEqual(Map<T, Integer> freqsA, Map<T, Integer> freqsB) {
        logger.trace("Compare frequencies {} with frequencies {}", freqsA, freqsB);
        boolean doesBContainsAllItems = freqsA.entrySet().stream()
                .map(entry -> {
                    T key = entry.getKey();
                    Integer freqB = freqsB.getOrDefault(key, 0);
                    return freqB.equals(entry.getValue());
                })
                .reduce(true, (acc, val) -> acc && val);

        boolean areFreqsEqual = freqsA.entrySet().size() == freqsB.entrySet().size()
                && doesBContainsAllItems;
        logger.trace("Are frequencies equal: {}", areFreqsEqual);
        return areFreqsEqual;
    }

}
