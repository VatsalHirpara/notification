package com.nagarro.notification.domain;

import com.nagarro.notification.enums.ServiceCategory;
import com.nagarro.notification.enums.ServiceName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerEntity {
	private Integer id;

	private String name;

	private String email;

	private String city;

	private ServiceCategory serviceCategory;

	private ServiceName serviceName;
}