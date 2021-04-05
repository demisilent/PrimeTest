package com.primetest.database;


import com.primetest.Utils.Utils;
import com.primetest.admin.Account;
import com.primetest.admin.Password;
import com.primetest.beans.AdminBean;
import com.primetest.contact.Contact;
import com.primetest.contact.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUploadData {
    private static final Logger LOG = LoggerFactory.getLogger(DBUploadData.class);

    public static void createNewUser(Account account) {
        String hashPassword = Password.get_SHA_512_SecurePassword(account.getPassword(), account.getUsername());
        try (Connection connection = DBConnection.getConnection()) {
            if (!DBLoadData.checkUserID(connection, account.getUsername())) {
                String sql = "INSERT INTO users_list (u_id, u_name, u_password, u_role, u_birthday) VALUES (?,?,?,?,?)";
                PreparedStatement psCreateUser = connection.prepareStatement(sql);
                {
                    psCreateUser.setInt(1, account.getId());
                    psCreateUser.setString(2, account.getUsername());
                    psCreateUser.setString(3, hashPassword);
                    psCreateUser.setString(4, Utils.setRole(account.getUsername()));
                    psCreateUser.setString(5, Utils.converterDateToString(account.getBirthdate()));
                    psCreateUser.executeUpdate();
                }
                LOG.info("Создан новый пользователь : [" + account.getUsername() + "][" + account.getId() + "]");
            } else {
                LOG.info("Пользователь с таким именем существует : [" + account.getUsername() + "]");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editPerson(Person person, String type) {
        String sql;
        try (Connection connection = DBConnection.getConnection()) {
            LOG.info("Установлено соединение с базой данной");
            if (person != null) {
                switch (type) {
                    case "new":
                        sql = "INSERT INTO persons_list (p_name , p_second_name, p_surname, c_adress, p_mID) VALUES (?,?,?,?,?);";
                        PreparedStatement psNew = connection.prepareStatement(sql);
                    {
                        psSetData(person, psNew);
                        psNew.setInt(5, AdminBean.getAccount().getId());
                        psNew.executeUpdate();
                    }
                    LOG.info("Создана новая запись : " + person.getSurname() + " " + person.getName());
                    break;
                    case "edit":
                        sql = "UPDATE persons_list SET p_name = ?, p_second_name = ?, p_surname = ?, c_adress = ? WHERE p_id = ? ";
                        PreparedStatement psEdit = connection.prepareStatement(sql);
                    {
                        psSetData(person, psEdit);
                        psEdit.setInt(5, person.getId());
                        psEdit.executeUpdate();
                    }
                    LOG.info("Запись изменена : " + person.getSurname() + " " + person.getName());
                    break;
                    case "delete":
                        int personID = person.getId();
                        sql = "DELETE FROM contact_card WHERE cc_mid = " + personID;
                        PreparedStatement psDelete = connection.prepareStatement(sql);
                        psDelete.executeUpdate();
                        sql = "DELETE FROM persons_list WHERE p_id = " + personID;
                        PreparedStatement psDelete1 = connection.prepareStatement(sql);
                        psDelete1.executeUpdate();
                        LOG.info("Запись удалена : " + person.getSurname() + " " + person.getName());
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.info(e.getMessage());
        }
    }

    public static void editContact(Contact contact, String type) {
        String sql;
        try (Connection connection = DBConnection.getConnection()) {
            LOG.info("Установлено соединение с базой данной");
            if (contact != null) {
                switch (type) {
                    case "new_c":
                        sql = "INSERT INTO contact_card (cc_mid, cc_data, cc_communication) VALUES (?,?,?);";
                        PreparedStatement psNew = connection.prepareStatement(sql);
                    {
                        psNew.setInt(1, contact.getM_id());
                        psNew.setString(2, contact.getValue());
                        psNew.setString(3, contact.getCommunication());
                        psNew.executeUpdate();
                    }
                    LOG.info("Создана новая запись : " + contact.getM_id() + " " + contact.getCommunication());
                    break;
                    case "edit_c":
                        sql = "UPDATE contact_card SET cc_communication=?, cc_data =? WHERE cc_id = " + contact.getId();
                        PreparedStatement psEdit = connection.prepareStatement(sql);
                    {
                        psEdit.setString(1, contact.getCommunication());
                        psEdit.setString(2, contact.getValue());
                        psEdit.executeUpdate();
                    }
                    LOG.info("Запись изменена : " + contact.getM_id() + " " + contact.getCommunication());
                    break;
                    case "delete_c":
                        sql = "DELETE FROM contact_card WHERE cc_id = " + contact.getId();
                        PreparedStatement psDelete = connection.prepareStatement(sql);
                        psDelete.executeUpdate();
                        LOG.info("Запись удалена : " + contact.getCommunication() + " " + contact.getValue());
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.info(e.getMessage());
        }
    }

    private static void psSetData(Person person, PreparedStatement psNew) throws SQLException {
        psNew.setString(1, person.getName());
        psNew.setString(2, person.getSecondName());
        psNew.setString(3, person.getSurname());
        psNew.setString(4, person.getAdress());
    }
}
