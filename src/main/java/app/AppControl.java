package app;

import exception.NoSuchOptionException;
import io.ConsolePrinter;
import io.DataReader;
import io.WeatherDataDownloader;
import model.CityInfo;
import org.json.JSONException;

import java.io.IOException;
import java.util.InputMismatchException;

public class AppControl {
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader reader = new DataReader(printer);

    void appLoop(){
        Options options;

        do {
            printOptions();
            options = getOption();
            switch (options){
                case CURRENT_WEATHER:
                    getCurrentWeather();
                    break;
                case HOURLY_FORECAST:
                    getHourlyForecast();
                    break;
                case EXIT:
                    //exit();
                    break;
                default:
                    printer.printLine("There is no such an option, please try again: ");

            }
        }while (options != Options.EXIT);

    }

    private void getHourlyForecast() {
        CityInfo city = reader.getCityInfo();
        String weatherInfo = null;
        try {
            weatherInfo = WeatherDataDownloader.getHourlyWeatherData(city.getCityName(), city.getCountryName());
        } catch (JSONException | IOException e){
            e.getStackTrace();
        }
        System.out.println(weatherInfo);
    }

    private void getCurrentWeather() {
        CityInfo city = reader.getCityInfo();
        String weatherInfo = null;
        try {
             weatherInfo = WeatherDataDownloader.getCurrentWeatherData(city.getCityName());
        } catch (JSONException | IOException e){
            e.getStackTrace();
        }
        System.out.println(weatherInfo);
    }

    private Options getOption() {
        boolean optionOk = false;
        Options options = null;

        while (!optionOk){
            try {
                options = Options.createFromInt(reader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e){
                printer.printLine(e.getMessage() + " , no such option, please enter option number: ");
            } catch (InputMismatchException e){
                printer.printLine(e.getMessage() + " , wrong value entered, please try again ");
            }
        }
        return options;

    }

    private void printOptions() {
        printer.printLine("Choose your option:");
        for (Options option : Options.values()) {
            printer.printLine(option.toString());
        }
    }

    private enum Options {
        CURRENT_WEATHER(1, "Check current weather in your city."),
        HOURLY_FORECAST(2, "Today's hourly weather forecast for your city."),
        EXIT(3, "Exit");

        private int value;
        private String description;

        Options(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String toString() {
            return value + ". " + description;
        }

        static Options createFromInt(int option) throws NoSuchOptionException {
            try {
                return Options.values()[option-1];
            } catch (IndexOutOfBoundsException e){
                throw new NoSuchOptionException("There is no such options");
            }
        }
    }
}
