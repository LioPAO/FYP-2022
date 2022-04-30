package com.finalyearproject.fyp.dto.Response;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDTO implements Serializable {

    //USED TO EXPOSE "WANTED" DATA TO REST API

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private int phoneNumber;
    private String userName;
    private LocalDate dateOfBirth;
    private int age;
    private LocalDate joinedOn;
}
