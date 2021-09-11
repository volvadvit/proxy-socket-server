package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CityWeatherBean {

    @JsonProperty("name")
    private String name;
    @JsonProperty("country")
    private String country;
    @JsonProperty("temp")
    private double temp;
    @JsonProperty("weather")
    private String weather;
    @JsonProperty("visibility")
    private int visibility;
    @JsonProperty("wind")
    private double wind;

    public CityWeatherBean(String name, String country, double temp, String weather, int visibility, double wind) {
        this.name = name;
        this.country = country;
        this.temp = temp;
        this.weather = weather;
        this.visibility = visibility;
        this.wind = wind;
    }

    //For JUnit test
    public CityWeatherBean(){

    }

    @Override
    public String toString() {
        return "City = " + this.name + ";\n" +
         "Country = " + this.country + ";\n" +
         "Temperature = " + this.temp + ";\n" +
         "Weather = " + this.weather + ";\n" +
         "Visibility = " + this.visibility + ";\n" +
         "Wind = " + this.wind + ";";
    }
}
