package com.finalyearproject.fyp.model;

import com.finalyearproject.fyp.common.Gender;
import com.finalyearproject.fyp.common.Message;
import com.finalyearproject.fyp.common.ResourceType;
import com.finalyearproject.fyp.config.StringToEnumConverter;
import com.finalyearproject.fyp.dto.Request.UserRequestDTO;
import com.finalyearproject.fyp.exceptionHandler.ResourceNotFoundException;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name="user_id",nullable = false)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private int phoneNumber;
    private String userName;

    @Getter(AccessLevel.NONE)
    private String password;

    @Setter(AccessLevel.NONE)
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Transient
    @Setter(AccessLevel.NONE)
    private int age ;

    @Setter(AccessLevel.NONE)
    private LocalDate joinedOn;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Set<Address> address;
    @OneToMany(mappedBy = "user")
    @Column(name="wishes")
    private Set<Wish> wishes;

    //CONSTRUCTOR
    public User(UserRequestDTO userRequestDTO) {

        Gender gender= new StringToEnumConverter().convert(userRequestDTO.getGender()); //CONVERTS GENDER INPUT TO ENUM TYPE GENDER

        this.firstName= userRequestDTO.getFirstName();
        this.lastName= userRequestDTO.getLastName();
        this.email= userRequestDTO.getEmail();
        this.gender= gender;
        this.phoneNumber= userRequestDTO.getPhoneNumber();
        this.userName = userRequestDTO.getUserName();
        this.password = userRequestDTO.getPassword();
        this.dateOfBirth = LocalDate.parse(userRequestDTO.getDateOfBirth());
        this.joinedOn = LocalDate.now();
        this.age = Period.between(  this.dateOfBirth,LocalDate.now()).getYears();
        this.address = new TreeSet<>();
    }

    public void addAddress(Address address){
        this.getAddress().add(address);
    }
    public void removeAddress(Address address){
        if(!this.address.contains(address)){
            throw new ResourceNotFoundException(Message.resourceNotFound(ResourceType.ADDRESS,address.getId()));
        }
        this.getAddress().remove(address);
    }
    public int getAge() {
        return Period.between(  this.dateOfBirth,LocalDate.now()).getYears();
    }
}
