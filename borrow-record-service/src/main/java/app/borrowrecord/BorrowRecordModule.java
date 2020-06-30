package app.borrowrecord;

import app.borrowrecord.api.BOBorrowRecordWebService;
import app.borrowrecord.api.BorrowRecordWebService;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import app.borrowrecord.borrowrecord.job.FindNeedReturnRecordJob;
import app.borrowrecord.borrowrecord.service.BOBorrowRecordService;
import app.borrowrecord.borrowrecord.service.BorrowRecordService;
import app.borrowrecord.borrowrecord.web.BOBorrowRecordWebServiceImpl;
import app.borrowrecord.borrowrecord.web.BorrowRecordWebServiceImpl;
import app.notification.api.notification.kafka.ReturnBorrowedBookMessage;
import app.user.api.BOUserWebService;
import core.framework.module.Module;
import core.framework.mongo.module.MongoConfig;

import java.time.LocalTime;

/**
 * @author zoo
 */
public class BorrowRecordModule extends Module {
    @Override
    protected void initialize() {
        MongoConfig config = config(MongoConfig.class);
        config.uri(requiredProperty("sys.mongo.uri"));
        config.collection(BorrowRecord.class);

        //TODO
        api().client(BOUserWebService.class, requiredProperty("app.user.ServiceURL"));

        bind(BOBorrowRecordService.class);
        bind(BorrowRecordService.class);

        api().service(BorrowRecordWebService.class, bind(BorrowRecordWebServiceImpl.class));
        api().service(BOBorrowRecordWebService.class, bind(BOBorrowRecordWebServiceImpl.class));

        kafka().publish("return-borrowed-book", ReturnBorrowedBookMessage.class);
        //TODO
        schedule().dailyAt("find-need-return-record", bind(FindNeedReturnRecordJob.class), LocalTime.of(23,59));
    }
}