package org.cb.service.impl;

import org.cb.entity.Users;
import org.cb.repo.UserRepo;
import org.cb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public Integer saveUser(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user).getId();
    }

    @Override
    public Users findByUsername(String name) {

        return repo.findByUsername(name).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " Not Exists !!!");
        }
        List<GrantedAuthority> authrority =
                        user.getRoles().stream().map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList());
        return new User(username, user.getPassword(), authrority);
    }
}
