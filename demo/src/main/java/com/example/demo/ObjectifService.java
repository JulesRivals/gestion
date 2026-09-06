package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class ObjectifService {
    private final ObjectifEpargneDAO dao;

    public ObjectifService(ObjectifEpargneDAO dao) {
        this.dao = dao;
    }

    public ObjectifEpargne getForUser(int userId) {
        return dao.findByUserId(userId);
    }

    public void save(ObjectifEpargne obj) {
        dao.save(obj);
    }

    public void update(ObjectifEpargne obj) {
        dao.update(obj);
    }
}
