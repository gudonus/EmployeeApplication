package org.sbrf.employee;

import java.io.Serializable;

public class Employee implements Serializable {

    private long id;

    private String firstName;

    private String surName;

    private String function;

    private String address;

    private String phone;

    public boolean isNull() {
        if(firstName.isEmpty() && surName.isEmpty())
            return true;

        return false;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public Employee() {
//    }
//
//    public Employee(String firstName, String surName) {
//        this.firstName = firstName;
//        this.surName = surName;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

}
