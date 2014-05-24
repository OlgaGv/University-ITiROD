package by.pantosha.itirod.lab1.internalpackage;

public class HelloWorld {
    private int count;

    public void Print() {
        count++;
        System.out.println(String.format("%d. Hello World!", count));
    }
}
