package util;

import junit.framework.TestCase;
import model.CityWeatherBean;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserTest extends TestCase {

    private final String jsonTest = "{\"name\":\"Test\",\"country\":\"UK\",\"temp\":10.0," +
            "\"weather\":\"light rain\",\"visibility\":10000,\"wind\":2.31}";

    private final CityWeatherBean dummy = new CityWeatherBean("Test", "UK", 10.0,
            "light rain", 10000, 2.31);

    @Test
    public void testGetModel() {
        CityWeatherBean city = JsonParser.getModel(jsonTest);
        Assert.assertEquals(dummy.toString() , city.toString());
    }

    @Test
    public void testParseJson() {
        String jsonTest =
                "{" +
                        "\"coord\":{\"lon\":37.6156,\"lat\":55.7522}," +
                        "\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}]," +
                        "\"base\":\"stations\"," +
                        "\"main\":{\"temp\":282.15,\"feels_like\":288.33,\"temp_min\":288.23,\"temp_max\":289.28,\"pressure\":1018,\"humidity\":83,\"sea_level\":1018,\"grnd_level\":1000}," +
                        "\"visibility\":10000," +
                        "\"wind\":{\"speed\":2.31,\"deg\":27,\"gust\":4.21},\"rain\":{\"1h\":0.37}," +
                        "\"clouds\":{\"all\":100}," +
                        "\"dt\":1631288731," +
                        "\"sys\":{\"type\":2,\"id\":2009195,\"country\":\"UK\",\"sunrise\":1631242307,\"sunset\":1631289698}," +
                        "\"timezone\":10800,\"id\":524901,\"name\":\"Test\",\"cod\":200" +
                        "}";

        CityWeatherBean city = JsonParser.parseJson(jsonTest);
        Assert.assertEquals(dummy.toString() , city.toString());
    }

    @Test
    public void testGetJson() {
        String result = JsonParser.getJson(dummy);
        Assert.assertEquals(jsonTest, result);
    }
}