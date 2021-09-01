package com.yocale.billmanagement.controllers;

import com.yocale.billmanagement.dtos.UserDto;
import com.yocale.billmanagement.dtos.UserLoginDto;
import com.yocale.billmanagement.dtos.UserRegisterDto;
import com.yocale.billmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserRegisterDto registerDto) throws URISyntaxException {
        UserDto registered = userService.register(registerDto);
        return ResponseEntity.created(new URI("/users/" + registered.getId())).body(registered);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable long id, @RequestBody UserRegisterDto registerDto) {
        UserDto updated = userService.update(registerDto, id);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDto>> findUsers() {
        List<UserDto> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUser(@PathVariable long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/{isAdmin}")
    public ResponseEntity<UserDto> changeUserPrivilege(@PathVariable long id, @PathVariable boolean isAdmin) {
        UserDto user = userService.changeUserPrivilege(id, isAdmin);
        return ResponseEntity.ok(user);
    }
}
