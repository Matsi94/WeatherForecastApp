package model;

public class CityInfo {
    private String cityName;
    private String countryName;

    public CityInfo(String cityName, String zipCode) {
        this.cityName = cityName;
        this.countryName = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "cityName='" + cityName + '\'' +
                ", zipCode=" + countryName +
                '}';
    }
}
