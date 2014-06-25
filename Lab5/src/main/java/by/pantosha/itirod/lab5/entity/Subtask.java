package by.pantosha.itirod.lab5.entity;

public class Subtask extends Entity {
    private String _text;
    private Integer _taskId;

    public Subtask() {

    }

    public Subtask(String text) {
        setText(text);
    }

    public String getText() {
        return _text;
    }

    public void setText(String text) {
        _text = text;
    }

    public Integer getTaskId() {
        return _taskId;
    }

    public void setTaskId(Integer taskId) {
        _taskId = taskId;
    }
}
