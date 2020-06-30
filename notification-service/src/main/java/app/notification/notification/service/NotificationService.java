package app.notification.notification.service;

import app.notification.api.notification.DeleteNotificationRequest;
import app.notification.api.notification.SearchNotificationRequest;
import app.notification.api.notification.SearchNotificationResponse;
import app.notification.api.notification.kafka.ReturnBorrowedBookMessage;
import app.notification.notification.domain.Notification;
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

    public void delete(Long id) {
        repository.delete(id);
    }

    public void deleteBatch(DeleteNotificationRequest request) {
        repository.batchDelete(List.of(request.ids));
    }

    public void create(ReturnBorrowedBookMessage message) {
        Notification notification = new Notification();
        notification.userId = message.userId;
        String bookName = message.bookName;
        notification.content = Strings.format(
            "The book 《{}》 you borrowed at {} should be returned tomorrow({}).",
            bookName,
            message.borrowedAt.format(DateTimeFormatter.ISO_DATE),
            message.returnAt.format(DateTimeFormatter.ISO_DATE)
        );
        LocalDateTime now = LocalDateTime.now();
        notification.createdAt = now;
        notification.updatedAt = now;
        notification.createdBy = message.operator;
        notification.updatedBy = message.operator;

        repository.insert(notification);
    }

    public SearchNotificationResponse search(SearchNotificationRequest request) {
        Query<Notification> query = repository.select();

        query.skip(request.skip);
        query.limit(request.limit);
        query.where("user_id = ?", request.userId);

        SearchNotificationResponse response = new SearchNotificationResponse();
        response.total = query.count();
        response.notifications = query.fetch().stream().map(notification -> {
            SearchNotificationResponse.Notification view = new SearchNotificationResponse.Notification();
            view.id = notification.id;
            view.content = notification.content;
            return view;
        }).collect(Collectors.toList());

        return response;
    }
}
