package io;

import model.CityInfo;

import java.util.Scanner;

public class DataReader {
    private Scanner sc = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public int getInt() {
        int number = sc.nextInt();
        sc.nextLine();
        return number;
    }

    public CityInfo getCityInfo() {
        printer.printLine("Enter city name (ex. Tokyo) : ");
        String cityName = sc.nextLine();
        printer.printLine("Enter country name (ex. Japan) : ");
        String zipCode = sc.nextLine();

        return new CityInfo(cityName, zipCode);
    }
}
