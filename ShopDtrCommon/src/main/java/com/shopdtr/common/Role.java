package com.shopdtr.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ROLE")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name", length = 40, nullable = false, unique = true)
    private String name;

    @Column(name="description", length = 150, nullable = false)
    private String description;

    public Role() {
    }
    public Role(String name) {
        this.name = name;
    }
    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
