package com.kuznetsovka.trueshop.mapper;

import com.kuznetsovka.trueshop.domain.User;
import com.kuznetsovka.trueshop.dto.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto dto);
    List<User> toUserList(List<UserDto> users);

    @InheritInverseConfiguration
    UserDto fromUser(User user);
    List<UserDto> fromUserList(List<User> users);


}
