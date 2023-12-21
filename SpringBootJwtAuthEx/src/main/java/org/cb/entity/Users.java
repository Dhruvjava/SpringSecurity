package org.cb.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String username;

    private String password;
//
//    @CreatedDate
//    private LocalDateTime createdOn;
//
//    @CreatedBy
//    private String createdBy;
//
//    @LastModifiedDate
//    private LocalDateTime updatedOn;
//
//    @LastModifiedBy
//    private String updatedBy;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

}
