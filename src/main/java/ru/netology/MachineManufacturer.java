package ru.netology;

import java.util.ArrayList;
import java.util.List;

public class MachineManufacturer extends Thread{
    private static final int PRODUCTION_TIME = 3000;
    private static final int TIME_OF_BUY = 1000;
    private String manufacturerName;
    private static List<Car> listOfCars = new ArrayList<>();
    private static int countOfSales = 0;

    public MachineManufacturer(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    @Override
    public void run() {
        while (true) {
            releaseNewCar();
            countOfSales++;
            if (countOfSales == 5) {
                return;
            }
        }
    }

    public synchronized void releaseNewCar() {
        try {
            System.out.println("Производитель " + manufacturerName + " начал производство автомобиля");
            Thread.sleep(PRODUCTION_TIME);
            listOfCars.add(new Car(getName()));
            System.out.println("Производитель " + manufacturerName + " выпустил новый автомобиль");
            notify();
        } catch (InterruptedException e) {

        }
    }

    public synchronized Car sellCar() {
        System.out.println(currentThread().getName() + " зашел в автосалон");
        try {
            while (listOfCars.size() == 0) {
                System.out.println("Машин нет в наличии\nОжидание...");
                wait();
            }
            System.out.println("Поздравляем, машина доступна для покупки\nОсуществляется оформление...");
            Thread.sleep(TIME_OF_BUY);
            System.out.println(currentThread().getName() + " забрал новый автомобиль из салона");
        } catch (InterruptedException e) {

        }
        return listOfCars.remove(0);
    }
}
