package by.pantosha.itirod.lab2.matrix;

import java.util.ArrayList;
import java.util.List;

public final class ArrayListMatrix extends ListMatrix {

    public ArrayListMatrix(int n) {
        this(n, n);
    }

    public ArrayListMatrix(int numberOfRows, int numberOfColumns) {
        super(numberOfRows, numberOfColumns);

        ArrayList<List<Integer>> matrix = new ArrayList<>();
        matrix.ensureCapacity(numberOfRows);
        for (int i = 0; i < numberOfRows; i++) {
            ArrayList<Integer> vector = new ArrayList<>();
            vector.ensureCapacity(numberOfColumns);
            for (int j = 0; j < numberOfColumns; j++)
                vector.add(null);
            matrix.add(vector);
        }
        this.matrix = matrix;
    }

    public static IMatrix getZeroMatrix(int row, int col) {
        IMatrix matrix = new ArrayListMatrix(row, col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++)
                matrix.setElement(i, j, 0);
        }
        return matrix;
    }

    public static IMatrix getIdentityMatrix(int n) {
        IMatrix matrix = getZeroMatrix(n, n);
        for (int i = 0; i < n; i++) {
            matrix.setElement(i, i, 1);
        }
        return matrix;
    }

    @Override
    // return c = a * b
    public IMatrix multiply(IMatrix b) {
        IMatrix a = this;
        if (a.numberOfColumns() != b.numberOfRows())
            throw new RuntimeException("Illegal matrix dimensions.");
        IMatrix c = getZeroMatrix(a.numberOfRows(), b.numberOfColumns());
        for (int i = 0; i < c.numberOfRows(); i++)
            for (int j = 0; j < c.numberOfColumns(); j++)
                for (int k = 0; k < a.numberOfColumns(); k++) {
                    int element = c.getElement(i, j) + (a.getElement(i, k) * b.getElement(k, j));
                    c.setElement(i, j, element);
                }
        return c;
    }
}
