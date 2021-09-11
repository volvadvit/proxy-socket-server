package dao;

import com.mongodb.BasicDBObject;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class CityWeatherRepoTest extends TestCase {

    private final CityWeatherRepo dao = new CityWeatherRepo();

    private final String jsonTest = "{\"name\":\"London_test\",\"country\":\"RU\"," +
            "\"weather\":\"light rain\",\"visibility\":10000,\"wind\":2.31}";

    @Test
    public void testRead() {
        String name = "London_test";
        String jsonTest = "{\"name\":\"London_test\",\"country\":\"RU\"," +
                "\"weather\":\"light rain\",\"visibility\":10000,\"wind\":2.31}";
        String result = dao.read(name);
        Assert.assertEquals(jsonTest, result);
    }

    @Test
    public void testCreate() throws NoSuchFieldException, IllegalAccessException {
        dao.create(jsonTest);

        BasicDBObject bson = new BasicDBObject();
        bson.append("name", "London_test");
        String actual = CityWeatherRepo.collection.find(bson).first().toJson();
        boolean test = false;
        test = actual != null && !actual.isEmpty();
        Assert.assertTrue(test);
    }

    @Test
    public void testCountdownAndDrop() {
        CityWeatherRepo.countdownAndDrop(3000);

        BasicDBObject bson = new BasicDBObject();
        bson.append("name", "London_test");
        String actual = CityWeatherRepo.collection.find(bson).first().toJson();
        boolean test = true;
        test = actual == null || actual.isEmpty();
        Assert.assertTrue(test);
    }
}