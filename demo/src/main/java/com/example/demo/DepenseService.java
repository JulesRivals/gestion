package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepenseService {
    private final DepenseDAO dao;

    public DepenseService(DepenseDAO dao) { this.dao = dao; }

    public void save(Depense d) { dao.save(d); }

    public List<Depense> recentForUser(int userId, int limit) { return dao.findRecentByUserId(userId, limit); }

    public double totalMonth(int userId) { return dao.sumMonthByUserId(userId); }

    public double totalCategoryMonth(int userId, String categorie) { return dao.sumByCategoryMonth(userId, categorie); }

    public void update(Depense d) { dao.update(d); }

    public void delete(int depenseId, int userId) { dao.delete(depenseId, userId); }
}
