package com.finalyearproject.fyp.service.serviceImpl;

import com.finalyearproject.fyp.common.Gender;
import com.finalyearproject.fyp.common.Message;
import com.finalyearproject.fyp.common.ResourceType;
import com.finalyearproject.fyp.config.StringToEnumConverter;
import com.finalyearproject.fyp.dto.Request.UserRequestDTO;
import com.finalyearproject.fyp.dto.Response.UserResponseDTO;
import com.finalyearproject.fyp.exceptionHandler.ResourceNotFoundException;
import com.finalyearproject.fyp.mapper.UserMapper;
import com.finalyearproject.fyp.model.Address;
import com.finalyearproject.fyp.model.User;
import com.finalyearproject.fyp.repository.UserRepository;
import com.finalyearproject.fyp.service.serviceInterface.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
class UserServiceImpl implements com.finalyearproject.fyp.service.serviceInterface.UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AddressService addressService, UserMapper userMapper){
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.userMapper = userMapper;
    }
    @Transactional
    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = new User(userRequestDTO);
        return userMapper.userToUserResponseDTO( userRepository.save(user));
    }

    @Override
    public List<UserResponseDTO> getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream().map(userMapper::userToUserResponseDTO).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {

         User user = userRepository.findById(id).orElseThrow(
                 ()->new ResourceNotFoundException(Message.resourceNotFound(ResourceType.USER,id)));
         return userMapper.userToUserResponseDTO(user);

    }


    @Transactional
    @Override
    public String updateUser(Long id, UserRequestDTO userRequestDTO) {
        User update = userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(Message.resourceNotFound(ResourceType.USER,id)));

        Gender gender= new StringToEnumConverter().convert(userRequestDTO.getGender()); //CONVERTS GENDER INPUT TO ENUM TYPE GENDER
        update.setFirstName(userRequestDTO.getFirstName());
        update.setLastName(userRequestDTO.getLastName());
        if(userRequestDTO.getAddressId()!= null){
            Address newAddress = addressService.getAddress(userRequestDTO.getAddressId());
            update.getAddress().add(newAddress);
        }
        update.setEmail(userRequestDTO.getEmail());
        update.setGender(gender);
        update.setPhoneNumber(userRequestDTO.getPhoneNumber());
        update.setUserName(userRequestDTO.getUserName());
        userRepository.save(update);

        return Message.updated;
    }

    @Transactional
    @Override
    public String deleteUser(Long userid) {
        userRepository.findById(userid).orElseThrow(()->new ResourceNotFoundException(Message.resourceNotFound(ResourceType.USER,userid)));
        userRepository.deleteById(userid);
        return Message.deleted;
    }

    @Override
    public List<UserResponseDTO> getUserByFirstName(String firstname) {
        return userRepository.findByFirstNameIgnoreCase(firstname)
                .stream()
                .map(userMapper::userToUserResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDTO> getUserByLastName(String lastname) {
        return userRepository.findByLastNameIgnoreCase(lastname)
                .stream()
                .map(userMapper::userToUserResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDTO> getUserByUserName(String username) {
        return userRepository.findByUserNameIgnoreCase(username)
                .stream()
                .map(userMapper::userToUserResponseDTO)
                .collect(Collectors.toList());
    }

}
