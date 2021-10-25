package service;

import repository.CityWeatherRepo;
import model.CityWeatherBean;
import util.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CityWeatherService {

    private final String API_KEY;
    private final CityWeatherRepo dao;

    public CityWeatherService(String apiKey) {
        this.API_KEY = apiKey;
        this.dao = new CityWeatherRepo();
    }

    public String getInfo(String city) {

        String tmp = dao.read(city);

        if (tmp == null || tmp.equals("")) {
            System.err.println("Get result from API server, for: " + city);

            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="
                        + city + "&appid=" + API_KEY);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))
                ) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = reader.readLine()) != null) {
                        response.append(inputLine);
                    }

                    CityWeatherBean model = JsonParser.parseJson(response.toString());
                    String result = model.toString();
                    dao.create(JsonParser.getJson(model));

                    return result;
                }
            } catch (IOException e) {
                System.err.println("HTTP/URL CONNECTION ERROR   ::   " + e.getMessage());
                return "Request ERROR. Change city name or try later.";
            }
        } else {
            return JsonParser.getModel(tmp).toString();
        }
    }
}
