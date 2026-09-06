package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {
    private final BudgetDAO dao;

    public BudgetService(BudgetDAO dao) { this.dao = dao; }

    public List<Budget> listForUser(int userId) { return dao.findByUserId(userId); }

    public void save(Budget b) { dao.save(b); }

    public void update(Budget b) { dao.update(b); }
}
