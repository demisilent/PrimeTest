package com.primetest.beans;

import com.primetest.admin.Account;
import com.primetest.contact.Contact;
import com.primetest.contact.Person;
import com.primetest.database.DBLoadData;
import com.primetest.database.DBUploadData;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = MainBean.BEAN_NAME)
@ViewScoped
public class MainBean implements Serializable {
    static final String BEAN_NAME = "main";
    private static final Logger LOG = LoggerFactory.getLogger(MainBean.class);
    private final PrimeFaces instance = PrimeFaces.current();

    private String name = "";
    private String secondName = "";
    private String surname = "";
    private String adress = "";
    private String SOMtypeOFContact;
    private String valueOFContact;
    private Person selectedPerson;
    private Contact selectedContact;

    private Account userAccount = AdminBean.getAccount();
    private List<Person> persons = DBLoadData.getContacts();
    private boolean disabledButtons = true;
    private boolean disabledButtonsContact = true;

    @PostConstruct
    public void init() {

    }

    public void EditContactList(String type) {
        if (type.equals("new")) {
            DBUploadData.editPerson(new Person(name, secondName, surname, adress), type);
        }
        if (type.equals("edit") || type.equals("delete")) {
            disabledButtons = type.equals("delete");
            DBUploadData.editPerson(selectedPerson, type);
        }
        if (type.equals("new_c")) {
            DBUploadData.editContact(new Contact(selectedPerson.getId(), SOMtypeOFContact, valueOFContact), type);
        }
        if (type.equals("edit_c") || type.equals("delete_c")) {
            disabledButtonsContact = type.equals("delete_c");
            DBUploadData.editContact(selectedContact, type);
        }
        instance.executeScript("PF('datatable1').clearFilters()");
        instance.executeScript("PF('datatable').clearFilters()");
        closeDialog(type);
        refreshTable();
        if (selectedPerson!=null) {
            refreshSelectedPerson(selectedPerson.getId());
        }
    }

    public void openDialog(String type) {
        instance.executeScript("PF('" + type + "').show()");
    }

    public void closeDialog(String type) {
        instance.executeScript("PF('" + type + "').hide()");
    }

    private void refreshTable() {
        persons = DBLoadData.getContacts();
    }

    private void refreshSelectedPerson(int id) {
        for (Person person : persons) {
            if (person.getId() == id) {
                selectedPerson = person;
            }
        }
    }

    public void enableButtons(boolean isD) {
        disabledButtons = isD;
        disabledButtonsContact = !isD;
    }

    public void enableButtonsContact(boolean isD) {
        disabledButtonsContact = isD;
    }

    /* ----- Getters and Setters ----- */

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


    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public boolean isDisabledButtons() {
        return disabledButtons;
    }

    public void setDisabledButtons(boolean disabledButtons) {
        this.disabledButtons = disabledButtons;
    }

    public Account getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public String getSOMtypeOFContact() {
        return SOMtypeOFContact;
    }

    public void setSOMtypeOFContact(String SOMtypeOFContact) {
        this.SOMtypeOFContact = SOMtypeOFContact;
    }

    public String getValueOFContact() {
        return valueOFContact;
    }

    public void setValueOFContact(String valueOFContact) {
        this.valueOFContact = valueOFContact;
    }

    public Contact getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(Contact selectedContact) {
        this.selectedContact = selectedContact;
    }

    public boolean isDisabledButtonsContact() {
        return disabledButtonsContact;
    }

    public void setDisabledButtonsContact(boolean disabledButtonsContact) {
        this.disabledButtonsContact = disabledButtonsContact;
    }
}
