package by.pantosha.itirod.lab5.repository;

import by.pantosha.itirod.lab5.entity.Subtask;
import by.pantosha.itirod.lab5.entity.Task;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

public class SqlTaskRepository implements TaskRepository {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE tasks (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(100) NOT NULL, creation_date DATETIME NOT NULL)";
    private static final String CREATE_QUERY = "INSERT INTO tasks (name, creation_date) VALUES (?, ?)";
    private static final String SELECT_QUERY = "SELECT id, name, creation_date FROM tasks WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT id, name, creation_date FROM tasks";
    private static final String UPDATE_QUERY = "UPDATE tasks SET name = ?, creation_date = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM tasks WHERE id = ?";

    private final String _connectionString;
    private final SqlSubTaskRepository _Sql_subTaskRepository;

    public SqlTaskRepository(String connectionString) {
        _connectionString = connectionString;
        _Sql_subTaskRepository = new SqlSubTaskRepository(connectionString);
        createTableIfNotExists();
    }

    @Override
    public void create(Task task) {
        if (task.getId() != null) {
            throw new IllegalArgumentException();
        }
        // TODO: Make connection pool
        try (Connection connection = DriverManager.getConnection(_connectionString)) {
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getTitle());
            statement.setTimestamp(2, new Timestamp(task.getCreationDate().getTime()));
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating task failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                task.setId(id);
                for (Subtask subtask : task.getSubtasks())
                    _Sql_subTaskRepository.create(subtask);
            } else {
                throw new SQLException("Creating task failed, no generated key obtained.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Task read(Integer id) {
        Task task = null;
        try (Connection connection = DriverManager.getConnection(_connectionString)) {
            PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);
            statement.setInt(1, id);
            ResultSet taskSet = statement.executeQuery();
            if (taskSet.next()) {
                task = new Task();
                task.setId(taskSet.getInt(1));
                task.setTitle(taskSet.getString(2));
                task.setCreationDate(taskSet.getTimestamp(3));
                task.setSubtasks(_Sql_subTaskRepository.readAllForTask(task.getId()));
                taskSet.close();
            } else {
                throw new SQLException("Reading task failed, no key obtained.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return task;
    }

    @Override
    public Collection<Task> readAll() {
        Collection<Task> tasks = new LinkedList<>();
        try (Connection connection = DriverManager.getConnection(_connectionString)) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet taskSet = statement.executeQuery();
            while (taskSet.next()) {
                Task task = new Task();
                task.setId(taskSet.getInt(1));
                task.setTitle(taskSet.getString(2));
                task.setCreationDate(taskSet.getTimestamp(3));
                task.setSubtasks(_Sql_subTaskRepository.readAllForTask(task.getId()));
                tasks.add(task);
            }
            taskSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tasks;
    }

    @Override
    public void update(Task task) {
        if (task.getId() == null)
            throw new IllegalArgumentException();
        try (Connection connection = DriverManager.getConnection(_connectionString)) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, task.getTitle());
            statement.setTimestamp(2, new Timestamp(task.getCreationDate().getTime()));
            statement.setInt(3, task.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
                throw new SQLException("Updating task failed.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = DriverManager.getConnection(_connectionString)) {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
                throw new SQLException("Deleting task failed.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createTableIfNotExists() {
        try (Connection connection = DriverManager.getConnection(_connectionString)) {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet res = meta.getTables(null, null, "tasks", new String[]{"TABLE"});
            if (!res.next()) {
                PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY);
                statement.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
