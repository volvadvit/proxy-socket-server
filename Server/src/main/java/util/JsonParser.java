package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.CityWeatherBean;
import service.CityWeatherService;

public class JsonParser {

    private static final ObjectMapper objectMapper = getObjectMapper();

    public static ObjectMapper getObjectMapper() {
            return new ObjectMapper();
    }

    public static CityWeatherBean getModel(String json) {
        try {
            return objectMapper.readValue(json, CityWeatherBean.class);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            return new CityWeatherBean("", "", 0.0, "", 0, 0.0);
        }
    }

    public static CityWeatherBean parseJson(String src) {
        try {
            JsonNode json = objectMapper.readTree(src);
            return new CityWeatherBean(
                    json.get("name").asText(),
                    json.get("sys").get("country").asText(),
                    json.get("main").get("temp").asDouble() - 272.15,
                    json.get("weather").get(0).get("description").asText(),
                    json.get("visibility").asInt(),
                    json.get("wind").get("speed").asDouble()
            );
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            return new CityWeatherBean("", "", 0.0, "", 0, 0.0);
        }
    }

    public static String getJson(CityWeatherBean model) {
        try {
            return objectMapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            return "{}";
        }
    }
}
