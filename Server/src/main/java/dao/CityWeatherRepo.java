package dao;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import util.PropertyLoader;

public class CityWeatherRepo {

    private final static MongoClient client = MongoClients.create(PropertyLoader.get("db.url"));
    private final static MongoDatabase database = client.getDatabase(PropertyLoader.get("db.name"));
    final static MongoCollection<Document> collection = database.getCollection(PropertyLoader.get("collection.name"));

    public String read(String city) {
        BasicDBObject searchObj = new BasicDBObject();
        searchObj.append("name", city);

        Document doc = collection.find(searchObj).first();
        if (doc != null) {
            String result = doc.toJson();
            if (!result.isEmpty()) {
                return result;
            }
        }
        return "";
    }

    public void create(String json) {
        Document doc = null;
        if (!json.isEmpty()) {
            doc = Document.parse(json);
        }
        if (doc != null) {
            collection.insertOne(doc);
        }
    }

    public static void countdownAndDrop(long time) {
        BasicDBObject bson = new BasicDBObject();
        bson.append("", "");
        while (true) {
            new Thread (() -> {
                try {
                    Thread.sleep(time);
                    collection.deleteMany(bson);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
