package org.cb.service;

import org.apache.catalina.User;
import org.cb.entity.Users;

public interface IUserService {

    Integer saveUser(Users user);

    Users findByUsername(String name);

}
