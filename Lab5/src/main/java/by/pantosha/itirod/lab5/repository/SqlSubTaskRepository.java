package by.pantosha.itirod.lab5.repository;

import by.pantosha.itirod.lab5.entity.Subtask;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

public class SqlSubTaskRepository implements SubTaskRepository {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE subtasks (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, id_task INT NOT NULL, text VARCHAR(1000))";
    private final static String CREATE_QUERY = "INSERT INTO subtasks (id_task, text) VALUES (?, ?)";
    private static final String SELECT_QUERY = "SELECT id, id_task, text FROM subtasks WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT id, id_task, text FROM subtasks";
    private static final String SELECT_ALL_QUERY_FOR_TASKS = "SELECT id, text FROM subtasks WHERE id_task = ?";
    private static final String UPDATE_QUERY = "UPDATE subtasks SET id_task = ?, text = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM subtasks WHERE id = ?";
    private final String _connectionString;

    public SqlSubTaskRepository(String connectionString) {
        _connectionString = connectionString;
        createTableIfNotExists();
    }

    @Override
    public void create(Subtask subtask) {
        if (subtask.getId() != null) {
            throw new IllegalArgumentException();
        }
        try (Connection connection = DriverManager.getConnection(_connectionString)) {
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, subtask.getTaskId());
            statement.setString(2, subtask.getText());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating subtask failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                subtask.setId(id);
            } else {
                throw new SQLException("Creating task failed, no generated key obtained.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Subtask read(Integer id) {
        Subtask subtask = null;
        try (Connection connection = DriverManager.getConnection(_connectionString)) {
            PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);
            statement.setInt(1, id);
            ResultSet taskSet = statement.executeQuery();
            if (taskSet.next()) {
                subtask = new Subtask();
                subtask.setId(taskSet.getInt(1));
                subtask.setTaskId(taskSet.getInt(2));
                subtask.setText(taskSet.getString(3));
                taskSet.close();
            } else {
                throw new SQLException("Reading task failed, no key obtained.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return subtask;
    }

    @Override
    public Collection<Subtask> readAll() {
        Collection<Subtask> subtasks = new LinkedList<>();
        try (Connection connection = DriverManager.getConnection(_connectionString)) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet taskSet = statement.executeQuery();
            while (taskSet.next()) {
                Subtask subtask = new Subtask();
                subtask.setId(taskSet.getInt(1));
                subtask.setTaskId(taskSet.getInt(2));
                subtask.setText(taskSet.getString(3));
                subtasks.add(subtask);
            }
            taskSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return subtasks;
    }

    public Collection<Subtask> readAllForTask(Integer taskId) {
        Collection<Subtask> subtasks = new LinkedList<>();
        try (Connection connection = DriverManager.getConnection(_connectionString)) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY_FOR_TASKS);
            statement.setInt(1, taskId);
            ResultSet taskSet = statement.executeQuery();
            while (taskSet.next()) {
                Subtask subtask = new Subtask();
                subtask.setId(taskSet.getInt(1));
                subtask.setTaskId(taskId);
                subtask.setText(taskSet.getString(2));
                subtasks.add(subtask);
            }
            taskSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return subtasks;
    }

    @Override
    public void update(Subtask subtask) {
        if (subtask.getId() == null)
            throw new IllegalArgumentException();
        try (Connection connection = DriverManager.getConnection(_connectionString)) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setInt(1, subtask.getTaskId());
            statement.setString(2, subtask.getText());
            statement.setInt(3, subtask.getId());
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
            ResultSet res = meta.getTables(null, null, "subtasks", new String[]{"TABLE"});
            if (!res.next()) {
                PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY);
                statement.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
