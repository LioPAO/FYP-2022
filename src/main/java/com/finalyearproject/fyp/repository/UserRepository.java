package com.finalyearproject.fyp.repository;

import com.finalyearproject.fyp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByFirstNameIgnoreCase(String firstName);

    List<User> findByLastNameIgnoreCase(String lastName);

    List<User> findByUserNameIgnoreCase(String userName);

}
