package app.borrowrecord;

import app.borrowrecord.api.BOBorrowRecordWebService;
import app.borrowrecord.api.BorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.kafka.ReturnBorrowedBookMessage;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import app.borrowrecord.borrowrecord.job.FindNeedReturnRecordJob;
import app.borrowrecord.borrowrecord.service.BOBorrowRecordService;
import app.borrowrecord.borrowrecord.service.BorrowRecordService;
import app.borrowrecord.borrowrecord.web.BOBorrowRecordWebServiceImpl;
import app.borrowrecord.borrowrecord.web.BorrowRecordWebServiceImpl;
import core.framework.module.Module;
import core.framework.mongo.module.MongoConfig;

import java.time.LocalTime;

/**
 * @author zoo
 */
public class BorrowRecordModule extends Module {
    @Override
    protected void initialize() {
        dbs();

        services();

        apiServices();

        async();
    }

    private void async() {
        kafka().publish("return-borrowed-book", ReturnBorrowedBookMessage.class);
        schedule().dailyAt("find-need-return-record", bind(FindNeedReturnRecordJob.class), LocalTime.of(23, 59));
    }

    private void apiServices() {
        api().service(BorrowRecordWebService.class, bind(BorrowRecordWebServiceImpl.class));
        api().service(BOBorrowRecordWebService.class, bind(BOBorrowRecordWebServiceImpl.class));
    }

    private void services() {
        bind(BOBorrowRecordService.class);
        bind(BorrowRecordService.class);
    }

    private void dbs() {
        MongoConfig config = config(MongoConfig.class);
        config.uri(requiredProperty("sys.mongo.uri"));
        config.collection(BorrowRecord.class);
    }
}