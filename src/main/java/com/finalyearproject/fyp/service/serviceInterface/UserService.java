package com.finalyearproject.fyp.service.serviceInterface;

import com.finalyearproject.fyp.dto.Request.UserRequestDTO;
import com.finalyearproject.fyp.dto.Response.UserResponseDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public interface UserService {
    List<UserResponseDTO> getAllUsers();

    @Transactional
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO getUserById(Long id);

    @Transactional
    String updateUser(Long id, UserRequestDTO userRequestDTO);

    @Transactional
    String deleteUser(Long Id);

    List<UserResponseDTO> getUserByFirstName(String firstname);

    List<UserResponseDTO> getUserByLastName(String firstname);

    List<UserResponseDTO> getUserByUserName(String firstname);
}
