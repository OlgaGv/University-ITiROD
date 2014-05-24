package by.pantosha.itirod.lab1;

import by.pantosha.itirod.lab1.internalpackage.HelloWorld;

public class Lab1 {
    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        for (int i = 0; i < 10; i++)
            helloWorld.Print();
    }
}