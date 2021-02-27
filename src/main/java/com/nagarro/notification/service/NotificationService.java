package com.nagarro.notification.service;

import java.util.List;

import com.nagarro.notification.domain.NotificationEntity;

public interface NotificationService {
	List<NotificationEntity> getCustomerNotifications(Integer customerId);

	List<NotificationEntity> getWorkerNotifications(Integer workerId);
}
