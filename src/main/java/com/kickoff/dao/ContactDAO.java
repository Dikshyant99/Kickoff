package com.kickoff.dao;

import com.kickoff.util.DBUtil;
import java.sql.*;
import java.util.*;
import com.kickoff.model.Contact;
import com.kickoff.util.DBUtil;


public class ContactDAO {

    private Connection conn;

    public ContactDAO() {
        try {
            conn = DBUtil.getConnection();
        } catch (Exception e){
            e.printStackTrace();

        }    }

    public boolean saveContact(Contact contact) {

        boolean result = false;

        String sql = "INSERT INTO Contact "
                + "(first_name, last_name, email, phone, subject, message) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getLastName());
            ps.setString(3, contact.getEmail());
            ps.setString(4, contact.getPhone());
            ps.setString(5, contact.getSubject());
            ps.setString(6, contact.getMessage());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
