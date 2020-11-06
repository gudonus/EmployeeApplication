package org.sbrf.employee;

public class Employee {

    private Integer id;

    private String firstName;

    private String surName;

    public Employee() {
    }

    public Employee(String firstName, String surName) {
        this.firstName = firstName;
        this.surName = surName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
