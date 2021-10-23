package com.bloodbuddy.lifeshare.Model;

public class User {

    private  String name , blood_group , phone_number , email ;


    public void User(String blood_group ,  String email , String name  , String phone_number){
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.blood_group = blood_group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
