package com.pao.proiect.licitatie.repository;

import com.pao.proiect.licitatie.model.InregistrareBid;
import com.pao.proiect.licitatie.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BidRepository implements Repository<InregistrareBid, Integer> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(InregistrareBid b) {
        String sql = "INSERT INTO inregistrare_bid (produs_id, licitator_id, suma, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, b.getProdusId());
            ps.setInt(2, b.getLicitatorId());
            ps.setDouble(3, b.getSuma());
            ps.setTimestamp(4, Timestamp.valueOf(b.getTimestamp()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<InregistrareBid> findById(Integer id) { return Optional.empty(); }

    @Override
    public List<InregistrareBid> findAll() {
        List<InregistrareBid> list = new ArrayList<>();
        String sql = "SELECT * FROM inregistrare_bid";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new InregistrareBid(rs.getInt("produs_id"), rs.getInt("licitator_id"), rs.getDouble("suma"), rs.getTimestamp("timestamp").toLocalDateTime()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void update(InregistrareBid entity) {}

    @Override
    public void delete(Integer id) {}
}