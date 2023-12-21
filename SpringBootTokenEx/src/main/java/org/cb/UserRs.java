package org.cb;

import java.io.Serializable;

public class UserRs implements Serializable {
    Integer id;
    String username;
    String email;
    String empId;
    String mobile;

    public UserRs(Integer id, String username, String email, String empId, String mobile) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.empId = empId;
        this.mobile = mobile;
    }

    public UserRs() {
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getEmpId() {
        return empId;
    }

    public String getMobile() {
        return mobile;
    }
}
