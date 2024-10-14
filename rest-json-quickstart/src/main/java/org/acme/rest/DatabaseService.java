package org.acme.rest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DatabaseService {
    
@Inject
    DataSource dataSource;  // Iniettare il datasource configurato

    @Inject
    EntityManager entityManager;

    @Transactional
    public String getDatabaseName(){
        // Query SQL per ottenere il nome del database
        String databaseName = (String) entityManager.createNativeQuery("SELECT DATABASE()").getSingleResult();
        return databaseName;
    }

    public String getPetName(){
        Pet pet = Pet.findAll().firstResult();
        return pet.getName();
    }
    
    @Transactional
    public List<Pet> getAllPets2() {
        return Pet.listAll();  // Usa Panache per ottenere tutti i Pet
    }

    @Transactional
    public String listTables() {
        try (Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement()) {

            StringBuilder stringBuilder = new StringBuilder();
            // Modifica questa query in base al database che stai usando
            String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='PUBLIC';";
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Tabelle nel database:");
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                stringBuilder.append(tableName);
                stringBuilder.append("\n");
            }

            return stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    @Transactional
        public String getAllPets() {
            try (Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()) {

                StringBuilder stringBuilder = new StringBuilder();
                // Modifica questa query in base al database che stai usando
                String query = "SELECT * FROM Pet";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    String tableName = rs.getString("name");
                    stringBuilder.append(tableName);
                    stringBuilder.append("\n");
                }

                return stringBuilder.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }
    
}
