package org.borland.ui.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayCompareUtils {

    // TODO: unit-tests
    public static <T> boolean areUnorderedListsEqual(List<T> listA, List<T> listB) {
        Map<T, Integer> freqsA = getItemsFrequencies(listA);
        Map<T, Integer> freqsB = getItemsFrequencies(listB);

        return areItemsFrequenciesEqual(freqsA, freqsB);
    }

    private static <T> Map<T, Integer> getItemsFrequencies(List<T> items) {
        HashMap<T, Integer> result = new HashMap<>();

        for(T item: items) {
            int prevFreq = result.getOrDefault(item, 0);
            result.put(item, prevFreq + 1);
        }

        return result;
    }

    private static <T> boolean areItemsFrequenciesEqual(Map<T, Integer> freqsA, Map<T, Integer> freqsB) {
        boolean doesBContainsAllItems = freqsA.entrySet().stream()
                .map(entry -> {
                    T key = entry.getKey();
                    Integer freqB = freqsB.getOrDefault(key, 0);
                    return freqB.equals(entry.getValue());
                })
                .reduce(true, (acc, val) -> acc && val);

        return freqsA.entrySet().size() == freqsB.entrySet().size()
                && doesBContainsAllItems;
    }

}
