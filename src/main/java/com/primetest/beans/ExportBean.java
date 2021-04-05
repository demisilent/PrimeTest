package com.primetest.beans;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.primetest.Utils.StringUtils;
import com.primetest.contact.Contact;
import com.primetest.contact.Person;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ManagedBean
@Named
@RequestScoped
public class ExportBean {

    private StreamedContent file;

    public StreamedContent downloadFile(List<Person> persons) throws FileNotFoundException {
        getListForCsv(persons);
        file = DefaultStreamedContent.builder()
                .name(getFileName() + ".csv")
                .contentType("text/csv")
                .build();

        //noinspection deprecation
        ((DefaultStreamedContent) file).setStream(new FileInputStream("persons.csv"));
        return file;
    }

    public void getListForCsv(List<Person> people) {
        String fileName = "persons.csv";
        Path myPath = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(myPath, StandardCharsets.UTF_8)) {
            StatefulBeanToCsv<Person> beanToCsv = new StatefulBeanToCsvBuilder<Person>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(getPersonsToCSV(people));
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException |
                IOException ignored) {
        }
    }

    private List<Person> getPersonsToCSV(List<Person> personList) {
        List<Person> listForCSV = new ArrayList<>();
        listForCSV.add(new Person("SURNAME", "NAME", "LAST_NAME", "ADRESS", "COMM_TYPE", "COMM_VALUE"));
        for (Person p : personList) {
            if (!p.getContacts().isEmpty()) {
                for (int i = 0; i < p.getContacts().size(); i++) {
                    Contact c = p.getContacts().get(i);
                    Person person = newPerson(p);
                    person.setComm(c.getCommunication());
                    person.setCommValue(c.getValue());
                    listForCSV.add(person);
                }
            } else {
                Person person = newPerson(p);
                person.setComm("");
                person.setCommValue("");
                listForCSV.add(person);
            }
        }
        return listForCSV;
    }

    private Person newPerson(Person p) {
        Person person = new Person();
        person.setSurname(p.getSurname());
        person.setName(p.getName());
        person.setSecondName(p.getSecondName());
        person.setAdress(StringUtils.replaceComma(p.getAdress()));
        return person;
    }

    private String getFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_hh:mm");
        StringBuilder sb = new StringBuilder(AdminBean.getAccount().getUsername())
                .append("_")
                .append(sdf.format(new Date()))
                .append("_")
                .append("persons");

        return String.valueOf(sb);
    }
}

