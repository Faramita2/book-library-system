package app.notification.notification.service;

import app.notification.api.notification.DeleteBatchNotificationRequest;
import app.notification.api.notification.DeleteNotificationRequest;
import app.notification.api.notification.GetNotificationResponse;
import app.notification.api.notification.SearchNotificationRequest;
import app.notification.api.notification.SearchNotificationResponse;
import app.notification.api.notification.kafka.ReturnBorrowedBookMessage;
import app.notification.notification.domain.Notification;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.ForbiddenException;
import core.framework.web.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class NotificationService {
    @Inject
    Repository<Notification> notificationRepository;

    public void create(ReturnBorrowedBookMessage message) {
        Notification notification = new Notification();
        notification.userId = message.userId;
        notification.content = Strings.format(
            "The book [{}] you borrowed at {} should be returned at {}.",
            message.bookName,
            message.borrowedTime.format(DateTimeFormatter.ISO_DATE_TIME),
            message.returnDate.format(DateTimeFormatter.ISO_DATE)
        );
        LocalDateTime now = LocalDateTime.now();
        notification.createdTime = now;
        notification.updatedTime = now;
        notification.createdBy = message.requestedBy;
        notification.updatedBy = message.requestedBy;

        notificationRepository.insert(notification);
    }

    public SearchNotificationResponse search(SearchNotificationRequest request) {
        Query<Notification> query = notificationRepository.select();

        query.skip(request.skip);
        query.limit(request.limit);
        query.orderBy("created_time DESC");
        query.where("user_id = ?", request.userId);

        if (!Strings.isBlank(request.content)) {
            query.where("content LIKE ?", Strings.format("{}%", request.content));
        }

        SearchNotificationResponse response = new SearchNotificationResponse();
        response.total = query.count();
        response.notifications = query.fetch().stream().map(notification -> {
            SearchNotificationResponse.Notification view = new SearchNotificationResponse.Notification();
            view.id = notification.id;
            view.content = Strings.truncate(notification.content, 50);
            view.createdTime = notification.createdTime;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public GetNotificationResponse get(Long id) {
        Notification notification = notificationRepository.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("notification not found, id = ?", id), "NOTIFICATION_NOT_FOUND"));
        GetNotificationResponse response = new GetNotificationResponse();
        response.id = notification.id;
        response.content = notification.content;
        response.createdTime = notification.createdTime;

        return response;
    }

    public void delete(Long id, DeleteNotificationRequest request) {
        Notification notification = notificationRepository.selectOne("id = ?", id).orElseThrow(() ->
            new NotFoundException(Strings.format("notification not found, id = ?", id), "NOTIFICATION_NOT_FOUND"));
        if (!notification.userId.equals(request.userId)) {
            throw new ForbiddenException("You cannot do this.");
        }

        notificationRepository.delete(id);
    }

    public void deleteBatch(DeleteBatchNotificationRequest request) {
        notificationRepository.batchDelete(List.of(request.ids.split(",")));
    }
}
