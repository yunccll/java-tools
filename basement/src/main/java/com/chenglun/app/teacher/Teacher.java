package com.chenglun.app.teacher;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Teacher {
    public Teacher() {}
    public Teacher(final int id, final String name){
        this.id = id;
        this.name = name;
    }
    private int id;
    @JsonProperty("id")
    public int getId(){
        return this.id;
    }
    private String name;
    @JsonProperty("name")
    public String getName(){
        return this.name;
    }
    public String toString() {
        return "id:" + this.id + ", name:" + this.name;
    }
}
