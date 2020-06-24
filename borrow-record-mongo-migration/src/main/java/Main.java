import com.mongodb.client.model.Indexes;
import core.framework.mongo.MongoMigration;
import org.bson.Document;

/**
 * @author zoo
 */
public class Main {
    public static void main(String[] args) {
        MongoMigration adminMigration = new MongoMigration("sys.properties", "sys.mongo.adminURI");
        adminMigration.migrate(mongo -> {
            mongo.runCommand(new Document().append("setParameter", 1).append("notablescan", 1));
        });

        MongoMigration migration = new MongoMigration("sys.properties");
        migration.migrate(mongo -> {
            mongo.createIndex("borrow_records", Indexes.ascending("book_id"));
        });
    }
}
