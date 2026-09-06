package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class RevenuService {
    private final RevenuDAO dao;

    public RevenuService(RevenuDAO dao) { this.dao = dao; }

    public Revenu getForUser(int userId) { return dao.findByUserId(userId); }

    public void save(Revenu r) { dao.save(r); }
}
