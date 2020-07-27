package com.upgrade.todolist.resources;

import com.upgrade.todolist.Constants;
import com.upgrade.todolist.domain.User;
import com.upgrade.todolist.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        User user = userService.validateUser(email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {
        String name = (String) userMap.get("name");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        User user = userService.registerUser(name, email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<Map<String, Boolean>> changePassword(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String currentPwd = (String) userMap.get("currentPassword");
        String newPwd = (String) userMap.get("newPassword");
        userService.changePassword(email, currentPwd, newPwd);

        Map<String, Boolean> map = new HashMap<>();
        map.put("Password change successful", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private Map<String, String> generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userID", user.getUserID())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .compact();

        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        return map;
    }
}
