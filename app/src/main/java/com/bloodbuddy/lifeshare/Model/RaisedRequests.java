package com.bloodbuddy.lifeshare.Model;

public class RaisedRequests {

    private  String uid, email , name , blood_group , phone_number , hospital_name , hospital_city , hospital_pin , hospital_area , amount , patient_condition, request_no;
    public RaisedRequests(){

    }

    public RaisedRequests(String amount, String blood_group, String email, String hospital_area, String hospital_city, String hospital_name, String hospital_pin, String name, String patient_condition, String phone_number, String request_no, String uid) {
        this.name = name;
        this.blood_group = blood_group;
        this.email = email;
        this.phone_number = phone_number;
        this.hospital_name = hospital_name;
        this.hospital_city = hospital_city;
        this.hospital_pin = hospital_pin;
        this.hospital_area = hospital_area;
        this.amount = amount;
        this.patient_condition = patient_condition;
        this.request_no = request_no;
        this.uid = uid;
    }

    public String getRequest_no() {
        return request_no;
    }

    public void setRequest_no(String request_no) {
        this.request_no = request_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_city() {
        return hospital_city;
    }

    public void setHospital_city(String hospital_city) {
        this.hospital_city = hospital_city;
    }

    public String getHospital_pin() {
        return hospital_pin;
    }

    public void setHospital_pin(String hospital_pin) {
        this.hospital_pin = hospital_pin;
    }

    public String getHospital_area() {
        return hospital_area;
    }

    public void setHospital_area(String hospital_area) {
        this.hospital_area = hospital_area;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPatient_condition() {
        return patient_condition;
    }

    public void setPatient_condition(String patient_condition) {
        this.patient_condition = patient_condition;
    }
}
