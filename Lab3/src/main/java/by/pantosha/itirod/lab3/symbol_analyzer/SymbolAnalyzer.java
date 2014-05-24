package by.pantosha.itirod.lab3.symbol_analyzer;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SymbolAnalyzer {

    private int totalCount = 0;
    private HashMap<Character, Integer> symbolStatistics;

    public SymbolAnalyzer() {
        symbolStatistics = new HashMap<>();
    }

    public void AddString(String s) {
        int count;
        char[] charArray = s.toCharArray();
        for (char aCharArray : charArray) {
            char character = Character.toLowerCase(aCharArray);
            if (symbolStatistics.containsKey(character))
                count = symbolStatistics.get(character) + 1;
            else
                count = 1;
            symbolStatistics.put(character, count);
        }
        totalCount += s.length();
    }

    public Collection<StatisticsResult> getStatistics() {
        Collection<StatisticsResult> results = new LinkedList<>();
        for (Map.Entry<Character, Integer> entry : symbolStatistics.entrySet()) {
            float frequency = (float) entry.getValue() / totalCount * 100;
            StatisticsResult element = new StatisticsResult(entry.getKey(), frequency);
            results.add(element);
        }
        return results;
    }
}
