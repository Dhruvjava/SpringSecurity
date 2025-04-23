package org.cb.rest;

import org.cb.dto.UserRequest;
import org.cb.dto.UserRs;
import org.cb.entity.Users;
import org.cb.service.IUserService;
import org.cb.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserRest {

    @Autowired
    private IUserService service;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/user/save")
    public ResponseEntity<String> saveUser(@RequestBody Users users) {
        Integer id = service.saveUser(users);
        return new ResponseEntity<>("User '" + id + "' created !!!", HttpStatus.CREATED);
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserRs> loginUser(@RequestBody UserRequest user) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                        user.getPassword()));
        String token = jwtUtil.generateToken(user.getUsername());
        Users s = service.findByUsername(user.getUsername());
        return ResponseEntity.ok(new UserRs(s.getName(), token, "Not Generate", s.getUsername()));
    }

    @PostMapping("/page/welcome")
    public ResponseEntity<?> welcome() {

        return ResponseEntity.ok("Welcome To User !!!");
    }

}
