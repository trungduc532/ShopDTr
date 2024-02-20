package com.shopdtr.web.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 128)
    private String name;

    @Column(nullable = false, unique = true, length = 64)
    private String alias;

    private boolean enable;

    @Column(nullable = false, length = 128)
    private String image;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new HashSet<>();

    @Transient
    public String getPhotosImagePath() {
        if(id == null || image == null) return "/images/image-thumbnail.png";
        return "/category_photos/" + this.id + "/" + this.image;
    }

    public Category(String name) {
        this.name = name;
        this.alias = name;
        this.image = "image-thumbnail.png";
    }

    public Category(Integer id) {
        this.id = id;
    }

    public Category(String name, Category parent) {
        this(name);
        this.parent = parent;
    }

    public Category(){}
}
