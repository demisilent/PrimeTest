package com.primetest.database;

import com.primetest.admin.Account;
import com.primetest.beans.AdminBean;
import com.primetest.contact.Contact;
import com.primetest.contact.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DBLoadData {
    private static final Logger LOG = LoggerFactory.getLogger(DBLoadData.class);

    public static List<Person> getContacts() {
        List<Person> people = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            LOG.info("Соединение с базой данных");
            StringBuilder sql = new StringBuilder().append("SELECT * FROM persons_list ");
            if (!AdminBean.getAccount().getRole().equals("admin")) {
                sql.append("WHERE p_mid = ");
                sql.append(AdminBean.getAccount().getId());
            }
            LOG.info("Получение списка абонентов");
            PreparedStatement ps1 = connection.prepareStatement(String.valueOf(sql));
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String sName = rs.getString(3);
                String surname = rs.getString(4);
                int masterID = rs.getInt(5);
                String adress = rs.getString(6);
                List<Contact> contactList = getContactDetails(connection, id);
                people.add(new Person(id, name, sName, surname, contactList, adress, getLoginForAdminTable(connection, masterID)));
            }
        } catch (SQLException e) {
            LOG.warn("Соединение с базой данных не установлено");
            e.printStackTrace();
        }
        return people;
    }

    private static List<Contact> getContactDetails(Connection connection, int id) throws SQLException {
        List<Contact> contactList = new ArrayList<>();
        String sql = "SELECT * FROM contact_card WHERE cc_mid = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int mID = rs.getInt(1);
            String comm = rs.getString(2);
            String value = rs.getString(3);
            int iid = rs.getInt(4);
            contactList.add(new Contact(iid, mID, comm, value));
        }
        return contactList;
    }

    public static Account getAccount(String login) {
        Account account = new Account();
        try (Connection connection = DBConnection.getConnection()) {
            LOG.info("Соединение с базой данных");
            if (checkUserID(connection, login)) {
                PreparedStatement ps1 = connection.prepareStatement("SELECT u_password, u_role, u_id FROM users_list WHERE u_name = ? ");
                ps1.setString(1, login);
                ResultSet rs = ps1.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        account.setUsername(login);
                        account.setPassword(rs.getString(1));
                        account.setRole(rs.getString(2));
                        account.setId(rs.getInt(3));
                    }
                    LOG.info("Получение данных :" + account.getUsername());
                    return account;
                }
            } else {
                LOG.info("Такого аккаунта не существует  -  " + login);
            }
        } catch (SQLException e) {
            LOG.warn("Соединение с базой данных не установлено");
            e.printStackTrace();
        }
        return account;
    }

    static boolean checkUserID(Connection connection, String login) throws SQLException {
        String sql = "SELECT * FROM users_list WHERE u_name = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    private static String getLoginForAdminTable(Connection connection, int mID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT u_name FROM users_list WHERE u_id = ?");
        ps.setInt(1, mID);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "";
    }
}
