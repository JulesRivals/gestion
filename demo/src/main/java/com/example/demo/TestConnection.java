package com.example.demo;

import java.sql.Connection;

import java.sql.SQLException;
public class TestConnection {
	
	public static void main(String[] args) {
	try {
		Connection conn = Bdd.getConnection();
		if (conn != null) {
			System.out.println("✅ Connexion à PostgreSQL réussie !");
		}
		conn.close();
		} catch (SQLException e) {
			System.out.println("❌ Échec de la connexion");
			e.printStackTrace();
		}
	}
}
