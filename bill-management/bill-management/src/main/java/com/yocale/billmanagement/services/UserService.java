package com.yocale.billmanagement.services;

import com.yocale.billmanagement.dtos.*;
import com.yocale.billmanagement.entities.User;
import com.yocale.billmanagement.exceptions.*;
import com.yocale.billmanagement.repositories.UserRepository;
import com.yocale.billmanagement.security.service.TokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final ModelMapper mapper;
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final TokenService tokenService;


    @Autowired
    public UserService(UserRepository repository, ModelMapper mapper, BCryptPasswordEncoder encoder, TokenService tokenService) {
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
        this.tokenService = tokenService;
    }

    public String login(UserLoginDto loginDto) {
        saveAdmin();
        User user = repository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("User with username " + loginDto.getUsername() + " does not exist."));

        if (!encoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password. Try again.");
        }

        return tokenService.generateToken(user);
    }

    private void saveAdmin() {
        User admin = new User("admin", encoder.encode( "adminpass"), true, 0, new LinkedList<>());
        admin.setId(1);
        repository.save(admin);
    }

    public UserDto register(UserRegisterDto userDto) {
        if (repository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new InvalidUsernameException("Username is already taken.");
        }
        User user = mapper.map(userDto, User.class);
        user.setPassword(encoder.encode(userDto.getPassword()));
        return mapper.map(repository.save(user), UserDto.class);
    }

    public void delete(long id) throws InvalidIdException {
        User user = repository.findById(id)
                .orElseThrow(() -> new InvalidIdException("User with id " + id + " does not exist."));
        repository.delete(user);
    }

    public List<UserDto> getAll() {
        return repository.findAll()
                .stream()
                .map(u -> mapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto update(UserRegisterDto userDto, long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new InvalidIdException("User with id " + id + " does not exist."));
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setAdmin(userDto.isAdmin());
        user.setBudget(userDto.getBudget());

        return mapper.map(repository.save(user), UserDto.class);
    }

    public UserDto findById(long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new InvalidIdException("User with id " + id + " does not exist."));
        return mapper.map(user, UserDto.class);
    }

    public UserDto changeUserPrivilege(long id, boolean isAdmin) {
        User user = repository.findById(id)
                .orElseThrow(() -> new InvalidIdException("User with id " + id + " does not exist."));
        user.setAdmin(isAdmin);
        return mapper.map(repository.save(user), UserDto.class);
    }
}
