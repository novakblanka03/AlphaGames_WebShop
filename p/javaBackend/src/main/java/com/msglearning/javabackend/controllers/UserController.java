package com.msglearning.javabackend.controllers;

import antlr.Token;
import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.services.PurchaseService;
import com.msglearning.javabackend.services.TokenService;
import com.msglearning.javabackend.services.UserService;

import com.msglearning.javabackend.to.PurchaseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({ ControllerConstants.API_PATH_USER })
public class UserController {

    private static final String ALL_PATH = "/all";
    private static final String ID_PATH = "/id/{id}";
    private static final String EMAIL_PATH = "/email/{email}";
    private static final String NAME_PATH = "/name/{name}";
    private static final String PROFILE_IMAGE = "/image/{id}";
    private static final String PURCHASE_PATH = "/purchases";

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    PurchaseService purchaseService;


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(PURCHASE_PATH)
    public List<PurchaseTO> getUserPurchases(@RequestHeader("Authorization") String bearerToken){
        String userName = tokenService.resolveToken(bearerToken);
        Long userId = userService.findByEmail(userName).get().getId();

        return purchaseService.getPurchasesByUser(userId);
    }

//    @PostMapping
//    public ResponseEntity<ResponseEntity<?>> createUser(@RequestBody User user) {
//        ResponseEntity<?> createdUser = userService.createUser(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
//    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
