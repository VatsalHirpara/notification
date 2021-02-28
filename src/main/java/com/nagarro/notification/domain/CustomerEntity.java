package com.nagarro.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    private Integer id;

    private String name;
    
    private String email;
        
    private String city;
 
    private String Address;   
}
