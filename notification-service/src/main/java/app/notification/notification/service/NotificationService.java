package app.notification.notification.service;

import app.book.api.BookWebService;
import app.notification.api.notification.DeleteNotificationRequest;
import app.notification.api.notification.SearchNotificationRequest;
import app.notification.api.notification.SearchNotificationResponse;
import app.notification.api.notification.kafka.ReturnBorrowedBookMessage;
import app.notification.notification.domain.Notification;
import app.user.api.UserWebService;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class NotificationService {
    @Inject
    Repository<Notification> repository;
    @Inject
    UserWebService userWebService;
    @Inject
    BookWebService bookWebService;

    public void delete(Long id) {
        repository.delete(id);
    }

    public void deleteBatch(DeleteNotificationRequest request) {
        repository.batchDelete(List.of(request.ids.split(",")));
    }

    public void create(ReturnBorrowedBookMessage message) {
        Notification notification = new Notification();
        notification.userId = message.userId;
        String username = userWebService.get(message.userId).username;
        String bookName = bookWebService.get(message.bookId).name;
        notification.content = Strings.format(
            "Dear {}, the book 《{}》 you borrowed at {} should be returned tomorrow({}).",
            username, bookName,
            message.borrowedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            message.returnAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
        LocalDateTime now = LocalDateTime.now();
        notification.createdAt = now;
        notification.updatedAt = now;
        notification.createdBy = "NotificationService";
        notification.updatedBy = "NotificationService";

        repository.insert(notification);
    }

    public SearchNotificationResponse search(SearchNotificationRequest request) {
        SearchNotificationResponse response = new SearchNotificationResponse();
        Query<Notification> query = repository.select();

        response.total = query.count();
        query.skip(request.skip);
        query.limit(request.limit);

        query.where("user_id = ?", request.userId);
        response.notifications = query.fetch().stream().map(notification -> {
            SearchNotificationResponse.Notification no = new SearchNotificationResponse.Notification();
            no.id = notification.id;
            no.content = notification.content;

            return no;
        }).collect(Collectors.toList());

        return response;
    }
}
