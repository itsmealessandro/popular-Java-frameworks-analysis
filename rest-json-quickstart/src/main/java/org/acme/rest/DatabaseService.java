package org.acme.rest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
  DataSource dataSource; // Iniettare il datasource configurato

  @Inject
  EntityManager entityManager;

  @Transactional
  public String getDatabaseName() {
    // Query SQL per ottenere il nome del database
    String databaseName = (String) entityManager.createNativeQuery("SELECT DATABASE()").getSingleResult();
    return databaseName;
  }

  public String getPetName() {
    Pet pet = Pet.findAll().firstResult();
    return pet.getName();
  }

  @Transactional
  public List<Pet> getAllPets2() {
    return Pet.listAll(); // Usa Panache per ottenere tutti i Pet
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
    StringBuilder stringBuilder = new StringBuilder();
    String query = "SELECT * FROM pets"; // Assicurati di usare il nome corretto della tabella

    try (Connection conn = dataSource.getConnection()) {
      if (conn == null) {
        return "Connection failed"; // Aggiunto log di errore
      }
      System.out.println("Connection established"); // Log per confermare la connessione

      try (Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery(query)) {

        if (!rs.isBeforeFirst()) {
          return "No pets found"; // Nessun record trovato
        }

        while (rs.next()) {
          String petName = rs.getString("name");
          stringBuilder.append(petName).append("\n");
        }
      }

    } catch (SQLException e) {
      e.printStackTrace(); // Log dell'eccezione
      return "SQL error: " + e.getMessage();
    } catch (Exception e) {
      e.printStackTrace(); // Log di errori generali
      return "Error: " + e.getMessage();
    }

    return stringBuilder.toString();
  }

  public String getAllInstances() {
    StringBuilder result = new StringBuilder();

    String[] tables = { "vets", "specialties", "vet_specialties", "types", "owners", "pets", "visits" };

    try (Connection conn = dataSource.getConnection()) {
      if (conn == null) {
        return "Connection failed"; // Aggiunto log di errore
      }

      for (String table : tables) {
        result.append("Records from table: ").append(table).append("\n");

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table)) {

          // Controlla se ci sono risultati
          if (!rs.isBeforeFirst()) {
            result.append("No records found.\n");
          } else {
            while (rs.next()) {
              StringBuilder row = new StringBuilder();
              int columnCount = rs.getMetaData().getColumnCount();

              for (int i = 1; i <= columnCount; i++) {
                row.append(rs.getMetaData().getColumnName(i)).append(": ")
                    .append(rs.getString(i)).append(", ");
              }
              result.append(row.toString()).append("\n");
            }
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      return "Error: " + e.getMessage();
    }

    return result.toString();
  }

}
