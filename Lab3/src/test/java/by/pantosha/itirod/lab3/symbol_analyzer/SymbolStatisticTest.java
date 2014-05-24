package by.pantosha.itirod.lab3.symbol_analyzer;

import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collection;

public class SymbolStatisticTest {

    @DataProvider
    public Object[][] strings() {
        return new Object[][]{
                {"Hello World", 8},
                {"Привет! Как дела? что творится?... мимими", 19},
                {"mmmmm", 1},
                {"amamamamamamamamamamamamamamamamamam", 2}
        };
    }

    @Test(dataProvider = "strings")
    public void TestSymbolStatisticCount(String s, int symbolCount) {
        SymbolAnalyzer statistics = new SymbolAnalyzer();
        statistics.AddString(s);
        Collection<StatisticsResult> results = statistics.getStatistics();
        for (StatisticsResult result : results) {
            System.out.println(result.toString());
        }
        AssertJUnit.assertEquals(results.size(), symbolCount);
    }
}
