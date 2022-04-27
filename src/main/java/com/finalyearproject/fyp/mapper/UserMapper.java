package com.finalyearproject.fyp.mapper;

import com.finalyearproject.fyp.dto.Request.UserRequestDTO;
import com.finalyearproject.fyp.dto.Response.UserResponseDTO;
import com.finalyearproject.fyp.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    User userRequestDTOToUser(UserRequestDTO userRequestDTO);
    UserResponseDTO userToUserResponseDTO(User user);

}
