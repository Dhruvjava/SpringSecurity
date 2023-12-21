package org.cb.service;

import org.apache.catalina.User;
import org.cb.entity.Users;

public interface IUserService {

    public Integer saveUser(Users user);

    public Users findByUsername(String name);

}
