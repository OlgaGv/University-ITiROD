package by.pantosha.itirod.lab3.symbol_analyzer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class FileSymbolAnalyzer {
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    private final String filePath;

    public FileSymbolAnalyzer(String filePath) {
        this.filePath = filePath;
    }

    public Collection<StatisticsResult> getStatistics() throws IOException {
        Path path = Paths.get(filePath);
        SymbolAnalyzer analyzer = new SymbolAnalyzer();
        List<String> strings = Files.readAllLines(path, ENCODING);
        for (String string : strings) {
            analyzer.AddString(string);
        }
        return analyzer.getStatistics();
    }
}