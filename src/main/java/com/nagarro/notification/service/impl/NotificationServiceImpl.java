package com.nagarro.notification.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.notification.domain.BookingEntity;
import com.nagarro.notification.domain.CustomerEntity;
import com.nagarro.notification.domain.NotificationEntity;
import com.nagarro.notification.domain.WorkerEntity;
import com.nagarro.notification.dto.ResponseTO;
import com.nagarro.notification.service.NotificationService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@Service
public class NotificationServiceImpl implements NotificationService {

	private List<NotificationEntity> customerNotificationsList = Collections.synchronizedList(new ArrayList<>());
	private List<NotificationEntity> workerNotificationsList = Collections.synchronizedList(new ArrayList<>());

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private EurekaClient eurekaClient;

	@Override
	public List<NotificationEntity> getCustomerNotifications(Integer customerId) {
		System.out.println(customerNotificationsList);
		List<NotificationEntity> customerNotifications = new ArrayList<>();
		for (NotificationEntity notificationEntity : customerNotificationsList) {
			if (notificationEntity.getUserId() == customerId)
				customerNotifications.add(notificationEntity);
		}
		return customerNotifications;
	}

	@Override
	public List<NotificationEntity> getWorkerNotifications(Integer workerId) {
		System.out.println(workerNotificationsList);
		List<NotificationEntity> workerNotifications = new ArrayList<>();
		for (NotificationEntity notificationEntity : workerNotificationsList) {
			if (notificationEntity.getUserId() == workerId)
				workerNotifications.add(notificationEntity);
		}
		return workerNotifications;
	}

	@JmsListener(destination = "BookingAcceptedByWorker")
	private void addCustomerNotification(String bookingId) {
		BookingEntity bookingEntity = getBookingEntityById(bookingId);
		WorkerEntity workerEntity = getWorkerEntityById(bookingEntity.getWorkerId());

		NotificationEntity customerNotification = new NotificationEntity();
		customerNotification.setUserId(bookingEntity.getCustomerId());
		String customertext = "Your booking of " + bookingEntity.getServiceName() + " with booking id " + bookingEntity.getId()
				+ " has been Accepted by our service provider " + workerEntity.getName() + " (email : "
				+ workerEntity.getEmail()+")";
		customerNotification.setText(customertext);
		customerNotificationsList.add(customerNotification);
		
		NotificationEntity workerNotification = new NotificationEntity();
		CustomerEntity customerEntity = getCustomerEntityById(bookingEntity.getCustomerId());
		workerNotification.setUserId(bookingEntity.getWorkerId());
		String workertext = "Your booking has been scheduled for customer : " + customerEntity.getName()
		+ " Email(" + customerEntity.getEmail()+")"+ " for service " + bookingEntity.getServiceName();
		workerNotification.setText(workertext);
		workerNotificationsList.add(workerNotification);
	}
	
	@JmsListener(destination = "BookingRejectedByWorker")
	private void addCustomerNotificationForReject(String bookingId) {
		BookingEntity bookingEntity = getBookingEntityById(bookingId);
		WorkerEntity workerEntity = getWorkerEntityById(bookingEntity.getWorkerId());

		NotificationEntity notificationEntity = new NotificationEntity();
		notificationEntity.setUserId(bookingEntity.getCustomerId());
		String text = "Your booking of " + bookingEntity.getServiceName() + " with booking id " + bookingEntity.getId()
				+ " has been rejected by our service provider " + workerEntity.getName() + " (email : "
				+ workerEntity.getEmail()+")" + "You will be assigned another worker soon" ;
		notificationEntity.setText(text);
		customerNotificationsList.add(notificationEntity);
	}

	@JmsListener(destination = "AcceptOrRejectBooking")
	private void acceptOrRejectBookingNotification(String bookingId) {
		BookingEntity bookingEntity = getBookingEntityById(bookingId);
		WorkerEntity workerEntity = getWorkerEntityById(bookingEntity.getWorkerId());

		String text = "You have received service request for " + bookingEntity.getServiceName() + " and booking id : "
				+ bookingEntity.getId();
		NotificationEntity notificationEntity = new NotificationEntity();
		notificationEntity.setUserId(workerEntity.getId());
		notificationEntity.setText(text);
		workerNotificationsList.add(notificationEntity);
	}

	private BookingEntity getBookingEntityById(String bookingId) {
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("apigateway", false);
		String serviceUrl = instance.getHomePageUrl() + "/bookings/" + bookingId;
		ResponseEntity<ResponseTO<BookingEntity>> response = restTemplate.exchange(serviceUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<ResponseTO<BookingEntity>>() {
				});
		return response.getBody().getData();
	}

	private WorkerEntity getWorkerEntityById(Integer workerId) {
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("apigateway", false);
		String workerUrl = instance.getHomePageUrl() + "/workers/" + workerId;
		ResponseEntity<ResponseTO<WorkerEntity>> workerResponse = restTemplate.exchange(workerUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<ResponseTO<WorkerEntity>>() {
				});
		return workerResponse.getBody().getData();
	}
	
	private CustomerEntity getCustomerEntityById(Integer customerID) {
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("apigateway", false);
		String workerUrl = instance.getHomePageUrl() + "/customers/" + customerID;
		ResponseEntity<ResponseTO<CustomerEntity>> workerResponse = restTemplate.exchange(workerUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<ResponseTO<CustomerEntity>>() {
				});
		return workerResponse.getBody().getData();
	}

}
