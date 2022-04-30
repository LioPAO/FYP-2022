package com.finalyearproject.fyp.service.serviceInterface;

import com.finalyearproject.fyp.dto.Request.UserRequestDTO;
import com.finalyearproject.fyp.dto.Response.AddressResponseDTO;
import com.finalyearproject.fyp.dto.Response.UserResponseDTO;
import com.finalyearproject.fyp.model.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public interface UserService {
    List<UserResponseDTO> getAllUsers();

    @Transactional
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    User getUser(Long id);

    UserResponseDTO getUserById(Long id);

    @Transactional
    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    @Transactional
    String deleteUser(Long Id);

    List<UserResponseDTO> getUserByFirstName(String firstname);

    List<UserResponseDTO> getUserByLastName(String firstname);

    List<UserResponseDTO> getUserByUserName(String firstname);

    //ADDRESS=======================================================================

    @Transactional
    String addAddress(Long addressId, Long userId);

    @Transactional
    String addAddresses(List <Long> addressIds, Long userId);

    @Transactional
    String removeAddress(Long addressId, Long userId) ;

    @Transactional
    String removeAddress(List <Long> addressIds, Long userId);

    Set<AddressResponseDTO> getAddress(Long userId);
}
