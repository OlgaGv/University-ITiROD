package by.pantosha.itirod.lab2.matrix;

import by.pantosha.itirod.lab2.matrix.ArrayListMatrix;
import by.pantosha.itirod.lab2.matrix.IMatrix;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ArrayListMatrixTest {

    private final int row = 5;

    public static boolean isZeroMatrix(IMatrix matrix) {
        for (int i = 0; i < matrix.numberOfRows(); i++) {
            for (int j = 0; j < matrix.numberOfColumns(); j++) {
                int element = matrix.getElement(i, j);
                if (element != 0)
                    return false;
            }
        }
        return true;
    }

    public static boolean matrixEqualArray(IMatrix actual, int[][] expected) {
        if (actual.numberOfRows() != expected.length || actual.numberOfColumns() != expected[0].length)
            return false;
        for (int i = 0; i < actual.numberOfRows(); i++)
            for (int j = 0; j < actual.numberOfColumns(); j++)
                if (actual.getElement(i, j) != expected[i][j])
                    return false;
        return true;
    }

    @DataProvider
    public Object[][] matrixesForMultiply() {
        int[][] zeroArray = new int[row][row];

        int[][] identityArray = new int[row][row];
        for (int i = 0; i < row; i++) {
            identityArray[i][i] = 1;
        }

        return new Object[][]{
                {ArrayListMatrix.getZeroMatrix(row, row), ArrayListMatrix.getZeroMatrix(row, row), zeroArray},
                {ArrayListMatrix.getZeroMatrix(row, row), ArrayListMatrix.getIdentityMatrix(row), zeroArray},
                {ArrayListMatrix.getIdentityMatrix(row), ArrayListMatrix.getIdentityMatrix(row), identityArray}
        };
    }

    @Test
    public void testIdentityMatrix() {
        IMatrix identityMatrix = ArrayListMatrix.getIdentityMatrix(row);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                int element = identityMatrix.getElement(i, j);
                if (i != j)
                    assert element == 0;
                else
                    assert element == 1;
            }
        }
    }

    @Test
    public void testZeroMatrix() {
        IMatrix zeroMatrix = ArrayListMatrix.getZeroMatrix(row, row);
        assert isZeroMatrix(zeroMatrix);
    }

    @Test(dataProvider = "matrixesForMultiply")
    public void testMultiply(IMatrix a, IMatrix b, int[][] expected) {
        IMatrix multiply = a.multiply(b);
        assert matrixEqualArray(multiply, expected);

    }
}