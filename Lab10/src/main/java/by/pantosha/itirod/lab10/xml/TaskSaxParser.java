package by.pantosha.itirod.lab10.xml;

import by.pantosha.itirod.lab5.entity.Subtask;
import by.pantosha.itirod.lab5.entity.Task;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TaskSaxParser extends DefaultHandler {

    private Task task;
    private Subtask subtask;
    private List<Task> tasks;
    private StringBuilder builder;

    public Collection<Task> getTasks() {
        return tasks;
    }

    @Override
    public void startDocument() throws SAXException {
        tasks = Collections.<Task>emptyList();
        builder = new StringBuilder();
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Parsing end.");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        builder.append(ch, start, length);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "tasks":
                if (tasks.isEmpty())
                    tasks = new LinkedList<>();
                else
                    throw new SAXNotRecognizedException("Tasks");
                break;
            case "task":
                task = new Task();
                break;
            case "subtask":
                subtask = new Subtask();
                break;
            case "title":
                break;
            default:
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "task":
                if (task != null) {
                    tasks.add(task);
                    task = null;
                }
                break;
            case "subtask":
                if (subtask != null && task != null) {
                    subtask.setText(builder.toString());
                    task.addSubtask(subtask);
                    subtask = null;
                }
                break;
            case "title":
                if (task != null) {
                    task.setTitle(builder.toString());
                }
                break;
            default:
                break;
        }
        builder.setLength(0);
    }
}
