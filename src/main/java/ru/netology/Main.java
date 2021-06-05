package ru.netology;

public class Main {
    private static final int COUNT_OF_BUYERS = 5;
    public static void main( String[] args ) {
        final MachineManufacturer machineManufacturer = new MachineManufacturer("Toyota");

        for (int i = 1; i <= COUNT_OF_BUYERS; i++) {
            new Thread(null, machineManufacturer::sellCar, "Покупатель " + i).start();
        }
        machineManufacturer.start();
    }
}
