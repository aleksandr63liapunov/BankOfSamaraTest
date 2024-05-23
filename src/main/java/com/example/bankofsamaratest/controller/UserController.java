package com.example.bankofsamaratest.controller;


import com.example.bankofsamaratest.model.User;
import com.example.bankofsamaratest.service.BankAccountService;
import com.example.bankofsamaratest.service.Impl.UserServiceImpl;
import com.example.bankofsamaratest.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;


@Tag(name = "User")
@RestController
@RequestMapping("/api/create")
public class UserController {

    private final UserService userService;
    private final BankAccountService bankAccountService;
    public UserController(UserServiceImpl userService, BankAccountService bankAccountService) {
        this.userService = userService;
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

}

