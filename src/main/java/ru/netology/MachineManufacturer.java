package ru.netology;

import java.util.ArrayList;
import java.util.List;

public class MachineManufacturer extends Thread{
    private static final int PRODUCTION_TIME = 6000;
    private static final int TIME_OF_BUY = 2000;
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

    public  void releaseNewCar() {
        System.out.println("Производитель " + manufacturerName + " начал производство автомобиля");
        try {
            Thread.sleep(PRODUCTION_TIME);
            synchronized (this) {
                listOfCars.add(new Car(getName()));
                System.out.println("Производитель " + manufacturerName + " выпустил новый автомобиль");
                notify();
            }
        } catch (InterruptedException e) {

        }
    }

    public Car sellCar() {
        System.out.println(currentThread().getName() + " зашел в автосалон");
        try {
            synchronized(this) {
                while (listOfCars.size() == 0) {
                    System.out.println(currentThread().getName() + ": машин нет в наличии, ожидание...");
                    wait();
                }
            }
            System.out.println("Поздравляем, машина доступна для покупки! Осуществляется оформление...");
            Thread.sleep(TIME_OF_BUY);
            System.out.println(currentThread().getName() + " забрал новый автомобиль из салона");
        } catch (InterruptedException e) {

        }
        return listOfCars.remove(0);
    }
}
