package by.pantosha.itirod.lab10.xml;

public class TaskSaxParserTest {

/*    @DataProvider(name = "SimpleTaskXml")
    public Object[][] simpleTaskXml() {
        CharArrayWriter writer = new CharArrayWriter();
        XmlTaskWriter xmlTaskWriter = new XmlTaskWriter(writer);

        Task task = new Task();
        task.setId(1);
        task.setTitle("Hello World");

        Subtask subtask = new Subtask();
        subtask.setId(1);
        subtask.setTaskId(task.getId());
        subtask.setText("subtask1");
        task.addSubtask(subtask);

        subtask = new Subtask();
        subtask.setId(2);
        subtask.setTaskId(task.getId());
        subtask.setText("subtask2");
        task.addSubtask(subtask);

        Collection<Task> collection = new ArrayList<>();
        collection.add(task);

        try {
            xmlTaskWriter.write(collection);
        } catch (ParserConfigurationException | TransformerException ex) {
            assert false;
        }
        return new Object[][]{
                {writer.toString()},
        };
    }

    @Test(dataProvider = "SimpleTaskXml")
    public void testStartElement(String xml) throws Exception {
        XmlTaskReader reader = new XmlTaskReader();
        Collection<Task> tasks = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
        Assert.assertFalse(tasks.isEmpty());
    }*/
}
