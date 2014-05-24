package by.pantosha.itirod.lab3.symbol_analyzer;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.LinkedList;

public class FileSymbolAnalyzerTest {

    final private static Charset ENCODING = StandardCharsets.UTF_8;
    private final String filePath = "/tmp/lab3.txt";

    @BeforeClass
    public void initialize() throws IOException {
        File file = new File(filePath);
        file.createNewFile();
        LinkedList<String> lines = new LinkedList<>();
        lines.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin rutrum tincidunt libero. Interdum et malesuada fames ac ante ipsum primis in faucibus. Maecenas faucibus ipsum non diam iaculis, id viverra orci aliquet. Curabitur mi nisl, varius eget porta ut, vehicula sed purus. Vivamus semper posuere vulputate. Sed vehicula tempor dolor sit amet sollicitudin. Praesent in turpis lobortis, adipiscing libero vel, tincidunt nulla. Ut ut libero blandit, volutpat neque et, iaculis augue.");
        lines.add("Vestibulum non blandit nibh. Nulla accumsan mi justo, ut hendrerit leo fringilla ut. Pellentesque velit erat, fringilla sit amet ipsum a, consequat malesuada lectus. Quisque nec convallis ante. Cras rhoncus magna purus, a eleifend nisl viverra ac. Ut facilisis quam lorem, ut gravida elit vulputate at. Vestibulum placerat nulla id tellus vulputate euismod. Quisque quis porttitor quam. Aenean molestie dapibus dui, id aliquam libero varius a. Integer gravida enim sit amet est scelerisque, ut venenatis massa suscipit. Ut pulvinar ligula at velit dignissim, ac suscipit sem gravida. Suspendisse potenti.");
        Files.write(file.toPath(), lines, ENCODING);
    }

    @DataProvider
    public Object[][] failFileName() {
        return new Object[][]{
                {""},
                {"/tmp/fileNotExist"},
                {"/dev/random"}
        };
    }

    @Test
    public void testFileAnalyze() throws IOException {
        FileSymbolAnalyzer analyzer = new FileSymbolAnalyzer(filePath);
        Collection<StatisticsResult> results = analyzer.getStatistics();
        for (StatisticsResult result : results) {
            System.out.println(result.toString());
        }
    }

    @Test(expectedExceptions = {IOException.class}, dataProvider = "failFileName")
    public void testExceptionFileAnalyze(String fileName) throws IOException {
        FileSymbolAnalyzer analyzer = new FileSymbolAnalyzer(fileName);
        Collection<StatisticsResult> results = analyzer.getStatistics();
        for (StatisticsResult result : results) {
            System.out.println(result.toString());
        }
    }
}