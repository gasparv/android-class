package example.chi.tuke.com.lab01;

import example.chi.tuke.com.lab01.Car;

public class Seat extends Car {
    public Seat()
    {
        super();
        System.out.println("Create Seat");
    }

    @Override
    public void speedUp() {
        super.speedUp();
        System.out.println("Seat speed up");
        speed+=10;
    }
}
