package io;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WeatherDataDownloader {
    private final static String apiKey = "&units=metric&APPID=7c714d0c932643cc3f4a013d409dcd31";
    private final static String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=";
    private final static String hourWeatherApiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=";
    private final static String countryApiUrl = "https://restcountries.eu/rest/v2/name/";

    public static String getCurrentWeatherData(String cityName) throws IOException {
        String jsonText = downloadJsonFile(getCurrentWeatherApiUrl(cityName));
        JSONObject jsonObject = new JSONObject(jsonText);
        return currentWeatherInfoGenerator(jsonObject, cityName);
    }

    public static String getHourlyWeatherData(String cityName, String countryName) throws IOException {
        String jsonText = downloadJsonFile(getHourlyWeatherApiUrl(cityName, countryName));
        JSONObject jsonObject = new JSONObject(jsonText);
        return hourWeatherInfoGenerator(jsonObject, cityName);
    }

    private static InputStream getHourlyWeatherApiUrl(String cityName, String countryName) throws IOException {
        return new URL(hourWeatherApiUrl + cityName + "," + getCountryCode(countryName) + apiKey).openStream();
    }

    private static InputStream getCountryApiUrl(String countryName) throws IOException {
        return new URL(countryApiUrl + countryName).openStream();
    }

    private static String getCountryCode(String countryName) throws IOException {
        String jsonText = downloadJsonFile(getCountryApiUrl(countryName));
        JSONArray jsonArray = new JSONArray(jsonText);
        return jsonArray.getJSONObject(0).getString("alpha2Code");
    }

    private static String downloadJsonFile(InputStream inputStream) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            return readJsonFile(reader);
        } finally {
            inputStream.close();
        }
    }

    private static InputStream getCurrentWeatherApiUrl(String cityName) throws IOException {
        return new URL(apiUrl + cityName + apiKey).openStream();
    }

    private static String readJsonFile(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int cp;
        while ((cp = reader.read()) != -1){
            stringBuilder.append((char) cp);
        }

        return stringBuilder.toString();
    }

    private static String currentWeatherInfoGenerator(JSONObject object, String cityName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nWeather in ").append(cityName);
        stringBuilder.append("\nTemperature: ").append(object.getJSONObject("main").get("temp") + " \u2103");
        stringBuilder.append("\nSensed Temperature: ").append(object.getJSONObject("main").get("feels_like") + " \u2103");
        stringBuilder.append("\nPressure: ").append(object.getJSONObject("main").get("pressure") + " hPa");
        stringBuilder.append("\nHumidity: ").append(object.getJSONObject("main").get("humidity") + " %");
        stringBuilder.append("\nWind Speed: ").append(object.getJSONObject("wind").get("speed") + " km/h").append("\n");
        return stringBuilder.toString();
    }

    private static String hourWeatherInfoGenerator(JSONObject object, String cityName){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < object.getJSONArray("list").length(); i++) {
            stringBuilder.append("\nWeather in ").append(cityName).append(" for ").append(object.getJSONArray("list").getJSONObject(i).get("dt_txt"));
            stringBuilder.append("\n").append(object.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).get("description"));
            stringBuilder.append(", Temperature: ").append(object.getJSONArray("list").getJSONObject(i).getJSONObject("main").get("temp") + " \u2103");
            stringBuilder.append(", Sensed Temperature: ").append(object.getJSONArray("list").getJSONObject(i).getJSONObject("main").get("feels_like") + " \u2103");
            stringBuilder.append(", Pressure: ").append(object.getJSONArray("list").getJSONObject(i).getJSONObject("main").get("pressure") + " hPa ");
            stringBuilder.append(", Humidity: ").append(object.getJSONArray("list").getJSONObject(i).getJSONObject("main").get("humidity") + " %");
            stringBuilder.append(", Wind Speed: ").append(object.getJSONArray("list").getJSONObject(i).getJSONObject("wind").get("speed") + " km/h").append("\n");
        }

    return stringBuilder.toString();
    }

}
