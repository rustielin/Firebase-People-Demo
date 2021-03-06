package com.example.rustielin.firebasepeopledemo;

import com.google.firebase.database.IgnoreExtraProperties;


/**
 * Created by Rustie Lin on 1/5/2017.
 *
 * Represents a Person (student really) with a name, email, and major(s) using String
 */

@IgnoreExtraProperties
public class Person {

    private String name;
    private String email;
    private String majors;

    public Person(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajors() {
        return majors;
    }


    public void setMajors(String majors) {
        this.majors = majors;
    }

    public String toString() {
        return this.name + " " + this.email + " " + this.majors;
    }

}
