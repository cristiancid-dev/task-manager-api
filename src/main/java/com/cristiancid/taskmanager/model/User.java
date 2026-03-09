package com.cristiancid.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class User {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String email;
}
