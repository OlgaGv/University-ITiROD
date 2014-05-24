package by.pantosha.itirod.lab2.matrix;

import java.io.Serializable;

public interface IMatrix extends Serializable {
    int getElement(int row, int col);
    void setElement(int row, int col, int element);

    int numberOfRows();
    int numberOfColumns();

    IMatrix multiply(IMatrix matrix);
}
