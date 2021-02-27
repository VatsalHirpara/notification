package com.nagarro.notification.domain;

import java.time.Instant;

import com.nagarro.notification.enums.BookingStatus;
import com.nagarro.notification.enums.ServiceCategory;
import com.nagarro.notification.enums.ServiceName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {

	private String id;

	private BookingStatus bookingStatus = BookingStatus.PROCESSING;

	private Integer customerId;

	private Integer serviceId;

	private ServiceCategory serviceCategory;

	private ServiceName serviceName;

	private Integer workerId;

	private String workerName;

	private Instant creationTimeStamp;
}
