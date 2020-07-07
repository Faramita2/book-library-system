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
            mongo.createIndex("borrow_records", Indexes.ascending("book.id"));
            mongo.createIndex("borrow_records", Indexes.ascending("book.name"));
            mongo.createIndex("borrow_records", Indexes.ascending("book.description"));
            mongo.createIndex("borrow_records", Indexes.ascending("book.authors.id"));
            mongo.createIndex("borrow_records", Indexes.ascending("book.categories.id"));
            mongo.createIndex("borrow_records", Indexes.ascending("book.tags.id"));
            mongo.createIndex("borrow_records", Indexes.ascending("user.id"));
            mongo.createIndex("borrow_records", Indexes.ascending("borrowed_time"));
            mongo.createIndex("borrow_records", Indexes.ascending("return_date"));
            mongo.createIndex("borrow_records", Indexes.ascending("actual_return_date"));
        });
    }
}
