package com.kickoff.controller;

import com.kickoff.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet(asyncSupported=true,urlPatterns={"/HomeServlet"})
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection con = DBUtil.getConnection()) {

            //FEATURED GROUNDS
            // Fetch 3 active grounds for homepage cards
            List<Map<String, Object>> grounds = new ArrayList<>();
            String groundSql = "SELECT ground_id, name, location, city, " +
                    "sport_types, price_per_hour, image_url, is_active " +
                    "FROM grounds WHERE is_active = true " +
                    "ORDER BY created_at DESC LIMIT 3";

            try (PreparedStatement ps = con.prepareStatement(groundSql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> ground = new HashMap<>();
                    ground.put("groundId",     rs.getInt("ground_id"));
                    ground.put("name",         rs.getString("name"));
                    ground.put("location",     rs.getString("location"));
                    ground.put("city",         rs.getString("city"));
                    ground.put("sportTypes",   rs.getString("sport_types"));
                    ground.put("pricePerHour", rs.getString("price_per_hour"));
                    ground.put("imageUrl",     rs.getString("image_url"));
                    ground.put("isActive",     rs.getBoolean("is_active"));
                    grounds.add(ground);
                }
            }

            // FEATURED TEAMS
            // Fetch 3 open recruiting teams for homepage cards
            List<Map<String, Object>> teams = new ArrayList<>();
            String teamSql = "SELECT t.team_id, t.name, t.sport_type, " +
                    "t.location, t.skill_level, t.max_players, " +
                    "t.current_players, t.recruitment_status, " +
                    "u.first_name, u.last_name " +
                    "FROM teams t " +
                    "JOIN users u ON t.captain_id = u.user_id " +
                    "ORDER BY t.created_at DESC LIMIT 3";

            try (PreparedStatement ps = con.prepareStatement(teamSql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> team = new HashMap<>();
                    team.put("teamId",            rs.getInt("team_id"));
                    team.put("name",              rs.getString("name"));
                    team.put("sportType",         rs.getString("sport_type"));
                    team.put("location",          rs.getString("location"));
                    team.put("skillLevel",        rs.getString("skill_level"));
                    team.put("maxPlayers",        rs.getInt("max_players"));
                    team.put("currentPlayers",    rs.getInt("current_players"));
                    team.put("recruitmentStatus", rs.getString("recruitment_status"));
                    team.put("captainName",       rs.getString("first_name") + " " +
                            rs.getString("last_name"));
                    teams.add(team);
                }
            }

            // Pass data to JSP
            request.setAttribute("grounds", grounds);
            request.setAttribute("teams",   teams);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Failed to load data: " + e.getMessage());
        }

        // Forward to Homepage.jsp
        request.getRequestDispatcher("/Pages/Root/Homepage.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}