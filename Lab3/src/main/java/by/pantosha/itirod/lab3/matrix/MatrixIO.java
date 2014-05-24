package by.pantosha.itirod.lab3.matrix;

import by.pantosha.itirod.lab2.matrix.ArrayListMatrix;
import by.pantosha.itirod.lab2.matrix.IMatrix;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.zip.DataFormatException;

public class MatrixIO {
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    public void serialize(Path filePath, IMatrix matrix) throws IOException {
        FileOutputStream fout = new FileOutputStream(filePath.toString());
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(matrix);
        oos.close();
    }

    public IMatrix deSerialize(Path filePath) throws IOException, DataFormatException {
        FileInputStream fin = new FileInputStream(filePath.toString());
        ObjectInputStream ois = new ObjectInputStream(fin);
        try {
            return (IMatrix) ois.readObject();
        } catch (ClassNotFoundException ex) {
            throw new DataFormatException(ex.getMessage());
        }
    }


    public IMatrix read(Path filePath) throws IOException, DataFormatException {
        try (Scanner scanner = new Scanner(filePath, ENCODING.name())) {
            scanner.findInLine("(\\d+)x(\\d+)");
            MatchResult match = scanner.match();
            int row = Integer.parseInt(match.group(1));
            int col = Integer.parseInt(match.group(2));
            IMatrix matrix = new ArrayListMatrix(row, col);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++)
                    matrix.setElement(i, j, scanner.nextInt());
            }
            return matrix;
        } catch (RuntimeException ex) {
            throw new DataFormatException(ex.getMessage());
        }
    }

    public void write(Path filePath, IMatrix matrix) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, ENCODING)) {
            writer.write(matrix.toString());
        }
    }
}
