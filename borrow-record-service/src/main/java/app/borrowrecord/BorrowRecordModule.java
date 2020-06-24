package app.borrowrecord;

import app.borrowrecord.api.BorrowRecordWebService;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import app.borrowrecord.borrowrecord.service.BorrowRecordService;
import app.borrowrecord.borrowrecord.web.BorrowRecordWebServiceImpl;
import core.framework.module.Module;
import core.framework.mongo.module.MongoConfig;

/**
 * @author zoo
 */
public class BorrowRecordModule extends Module {
    @Override
    protected void initialize() {
        MongoConfig config = config(MongoConfig.class);
        config.uri(requiredProperty("sys.mongo.uri"));
        config.collection(BorrowRecord.class);

        bind(BorrowRecordService.class);
        api().service(BorrowRecordWebService.class, bind(BorrowRecordWebServiceImpl.class));
    }
}