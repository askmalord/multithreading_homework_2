package ru.netology;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MachineManufacturer extends Thread{
    private static final int PRODUCTION_TIME = 7000;
    private static final int TIME_OF_BUY = 5000;
    private String manufacturerName;
    private List<Car> listOfCars = new ArrayList<>();
    private static int countOfSales = 0;
    private Lock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();

    public MachineManufacturer(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            releaseNewCar();
            countOfSales++;
            if (countOfSales == 5) {
                return;
            }
        }
    }

    public void releaseNewCar() {
        try {
            lock.lock();
            System.out.println("Производитель " + manufacturerName + " начал производство автомобиля");
            Thread.sleep(PRODUCTION_TIME);
            listOfCars.add(new Car(getName()));
            condition.signal();
            System.out.println("Производитель " + manufacturerName + " выпустил новый автомобиль");
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }

    public Car sellCar() {
        System.out.println(currentThread().getName() + " зашел в автосалон");
        try {
            lock.lock();
            while (listOfCars.size() == 0) {
                System.out.println("Машин нет в наличии\nОжидание...");
                condition.await();
            }
            System.out.println("Поздравляем, машина доступна для покупки\nОсуществляется оформление...");
            Thread.sleep(TIME_OF_BUY);
            System.out.println(currentThread().getName() + " забрал новый автомобиль из салона");
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
        return listOfCars.remove(0);
    }
}
