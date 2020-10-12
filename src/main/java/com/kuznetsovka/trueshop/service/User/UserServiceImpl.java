package com.kuznetsovka.trueshop.service.User;

import com.kuznetsovka.trueshop.dao.UserRepository;
import com.kuznetsovka.trueshop.domain.*;
import com.kuznetsovka.trueshop.dto.UserDto;
import com.kuznetsovka.trueshop.mapper.UserMapper;
import com.kuznetsovka.trueshop.service.measure.MeasureMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper = UserMapper.MAPPER;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        InitBDUser();
    }
    @MeasureMethod
    private void InitBDUser() {
        if (!userRepository.existsById ((long) 1)) {
            userRepository.saveAll (Arrays.asList (
                    new User (null, "admin", passwordEncoder.encode ("pass"), "mail@gmail.com", false, Role.ADMIN, null, null, null),
                    new User (null, "user", passwordEncoder.encode ("pass"), "test@gmail.com", false, Role.MANAGER, null, null, null)
            ));
        }
    }

    @MeasureMethod
    @Override
    @Transactional
    public boolean save(UserDto userDto) {
        if(!Objects.equals(userDto.getPassword(), userDto.getMatchingPassword())){
            throw new RuntimeException ("Password is not equal");
        }
        User user = User.builder()
                .name(userDto.getName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .role(Role.CLIENT)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Long getId(UserDto user) {
        return mapper.toUser (user).getId ();
    }

    @Override
    public User findByName(String name) {
        return userRepository.findFirstByName(name);
    }

    @Override
    public UserDto findById(Long id) {
        return mapper.fromUser(userRepository.getOne (id));
    }

    @Override
    public UserDto getByName(String name) {
        return mapper.fromUser (userRepository.findFirstByName (name));
    }

    @Override
    public List<UserDto> findAll() {
        return mapper.fromUserList (userRepository.findAll());
    }

    @Override
    public void delete(Long id){
        userRepository.deleteById (id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @MeasureMethod
    @Override
    @Transactional
    public void updateProfile(UserDto dto) {
        User savedUser = userRepository.findFirstByName(dto.getName());
        if(savedUser == null){
            throw new RuntimeException("User not found by name " + dto.getName());
        }

        boolean changed = false;
        if(dto.getPassword() != null && !dto.getPassword().isEmpty()){
            savedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            changed = true;
        }
        if(!Objects.equals(dto.getEmail(), savedUser.getEmail())){
            savedUser.setEmail(dto.getEmail());
            changed = true;
        }
        if(changed){
            userRepository.save(savedUser);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found with name: " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<> ();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                roles);
    }
}
