package com.kickoff.service;

import com.kickoff.dao.AdminDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AdminService {

    private AdminDAO dao = new AdminDAO();

    public List<Map<String, Object>> getAllUsers() throws SQLException {
        return dao.getUsers();
    }

    public List<Map<String, Object>> getAllGrounds() throws SQLException {
        return dao.getGrounds();
    }



    public List<Map<String, Object>> getAllBookings() throws SQLException {
        return dao.getBookings();
    }

    public void addGround(int ownerId, String name, String location,
                          String city, String sportTypes,
                          String price, String description) throws SQLException {
        dao.insertGround(ownerId, name, location, city, sportTypes, price, description);
    }

    public void deleteUser(int id) throws SQLException {
        dao.delete("users", "user_id", id);
    }

    public void softDeleteUser(int id) throws SQLException {
        dao.softDeleteUser(id);
    }

    public void restoreUser(int id) throws SQLException {
        dao.restoreUser(id);
    }


    public void deleteGround(int id) throws SQLException {
        dao.delete("grounds", "ground_id", id);
    }

    public void updateBookingStatus(int id, String status) throws SQLException {
        dao.updateBookingStatus(id, status);
    }

    public int getCount(String table) throws SQLException {
        return dao.getCount(table);
    }

    public List<Map<String, Object>> getRecentUsers() throws SQLException {
        return dao.getRecentUsers();
    }

    public List<Map<String, Object>> getRecentBookings() throws SQLException {
        return dao.getRecentBookings();
    }


}
