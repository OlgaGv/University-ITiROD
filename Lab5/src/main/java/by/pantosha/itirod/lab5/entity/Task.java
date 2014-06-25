package by.pantosha.itirod.lab5.entity;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

public class Task extends Entity {

    private String _name;
    private Date _creationDate;
    private Collection<Subtask> _subtasks;

    public Task(){
        _creationDate = new Date();
        _subtasks = new LinkedList<>();
    }

    public Task(String title){
        this();
        setTitle(title);
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for(Subtask subtask : _subtasks){
            subtask.setTaskId(id);
        }
    }

    public String getTitle() {
        return _name;
    }

    public void setTitle(String _name) {
        this._name = _name;
    }

    public Collection<Subtask> getSubtasks() {
        return _subtasks;
    }

    public void setSubtasks(Collection<Subtask> _subtasks) {
        for(Subtask subtask : _subtasks){
            subtask.setTaskId(getId());
        }
        this._subtasks = _subtasks;
    }

    public void addSubtask(Subtask subtask){
        _subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask){
        _subtasks.remove(subtask);
        subtask.setTaskId(null);
    }

    @Override
    public String toString() {
        return String.format("%s(%d)", getTitle(), getId());
    }

    public Date getCreationDate() {
        return _creationDate;
    }

    public void setCreationDate(Date creationDate) {
        _creationDate = creationDate;
    }
}
