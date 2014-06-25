package by.pantosha.itirod.lab5.repository;

import by.pantosha.itirod.lab5.entity.Subtask;

import java.util.Collection;

public interface SubTaskRepository extends Repository<Integer, Subtask> {
    Collection<Subtask> readAllForTask(Integer taskId);
}
