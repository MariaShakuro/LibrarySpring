package com.example.library.mapper;

import com.example.library.dto.UserDTO;
import com.example.library.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public UserDTO map(UserEntity userEntity){
        return modelMapper.map(userEntity, UserDTO.class);
    }
    public UserEntity map(UserDTO userDTO){
        return modelMapper.map(userDTO, UserEntity.class);
    }
}
