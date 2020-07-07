package app.borrowrecord.api;

import app.borrowrecord.api.borrowrecord.SchedulerSearchBorrowRecordResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface SchedulerBorrowRecordWebService {
    @GET
    @Path("/scheduler-borrow-record")
    SchedulerSearchBorrowRecordResponse list();
}
