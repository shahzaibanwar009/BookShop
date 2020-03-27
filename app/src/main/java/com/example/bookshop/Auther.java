package com.example.bookshop;

import javax.annotation.Nullable;

public class Auther {
    @Nullable
    private String id;

    private String name;
    private String city;
    private String gender;

    public Auther() {}

    public Auther(String name, String city, String gender) {
        this.name = name;
        this.city = city;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getGender() {
        return gender;
    }
    public String getId()
    {
        return id;
    }
}
