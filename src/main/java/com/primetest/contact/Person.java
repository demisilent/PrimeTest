package com.primetest.contact;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import java.util.List;

public class Person {

    private int id;

    @CsvBindByPosition(position = 1)
    private String name;

    @CsvBindByPosition(position = 2)
    private String secondName;

    @CsvBindByPosition(position = 0)
    private String surname;

    private List<Contact> contacts;

    @CsvBindByPosition(position = 3)
    private String adress;

    private String creator;

    @CsvBindByPosition(position = 4)
    private String comm;

    @CsvBindByPosition(position = 5)
    private String commValue;

    public Person(int id, String name, String secondName, String surname, List<Contact> contacts, String adress, String creator) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.surname = surname;
        this.contacts = contacts;
        this.adress = adress;
        this.creator = creator;
    }

    public Person(String name, String secondName, String surname, String adress) {
        this.name = name;
        this.secondName = secondName;
        this.surname = surname;
        this.adress = adress;
    }

    public Person(String surname, String name, String secondName, String adress, String comm, String commValue) {
        this.surname = surname;
        this.name = name;
        this.secondName = secondName;
        this.adress = adress;
        this.comm = comm;
        this.commValue = commValue;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public String getCommValue() {
        return commValue;
    }

    public void setCommValue(String commValue) {
        this.commValue = commValue;
    }

}
