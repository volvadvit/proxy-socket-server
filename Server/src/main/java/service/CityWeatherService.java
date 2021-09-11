package service;

import dao.CityWeatherRepo;
import util.PropertyLoader;
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

        String result = dao.read(city);

        if (result.equals("")) {
            try {
                URL url = new URL("api.openweathermap.org/data/2.5/weather?q="
                        + city + "&appid=" + API_KEY);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())))
                {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = reader.readLine()) != null) {
                        response.append(inputLine);
                    }

                    result = JsonParser.parseJson(response.toString()).toString();
                    dao.create(result);
                }
            } catch (IOException e) {
                System.err.println("HTTP/URL CONNECTION ERROR   ::   " + e.getMessage());;
            }
        }
        return result;
    }
}
