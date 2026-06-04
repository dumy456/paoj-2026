package com.pao.proiect.licitatie.repository;

import com.pao.proiect.licitatie.model.*;
import com.pao.proiect.licitatie.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilizatorRepository implements Repository<Utilizator, Integer> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Utilizator u) {
        String sqlUser = "INSERT INTO utilizator (nume, email, tip) VALUES (?, ?, ?)";
        try (PreparedStatement psUser = connection.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {
            psUser.setString(1, u.getNume());
            psUser.setString(2, u.getEmail());
            psUser.setString(3, u.getTip());
            psUser.executeUpdate();

            try (ResultSet generatedKeys = psUser.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    u.setId(generatedId); // Setăm ID-ul generat în obiect
                    
                    if (u instanceof Licitator) {
                        String sqlLic = "INSERT INTO licitator (id, buget) VALUES (?, ?)";
                        try (PreparedStatement psLic = connection.prepareStatement(sqlLic)) {
                            psLic.setInt(1, generatedId);
                            psLic.setDouble(2, ((Licitator) u).getBuget());
                            psLic.executeUpdate();
                        }
                    } else if (u instanceof Vanzator) {
                        String sqlVanz = "INSERT INTO vanzator (id, rating) VALUES (?, ?)";
                        try (PreparedStatement psVanz = connection.prepareStatement(sqlVanz)) {
                            psVanz.setInt(1, generatedId);
                            psVanz.setDouble(2, ((Vanzator) u).getRating());
                            psVanz.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea utilizatorului: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Utilizator> findById(Integer id) {
        String sql = "SELECT u.*, l.buget, v.rating FROM utilizator u " +
                     "LEFT JOIN licitator l ON u.id = l.id " +
                     "LEFT JOIN vanzator v ON u.id = v.id WHERE u.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String tip = rs.getString("tip");
                    String nume = rs.getString("nume");
                    String email = rs.getString("email");
                    if ("LICITATOR".equals(tip)) {
                        return Optional.of(new Licitator(id, nume, email, rs.getDouble("buget")));
                    } else {
                        return Optional.of(new Vanzator(id, nume, email, rs.getDouble("rating")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Utilizator> findAll() {
        List<Utilizator> list = new ArrayList<>();
        String sql = "SELECT u.*, l.buget, v.rating FROM utilizator u " +
                     "LEFT JOIN licitator l ON u.id = l.id " +
                     "LEFT JOIN vanzator v ON u.id = v.id ORDER BY u.nume";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                String email = rs.getString("email");
                String tip = rs.getString("tip");
                if ("LICITATOR".equals(tip)) {
                    list.add(new Licitator(id, nume, email, rs.getDouble("buget")));
                } else {
                    list.add(new Vanzator(id, nume, email, rs.getDouble("rating")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void update(Utilizator u) {
        String sql = "UPDATE utilizator SET nume = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, u.getNume());
            ps.setString(2, u.getEmail());
            ps.setInt(3, u.getId());
            ps.executeUpdate();
            
            if (u instanceof Licitator) {
                String sqlLic = "UPDATE licitator SET buget = ? WHERE id = ?";
                try (PreparedStatement psLic = connection.prepareStatement(sqlLic)) {
                    psLic.setDouble(1, ((Licitator) u).getBuget());
                    psLic.setInt(2, u.getId());
                    psLic.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM utilizator WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean checkEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM utilizator WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}