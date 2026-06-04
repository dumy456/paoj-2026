package com.pao.proiect.licitatie.service;

import com.pao.proiect.licitatie.model.Utilizator;
import com.pao.proiect.licitatie.repository.UtilizatorRepository;
import java.util.List;

public class UserService {
    private static UserService instance;
    private final UtilizatorRepository userRepo = new UtilizatorRepository();
    private final AuditService audit = AuditService.getInstance();

    private UserService() {}

    public static synchronized UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }

    public void inregistreazaUtilizator(Utilizator u) {
        audit.logActiune("inregistreaza_utilizator");
        userRepo.save(u);
    }

    public void stergeUtilizator(int id) {
        audit.logActiune("sterge_utilizator");
        userRepo.delete(id);
    }

    public boolean existaEmail(String email) {
        audit.logActiune("verifica_email");
        return userRepo.checkEmailExists(email);
    }

    public void afiseazaUtilizatori() {
        audit.logActiune("listeaza_utilizatori");
        List<Utilizator> list = userRepo.findAll();
        list.forEach(System.out::println);
    }
}