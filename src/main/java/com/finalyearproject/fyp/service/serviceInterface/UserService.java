package com.finalyearproject.fyp.service.serviceInterface;

import com.finalyearproject.fyp.dto.Request.UserRequestDTO;
import com.finalyearproject.fyp.dto.Response.AddressResponseDTO;
import com.finalyearproject.fyp.dto.Response.UserResponseDTO;
import com.finalyearproject.fyp.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface UserService {
    List<UserResponseDTO> getAllUsers();

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    User getUser(Long id);

    UserResponseDTO getUserById(Long id);

    String updateUser(Long id, UserRequestDTO userRequestDTO);

    String deleteUser(Long Id);

    List<UserResponseDTO> getUserByFirstName(String firstname);

    List<UserResponseDTO> getUserByLastName(String firstname);

    List<UserResponseDTO> getUserByUserName(String firstname);

    //ADDRESS=======================================================================

    String addAddress(Long addressId, Long userId);

    String addAddresses(List <Long> addressIds, Long userId);

    String removeAddress(Long addressId, Long userId) ;

    String removeAddress(List <Long> addressIds, Long userId);

    Set<AddressResponseDTO> getAddress(Long userId);
}
