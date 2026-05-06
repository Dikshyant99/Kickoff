package com.kickoff.dao;

import com.kickoff.model.User;
import com.kickoff.util.DBUtil;
import com.kickoff.util.PasswordUtil;
import java.sql.*;

public class UserDAO {

    // CHECK LOGIN
    // Checks if email exists first, then verifies password
    // Returns specific string telling exactly what happened
    // "success" :email found AND password matched
    // "wrong_password": email found BUT password is wrong
    // "user_not_found" : no account with this email in database
    // "error" : something went wrong with database
    public String checkLogin(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND is_deleted = false";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Email was found in database
                // Now check if the entered password matches the stored hash
                String storedHash = rs.getString("password");

                if (PasswordUtil.verify(password, storedHash)) {
                    // Password matched - login success
                    return "success";
                } else {
                    // Email found but password is wrong
                    return "wrong_password";
                }

            } else {
                // No row found with this email
                // Account does not exist
                return "user_not_found";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
    // REGISTER NEW USER
    // Inserts a new user row into the database during registration
    // Returns true if insert succeeded, false if it failed
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users " +
                "(first_name, last_name, email, phone, sport, skill_level, password, role, image) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getSport());
            ps.setString(6, user.getSkillLevel());
            ps.setString(7, user.getPassword());
            ps.setString(8, user.getRole());
            ps.setString(9, user.getImage());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET USER BY EMAIL
    // Returns full User object after successful login
    // Called only after checkLogin() returns "success"
    // Returns null if no user found
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Build User object from database row
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setSport(rs.getString("sport"));
                u.setSkillLevel(rs.getString("skill_level"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                u.setCreatedAt(rs.getString("created_at"));
                u.setImage(rs.getString("image"));
                return u;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===== EMAIL EXISTS =====
    // Checks if email already exists in database
    // Used during registration to prevent duplicate accounts
    public boolean emailExists(String email) {
        String sql = "SELECT user_id FROM users WHERE email = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            return ps.executeQuery().next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // PHONE EXISTS
    // Checks if phone already exists in database
    // Used during registration to prevent duplicate phone numbers
    public boolean phoneExists(String phone) {
        String sql = "SELECT user_id FROM users WHERE phone = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, phone);
            return ps.executeQuery().next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Update user
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET first_name=?, last_name=?, email=?, phone=?, sport=?, skill_level=?,image=? WHERE user_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getSport());
            ps.setString(6, user.getSkillLevel());
            ps.setString(7,user.getImage());
            ps.setInt(8, user.getUserId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // SOFT DELETE
    public boolean softDeleteUser(int userId) {
        String sql = "UPDATE users SET is_deleted = true WHERE user_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
