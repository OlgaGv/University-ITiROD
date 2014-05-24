package by.pantosha.itirod.lab2.matrix;

import java.util.List;

public abstract class ListMatrix implements IMatrix {

    private final int row;
    private final int column;
    protected List<List<Integer>> matrix;

    public ListMatrix(int n) {
        this(n, n);
    }

    public ListMatrix(int numberOfRows, int numberOfColumns) {
        row = numberOfRows;
        column = numberOfColumns;
    }

    @Override
    public int numberOfRows() {
        return row;
    }

    @Override
    public int numberOfColumns() {
        return column;
    }

    @Override
    public int getElement(int row, int col) {
        return matrix.get(row).get(col);
    }

    @Override
    public void setElement(int row, int col, int element) {
        matrix.get(row).set(col, element);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");
        stringBuilder.append(String.format("%dx%d%s", numberOfRows(), numberOfColumns(), lineSeparator));
        for (List<Integer> vector : matrix) {
            for (int element : vector) {
                stringBuilder.append(String.format("%d ", element));
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (this.getClass() != obj.getClass())
            return false;
        IMatrix matrix = (IMatrix) obj;
        if (this.numberOfRows() != matrix.numberOfRows() || this.numberOfColumns() != matrix.numberOfColumns())
            return false;
        for (int i = 0; i < this.numberOfRows(); i++)
            for (int j = 0; j < this.numberOfColumns(); j++)
                if (this.getElement(i, j) != matrix.getElement(i, j))
                    return false;
        return true;
    }
}