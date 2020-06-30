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
@Collection(name = "borrow_records")
public class BorrowRecord {
    @Field(name = "id")
    @Id
    @NotNull
    public ObjectId id;

    @Field(name = "book_id")
    @NotNull
    public Long bookId;

    @Field(name = "borrower_id")
    @NotNull
    public Long borrowerId;
    //TODO

    @Field(name = "borrowed_at")
    @NotNull
    public LocalDateTime borrowedAt;

    @Field(name = "return_at")
    @NotNull
    public LocalDateTime returnAt;

    @Field(name = "created_at")
    @NotNull
    public LocalDateTime createdAt;
    
    @Field(name = "updated_at")
    @NotNull
    public LocalDateTime updatedAt;
    
    @Field(name = "created_by")
    @NotNull
    @NotBlank
    public String createdBy;
    
    @Field(name = "updated_by")
    @NotNull
    @NotBlank
    public String updatedBy;
}
