package com.kuznetsovka.trueshop.service.User;

import com.kuznetsovka.trueshop.domain.User;
import com.kuznetsovka.trueshop.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean save(UserDto userDto);
    UserDto findById(Long id);
    UserDto getByName(String name);
    User findByName(String name);
    List<UserDto> findAll();
    void delete(Long id);
    void save(User user);
    void updateProfile(UserDto dto);
}
