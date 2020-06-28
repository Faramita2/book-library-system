package app.borrowrecord;

import app.borrowrecord.api.BOBorrowRecordWebService;
import app.borrowrecord.api.BorrowRecordWebService;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import app.borrowrecord.borrowrecord.service.BOBorrowRecordService;
import app.borrowrecord.borrowrecord.service.BorrowRecordService;
import app.borrowrecord.borrowrecord.web.BOBorrowRecordWebServiceImpl;
import app.borrowrecord.borrowrecord.web.BorrowRecordWebServiceImpl;
import app.user.api.UserWebService;
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
        api().client(UserWebService.class, requiredProperty("app.user.ServiceURL"));
        api().service(BorrowRecordWebService.class, bind(BorrowRecordWebServiceImpl.class));
        bind(BOBorrowRecordService.class);
        api().service(BOBorrowRecordWebService.class, bind(BOBorrowRecordWebServiceImpl.class));
    }
}