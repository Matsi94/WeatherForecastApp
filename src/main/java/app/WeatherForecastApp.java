package app;

public class WeatherForecastApp {
    private static final String appName = "Weather App v1.0";
    public static void main(String[] args) {
        System.out.println(appName);
        AppControl appControl = new AppControl();
        appControl.appLoop();
    }
}
