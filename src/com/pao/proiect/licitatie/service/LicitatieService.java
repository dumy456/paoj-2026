package com.pao.proiect.licitatie.service;

import com.pao.proiect.licitatie.exception.OfertaInvalidaException;
import com.pao.proiect.licitatie.model.*;
import com.pao.proiect.licitatie.repository.*;
import com.pao.proiect.licitatie.util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class LicitatieService {
    private static LicitatieService instance;
    private final ProdusRepository produsRepo = new ProdusRepository();
    private final BidRepository bidRepo = new BidRepository();
    private final UtilizatorRepository userRepo = new UtilizatorRepository();
    private final AuditService audit = AuditService.getInstance();

    private LicitatieService() {}

    public static synchronized LicitatieService getInstance() {
        if (instance == null) instance = new LicitatieService();
        return instance;
    }

    public void adaugaProdus(Produs p) {
        audit.logActiune("adauga_produs");
        produsRepo.save(p);
    }

    public Produs cautaProdus(int id) {
        audit.logActiune("cauta_produs");
        return produsRepo.findById(id).orElse(null);
    }

    public void afiseazaProduseSortateDupaPret() {
        audit.logActiune("afiseaza_produse_sortate");
        List<Produs> list = produsRepo.findAll();
        Collections.sort(list);
        list.forEach(System.out::println);
    }

    // CERINȚA 2: TRANZACȚIE JDBC EXPLICITĂ
    // Afectează 3 tabele: Modifică Sold Licitator -> Modifică Preț Curent Produs -> Inserează Bid
    public void plaseazaOferta(Licitator licitator, int produsId, double suma) throws OfertaInvalidaException {
        audit.logActiune("plaseaza_oferta");
        Connection conn = DatabaseConnection.getInstance().getConnection();

        Produs p = produsRepo.findById(produsId).orElseThrow(() -> new RuntimeException("Produsul nu exista!"));
        if (suma <= p.getPretPornire()) throw new OfertaInvalidaException("Suma e prea mica!");
        if (licitator.getBuget() < suma) throw new OfertaInvalidaException("Fonduri insuficiente!");

        try {
            conn.setAutoCommit(false);

            licitator.setBuget(licitator.getBuget() - suma);
            userRepo.update(licitator);

            p.setPretPornire(suma);
            produsRepo.update(p);

            InregistrareBid bid = new InregistrareBid(produsId, licitator.getId(), suma);
            bidRepo.save(bid);

            conn.commit();
            System.out.println("Tranzactie reusita! Oferta plasata cu succes de " + licitator.getNume());
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.err.println("Tranzactia a esuat. S-a efectuat rollback!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Eroare la procesarea tranzactiei: " + e.getMessage(), e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // CERINȚA 3: INTEROGĂRI AVANSATE CU JOIN (Exemplul 1)
    public void afiseazaProduseAccesibile(double bugetMaxim) {
        audit.logActiune("afiseaza_produse_accesibile");
        String sql = "SELECT p.*, u.nume AS nume_vanzator FROM produs p " +
                "JOIN utilizator u ON p.vanzator_id = u.id WHERE p.pret_pornire <= ?";

        try (PreparedStatement ps = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            ps.setDouble(1, bugetMaxim);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n--- Produse accesibile sub " + bugetMaxim + " RON ---");
                while (rs.next()) {
                    System.out.println("Produs: " + rs.getString("nume") + " | Pret: " + rs.getDouble("pret_pornire") + " | Vanzator: " + rs.getString("nume_vanzator"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // CERINȚA 3: INTEROGĂRI AVANSATE CU JOIN (Exemplul 2)
    public long numarOfertePerProdus(int produsId) {
        audit.logActiune("numar_oferte_produs");
        String sql = "SELECT COUNT(*) FROM inregistrare_bid b JOIN produs p ON b.produs_id = p.id WHERE p.id = ?";
        try (PreparedStatement ps = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, produsId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    // CERINȚA 3: INTEROGĂRI AVANSATE CU JOIN (Exemplul 3)
    public void afiseazaIstoricComplect() {
        audit.logActiune("afiseaza_istoric_global");
        String sql = "SELECT b.suma, b.timestamp, p.nume AS nume_produs, u.nume AS nume_licitator FROM inregistrare_bid b " +
                "JOIN produs p ON b.produs_id = p.id " +
                "JOIN utilizator u ON b.licitator_id = u.id ORDER BY b.timestamp DESC";

        try (PreparedStatement ps = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n--- ISTORIC COMPLET AVANSAT DE LICITARE (JOIN) ---");
            while (rs.next()) {
                System.out.println("Utilizatorul [" + rs.getString("nume_licitator") + "] a oferit " +
                        rs.getDouble("suma") + " RON pentru [" + rs.getString("nume_produs") + "] la data de " + rs.getTimestamp("timestamp"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double calculeazaValoareTotalaStoc() {
        audit.logActiune("calcul_valoare_stoc");
        return produsRepo.findAll().stream().mapToDouble(Produs::getPretPornire).sum();
    }
}