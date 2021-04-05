package com.primetest.beans;

import com.primetest.contact.Contact;
import com.primetest.contact.Person;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@ManagedBean(name = FilterBean.BEAN_NAME)
@SessionScoped
public class FilterBean implements Serializable {
    static final String BEAN_NAME = "filter";
    private final PrimeFaces primeFaces = PrimeFaces.current();
    private String filteringValue = "";
    private String filteringContactValue = "";
    private List<Person> filteringPerson;
    private List<Contact> filteringContact;

    public boolean globalFilterFunctionForPersons(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        Person person = (Person) value;

        return person.getName().toLowerCase().contains(filterText)
                || person.getSecondName().toLowerCase().contains(filterText)
                || person.getSurname().toLowerCase().contains(filterText)
                || checkContactsOnGlobalFilter(person.getContacts(), filterText);
    }

    private boolean checkContactsOnGlobalFilter(List<Contact> contacts, String filterText) {
        boolean contact = false;
        if (!contacts.isEmpty()) {
            for (Contact c : contacts) {
                if (c.getValue().toLowerCase().contains(filterText)) {
                    contact = true;
                    break;
                }
            }
        }
        return contact;
    }

    public boolean globalFilterFunctionForContacts(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }
        Contact contact = (Contact) value;

        return contact.getValue().toLowerCase().contains(filterText);
    }

    public void onChangeFilteringTable(String tableId) {
        primeFaces.executeScript("PF('" + tableId + "').filter()");
    }

    public void ClearFilter(String tableId) {
        if (filteringContact != null) {
            primeFaces.executeScript("PF('" + tableId + "').clearFilters()");
        }
            primeFaces.executeScript("PF('" + tableId + "').unselectAllRows()");
    }


    /*    Getters and Setters    */
    public List<Person> getFilteringPerson() {
        return filteringPerson;
    }

    public void setFilteringPerson(List<Person> filteringPerson) {
        this.filteringPerson = filteringPerson;
    }

    public List<Contact> getFilteringContact() {
        return filteringContact;
    }

    public void setFilteringContact(List<Contact> filteringContact) {
        this.filteringContact = filteringContact;
    }

    public String getFilteringValue() {
        return filteringValue;
    }

    public void setFilteringValue(String filteringValue) {
        this.filteringValue = filteringValue;
    }

    public String getFilteringContactValue() {
        return filteringContactValue;
    }

    public void setFilteringContactValue(String filteringContactValue) {
        this.filteringContactValue = filteringContactValue;
    }
}
