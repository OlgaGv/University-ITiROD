package by.pantosha.itirod.lab2;

import by.pantosha.itirod.lab2.matrix.ArrayListMatrix;
import by.pantosha.itirod.lab2.matrix.IMatrix;
import by.pantosha.itirod.lab2.matrix.LinkedListMatrix;

public class Lab2 {
    public static void main(String[] args) {
        int n = 100;
        IMatrix identityMatrix = ArrayListMatrix.getIdentityMatrix(n);

        System.out.println(String.format("Time for multiply matrix %dx%d:", n, n));
        long startTime = System.nanoTime();
        identityMatrix.multiply(identityMatrix);
        long estimatedTime = System.nanoTime() - startTime;

        System.out.println(String.format("ArrayListMatrix : %dns.", estimatedTime));

        identityMatrix = LinkedListMatrix.getIdentityMatrix(n);

        startTime = System.nanoTime();
        identityMatrix.multiply(identityMatrix);
        estimatedTime = System.nanoTime() - startTime;

        System.out.println(String.format("LinkedListMatrix: %dns.", estimatedTime));
    }

}