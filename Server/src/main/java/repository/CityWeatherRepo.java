package repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import util.PropertyLoader;

public class CityWeatherRepo {

    private final static MongoClient client = MongoClients.create(PropertyLoader.get("db.url"));
    private final static MongoDatabase database = client.getDatabase(PropertyLoader.get("db.name"));
    final static MongoCollection<Document> collection = database.getCollection(PropertyLoader.get("collection.name"));

    public String read(String city) {
        BasicDBObject selector = new BasicDBObject("name", city);
        Document doc = collection.find(selector).projection(Projections.excludeId()).first();

        if (doc != null) {
            System.err.println("Get result from DB, for: " + city);
            String result = doc.toJson()
                    .replaceAll("\\s", "")
                    .replaceAll("_", " ");
            if (!result.isEmpty()) {
                return result;
            }
        }
        return "";
    }

    public void create(String json) {
       new Thread(() ->
       {
            Document doc = null;
            if (!json.isEmpty()) {
                String src = json.replaceAll("\\s", "_");
                doc = Document.parse(src);
            }
            if (doc != null) {
                collection.insertOne(doc);
            }
        }).start();
    }

    public static void countdownAndDropDB(long time) {
        new Thread( () -> {
                while (true) {
                    try {
                        System.out.println("######### DROP DATABASE ##########");
                        collection.drop();
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        System.err.println("\"Countdown and Drop\" ERROR " + e.getMessage());
                    }
                }
        }).start();
    }
}
