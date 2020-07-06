package app.borrowrecord.borrowrecord.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.mongo.Collection;
import core.framework.mongo.Field;
import core.framework.mongo.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author zoo
 */
// todo
@Collection(name = "borrow_records")
public class BorrowRecord {
    @Id
    @Field(name = "_id") // no effect
    public ObjectId id;

    @NotNull
    @Field(name = "book_id")
    public Long bookId;

    @NotNull
    @Field(name = "borrower_id")
    public Long borrowerId;

    @NotNull
    @Field(name = "borrowed_time")
    public LocalDateTime borrowedTime;

    @NotNull
    @Field(name = "return_date")
    public LocalDateTime returnDate;

    @Field(name = "actual_return_date")
    public LocalDateTime actualReturnDate;

    @NotNull
    @Field(name = "created_time")
    public LocalDateTime createdTime;

    @NotNull
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;

    @NotNull
    @NotBlank
    @Field(name = "created_by")
    public String createdBy;

    @NotNull
    @NotBlank
    @Field(name = "updated_by")
    public String updatedBy;
}
