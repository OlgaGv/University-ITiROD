package by.pantosha.itirod.lab3.matrix;

import by.pantosha.itirod.lab2.matrix.ArrayListMatrix;
import by.pantosha.itirod.lab2.matrix.IMatrix;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.zip.DataFormatException;

public class MatrixIOTest {

    private final Path path = Paths.get("/tmp/matrix.txt");

    @DataProvider
    public Object[][] randomMatrix() {
        int row = 20;
        int col = 4;
        Object[][] arguments = new Object[][]{
                {new ArrayListMatrix(row, col)},
                {new ArrayListMatrix(row, col)},
                {new ArrayListMatrix(row, col)},
                {new ArrayListMatrix(row, col)}
        };

        for (Object[] argument : arguments)
            fillMatrix((IMatrix) argument[0]);
        return arguments;
    }

    @Test(dataProvider = "randomMatrix")
    public void testReadWriteMatrix(IMatrix matrix) {
        MatrixIO matrixIO = new MatrixIO();
        try {
            matrixIO.write(path, matrix);
            assert matrix.equals(matrixIO.read(path));
        } catch (IOException | DataFormatException ex) {
            AssertJUnit.fail(ex.getMessage());
        }
    }

    @Test(dataProvider = "randomMatrix")
    public void testSerializeDeSerializeMatrix(IMatrix matrix) {
        MatrixIO matrixIO = new MatrixIO();
        try {
            matrixIO.serialize(path, matrix);
            assert matrix.equals(matrixIO.deSerialize(path));
        } catch (IOException | DataFormatException ex) {
            AssertJUnit.fail(ex.getMessage());
        }
    }

    void fillMatrix(IMatrix matrix) {
        Random rand = new Random();
        for (int i = 0; i < matrix.numberOfRows(); i++) {
            for (int j = 0; j < matrix.numberOfColumns(); j++) {
                int element = rand.nextInt();
                matrix.setElement(i, j, element);
            }
        }
    }
}
