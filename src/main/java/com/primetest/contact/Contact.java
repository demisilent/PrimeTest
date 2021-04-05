package com.primetest.contact;

public class Contact {
    private int id;
    private int m_id;
    private String communication;
    private String value;

    public Contact(int id, int m_id, String communication, String value) {
        this.id = id;
        this.m_id = m_id;
        this.communication = communication;
        this.value = value;
    }

    public Contact(int m_id, String communication, String value) {
        this.m_id = m_id;
        this.communication = communication;
        this.value = value;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
