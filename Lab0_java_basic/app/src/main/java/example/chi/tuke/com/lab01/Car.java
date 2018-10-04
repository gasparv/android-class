package example.chi.tuke.com.lab01;

public class Car {
    // Konstruktor
    protected int color = 0x000000;
    protected static int countOfCars = 0;
    protected int speed = 0;

    final public int countOfPassangers = 5;

    public Car() {
        System.out.println("Create car");
        countOfCars += 1;
    }

    public Car(int color) {
        System.out.println("Create car");
        this.color = color;
        countOfCars += 1;
    }

    public void speedUp()
    {
        System.out.println("Car speed up");
        speed += 5;
    }
}

