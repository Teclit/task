package com.ecolepmn.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "user_id")
    private int id;

    private String name;

    private String email;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String role;

    @Column(name = "create_dt")
    private String createDt;

    @JsonIgnore
    @OneToMany(mappedBy="user",fetch=FetchType.EAGER)
    private Set<Authority> authorities;

}
