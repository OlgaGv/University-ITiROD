package by.pantosha.itirod.lab10.xml;

import by.pantosha.itirod.lab5.entity.Subtask;
import by.pantosha.itirod.lab5.entity.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;

public final class XmlTaskWriter {
    private final static String ID = "id";
    private final static String CREATION_DATE = "creationDate";
    private final static String TASK = "task";
    private final static String TITLE = "title";
    private final static String SUBTASK = "subtask";
    private final static String SUBTASKS_COLLECTION = "subtasks";

    private final Writer writer;

    public XmlTaskWriter(Writer writer) {
        this.writer = writer;
    }

    public XmlTaskWriter(OutputStream stream) {
        writer = new PrintWriter(stream);
    }

    public void write(Collection<Task> tasks) throws ParserConfigurationException, TransformerException{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();
        Element root = document.createElement("tasks");
        document.appendChild(root);

        for (Task task : tasks){
            appendTask(document, root, task);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(writer);

        transformer.transform(domSource, streamResult);
    }

    private void appendTask(Document document, Element parentElement, Task task){
        // <task id="id" creationDate="date">...</task>
        Element taskElement = document.createElement(TASK);
        taskElement.setAttribute(ID, task.getId().toString());
        taskElement.setAttribute(CREATION_DATE, task.getCreationDate().toString());

        // <title>Title of task</title>
        Element taskTitleElement = document.createElement(TITLE);
        taskTitleElement.appendChild(document.createTextNode(task.getTitle()));
        taskElement.appendChild(taskTitleElement);

        // <subtasks> ... <subtasks>
        Element subtasksCollectionElement = document.createElement(SUBTASKS_COLLECTION);
        for (Subtask subtask : task.getSubtasks()) {
            // <subtask id="id">Text</subtask>
            Element subtaskElement = document.createElement(SUBTASK);
            subtaskElement.setAttribute(ID, subtask.getId().toString());
            subtaskElement.appendChild(document.createTextNode(subtask.getText()));

            subtasksCollectionElement.appendChild(subtaskElement);
        }

        taskElement.appendChild(subtasksCollectionElement);
        parentElement.appendChild(taskElement);
    }
}