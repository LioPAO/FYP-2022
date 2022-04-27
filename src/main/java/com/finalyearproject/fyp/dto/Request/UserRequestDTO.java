package com.finalyearproject.fyp.dto.Request;

import com.finalyearproject.fyp.common.Message;
import com.finalyearproject.fyp.common.ResourceType;
import com.finalyearproject.fyp.model.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Getter
@Setter
public class UserRequestDTO {

    //USED TO ACCEPT DATA FOR CREATION

    @NotEmpty(message ="First name cannot be empty")
    @Size(min=3, message = "First name should contain at least 3 characters")
    private String firstName;
    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 3, message = "Last name should contain at least 3 characters")
    private String lastName;
    private Long addressId;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email must conform to email format")
    private String email;
    @NotEmpty(message = "Gender cannot be empty")
    private String gender;
    @NotNull(message = "User must have a phone number")
    private int phoneNumber;
    private String userName;
    @NotEmpty(message = "password cannot be empty")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;
    @NotEmpty(message = "Date of birth cannot be empty")
    private String dateOfBirth;
}
