package by.pantosha.itirod.lab4.utils;

public class WorkUnit<T> {
    protected final T workUnit;

    public WorkUnit(T workUnit) {
        this.workUnit = workUnit;
    }

    public T getWorkUnit() {
        return workUnit;
    }
}
