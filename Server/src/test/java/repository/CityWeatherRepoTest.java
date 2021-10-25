package repository;

import com.mongodb.BasicDBObject;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class CityWeatherRepoTest extends TestCase {

    private final CityWeatherRepo dao = new CityWeatherRepo();

    private final String jsonTest = "{\"name\":\"Test\",\"country\":\"UK\",\"temp\":10.0," +
            "\"weather\":\"light rain\",\"visibility\":10000,\"wind\":2.31}";

    @Test
    public void testRead() {
        String name = "Test";
        String result = dao.read(name);
        Assert.assertEquals(jsonTest, result);
    }

    @Test
    public void testCreate() {
        dao.create(jsonTest);

        BasicDBObject bson = new BasicDBObject("name", "Test");
        String actual = CityWeatherRepo.collection.find(bson).first().toJson();

        boolean test = actual != null && !actual.isEmpty();
        Assert.assertTrue(test);
    }
}