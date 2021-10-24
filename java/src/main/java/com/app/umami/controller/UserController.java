
package com.app.umami.controller;

import com.app.umami.entity.Meal;
import com.app.umami.entity.User;
import com.app.umami.pojo.MessagePojo;
import com.app.umami.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/auth/login")
    public User login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/auth/signup")
    public User signUp(@RequestBody User user) {
        return userService.signup(user);
    }

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @MessageMapping("/chat")
    public void getMessages(MessagePojo messagePojo) {
        simpMessagingTemplate.convertAndSend("/topic/messages/" + messagePojo.getMeal().getHostedBy().get("_id").toString(),
                messagePojo);
    }
}
