package com.pao.proiect.licitatie.repository;

import com.pao.proiect.licitatie.model.Produs;
import com.pao.proiect.licitatie.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdusRepository implements Repository<Produs, Integer> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Produs p) {
        String sql = "INSERT INTO produs (nume, pret_pornire, vanzator_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNume());
            ps.setDouble(2, p.getPretPornire());
            ps.setInt(3, p.getVanzatorId());
            ps.executeUpdate();
            try (ResultSet gk = ps.getGeneratedKeys()) {
                if (gk.next()) p.setId(gk.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Produs> findById(Integer id) {
        String sql = "SELECT * FROM produs WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produs p = new Produs(rs.getInt("id"), rs.getString("nume"), rs.getDouble("pret_pornire"), rs.getInt("vanzator_id"));
                    return Optional.of(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Produs> findAll() {
        List<Produs> list = new ArrayList<>();
        String sql = "SELECT * FROM produs";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Produs(rs.getInt("id"), rs.getString("nume"), rs.getDouble("pret_pornire"), rs.getInt("vanzator_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void update(Produs p) {
        String sql = "UPDATE produs SET nume = ?, pret_pornire = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getNume());
            ps.setDouble(2, p.getPretPornire());
            ps.setInt(3, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM produs WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}