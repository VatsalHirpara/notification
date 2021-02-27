package com.nagarro.notification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.notification.domain.NotificationEntity;
import com.nagarro.notification.dto.ResponseTO;
import com.nagarro.notification.service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

	@Autowired
	NotificationService notificationService;

	@GetMapping("/customers/{customerId}")
	public ResponseEntity<ResponseTO<List<NotificationEntity>>> getCustomerNotification(@PathVariable Integer customerId) {
		ResponseTO<List<NotificationEntity>> response = new ResponseTO<>();
		List<NotificationEntity> customerNotification = notificationService.getCustomerNotifications(customerId);
		response.setData(customerNotification);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/workers/{workerId}")
	public ResponseEntity<ResponseTO<List<NotificationEntity>>> getWorkerNotification(@PathVariable Integer workerId) {
		ResponseTO<List<NotificationEntity>> response = new ResponseTO<>();
		List<NotificationEntity> workerNotification = notificationService.getWorkerNotifications(workerId);
		response.setData(workerNotification);
		return ResponseEntity.ok(response);
	}
}
