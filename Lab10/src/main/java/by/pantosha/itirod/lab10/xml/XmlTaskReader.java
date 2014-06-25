package by.pantosha.itirod.lab10.xml;

import by.pantosha.itirod.lab5.entity.Task;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class XmlTaskReader {

    private TaskSaxParser handler = new TaskSaxParser();

    public Collection<Task> read(InputStream stream) throws SAXException, ParserConfigurationException, IOException {
        getParser().parse(stream, handler);
        return handler.getTasks();
    }
    public Collection<Task> read(String string) throws SAXException, ParserConfigurationException, IOException {
        getParser().parse(new ByteArrayInputStream(string.getBytes("UTF-8")), handler);
        return handler.getTasks();
    }

    private SAXParser getParser() throws SAXException, ParserConfigurationException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        return saxParserFactory.newSAXParser();
    }
}
