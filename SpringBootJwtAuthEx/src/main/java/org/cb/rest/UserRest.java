package org.cb.rest;

import org.cb.entity.Users;
import org.cb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserRest {

    @Autowired
    private IUserService service;

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody Users users) {
        Integer id = service.saveUser(users);
        return new ResponseEntity<>("User '" + id + "' created !!!", HttpStatus.CREATED);
    }

}
