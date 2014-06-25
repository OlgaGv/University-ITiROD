package by.pantosha.itirod.lab5;

import by.pantosha.itirod.lab5.entity.Subtask;
import by.pantosha.itirod.lab5.entity.Task;
import by.pantosha.itirod.lab5.repository.SqlTaskRepository;
import by.pantosha.itirod.lab5.repository.TaskRepository;
import com.mysql.jdbc.Driver;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

public class DalTest {

    private final static String CONNECTION_STRING = "jdbc:mysql://localhost:3306/test";
    private final TaskRepository _taskDal = new SqlTaskRepository(CONNECTION_STRING);

    @DataProvider
    public Object[][] exampleTask() {
        Task task = new Task();
        task.setTitle("Lorem ipsum");
        Subtask subtask1 = new Subtask();
        Subtask subtask2 = new Subtask();
        subtask1.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        subtask2.setText("Duis quis mauris facilisis, porttitor enim at, condimentum libero.");
        task.addSubtask(subtask1);
        task.addSubtask(subtask2);
        return new Object[][]{
                {task}
        };
    }

    @Test
    public void TestConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());
        DriverManager.getConnection(CONNECTION_STRING);
    }

    @Test(dataProvider = "exampleTask")
    public void TestReadCreate(Task task) throws Exception {
        _taskDal.create(task);
        Task readTask = _taskDal.read(task.getId());
        AssertJUnit.assertEquals(task.getTitle(), readTask.getTitle());
    }

    @Test
    public void TestReadAll() {
        Collection<Task> tasks = _taskDal.readAll();
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    @Test
    public void TestUpdate() {
        Collection<Task> tasks = _taskDal.readAll();
        Iterator<Task> iterator = tasks.iterator();
        Task task = iterator.next();
        task.setTitle(task.getTitle().replace('o', 'y'));
        _taskDal.update(task);
        Task readTask = _taskDal.read(task.getId());
        AssertJUnit.assertEquals(task.toString(), readTask.toString());
    }

    @Test
    public void TestDelete() throws SQLException {
        DriverManager.registerDriver(new Driver());
        Collection<Task> tasks = _taskDal.readAll();
        Iterator<Task> iterator = tasks.iterator();
        Task task = iterator.next();
        _taskDal.delete(task.getId());
    }
}
