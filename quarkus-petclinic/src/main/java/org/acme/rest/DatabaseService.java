package org.acme.rest;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class DatabaseService {

  @Inject
  DataSource dataSource; // Iniettare il datasource configurato

  @Inject
  EntityManager entityManager;

  // TEST methods
  // ---------------------------------------------------------------------------------------
  @Transactional
  public String getDatabaseName() {
    // Query SQL per ottenere il nome del database
    String databaseName = (String) entityManager.createNativeQuery("SELECT DATABASE()").getSingleResult();
    return databaseName;
  }

  /**
   * returns all tables of DB
   * 
   * @return
   */
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

  /**
   * Test Method that retuns all instanced of the database
   * 
   * @return
   */
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

  // Owner methods
  // ---------------------------------------------------------------------------------------

  @Transactional
  public Owner getOwner(int ownerId) {
    String query = "SELECT * FROM owners WHERE id = ?";
    Owner owner = new Owner();
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, ownerId);
      ResultSet rs = stmt.executeQuery();
      if (!rs.next())
        return null;
      owner.setId(rs.getInt("id"));
      owner.setFirstName(rs.getString("first_name"));
      owner.setLastName(rs.getString("last_name"));
      owner.setCity(rs.getString("city"));
      owner.setTelephone(rs.getString("telephone"));

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return owner;
  }

  @Transactional
  public void addOwner(String firstName, String lastName, String address, String city, String telephone) {
    String query = "INSERT INTO owners (first_name, last_name, address, city, telephone) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, firstName);
      stmt.setString(2, lastName);
      stmt.setString(3, address);
      stmt.setString(4, city);
      stmt.setString(5, telephone);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Transactional
  public List<Owner> listOwners(String lastName) {
    String query = "SELECT * FROM owners WHERE last_name LIKE ?";
    List<Owner> owners = new ArrayList<>();
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, "%" + lastName + "%");
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        Owner owner = new Owner();
        owner.setId(rs.getInt("id"));
        owner.setFirstName(rs.getString("first_name"));
        owner.setLastName(rs.getString("last_name"));
        owner.setCity(rs.getString("city"));
        owner.setTelephone(rs.getString("telephone"));

        owners.add(owner);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return owners;
  }

  @Transactional
  public void updateOwner(int ownerId, String firstName, String lastName, String address, String city,
      String telephone) {
    String query = "UPDATE owners SET first_name = ?, last_name = ?, address = ?, city = ?, telephone = ? WHERE id = ?";
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, firstName);
      stmt.setString(2, lastName);
      stmt.setString(3, address);
      stmt.setString(4, city);
      stmt.setString(5, telephone);
      stmt.setInt(6, ownerId);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Transactional
  public void deleteOwner(int ownerId) {
    String query = "DELETE FROM owners WHERE id = ?";
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, ownerId);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Transactional
  public void addPetToOwner(int ownerId, String petName, String birthDate, int typeId) {
    String query = "INSERT INTO pets (name, birth_date, owner_id, type_id) VALUES (?, ?, ?, ?)";
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, petName);
      stmt.setDate(2, Date.valueOf(birthDate));
      stmt.setInt(3, ownerId);
      stmt.setInt(4, typeId);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // PET methods
  // ----------------------------------------------------------------------------------------------

  @Transactional
  public Pet getPet(int petId) throws NotFoundException {
    String query = "SELECT * FROM pets WHERE id = ?";
    Pet pet = null;
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, petId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        pet = new Pet();
        pet.setId(rs.getInt("id"));
        pet.setName(rs.getString("name"));
        pet.setBirthDate(rs.getDate("birth_date").toLocalDate());
        // TODO: references Owner
        pet.setOwner(getOwner(rs.getInt("owner_id")));

        pet.setType(getType(rs.getInt("type_id")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return pet;
  }

  @Transactional
  public List<Pet> listPets() {
    String query = "SELECT * FROM pets";
    List<Pet> pets = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
      while (rs.next()) {
        Pet pet = new Pet();
        pet.setId(rs.getInt("id"));
        pet.setName(rs.getString("name"));
        pet.setBirthDate(rs.getDate("birth_date").toLocalDate());
        // TODO: aad reference if are presents
        pets.add(pet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return pets;
  }

  @Transactional
  public void addPet(Pet pet) { // Tested
    String query = "INSERT INTO pets (name, birth_date,type_id) VALUES (?,?,?)";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, pet.getName());
      stmt.setDate(2, Date.valueOf(pet.getBirthDate()));
      stmt.setInt(3, pet.getType().getId());
      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  // VET methods
  // ----------------------------------------------------------------------------------------------
  @Transactional
  public void addVet(String firstName, String lastName, List<Specialty> specialties) {
    String query = "INSERT INTO vets (first_name, last_name) VALUES (?, ?)";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, firstName);
      stmt.setString(2, lastName);
      stmt.executeUpdate();

      ResultSet generatedKeys = stmt.getGeneratedKeys();
      if (generatedKeys.next()) {
        int vetId = generatedKeys.getInt(1);
        for (Specialty specialty : specialties) {
          String specialtyQuery = "INSERT INTO vet_specialties (vet_id, specialty_id) VALUES (?, ?)";
          try (PreparedStatement specialtyStmt = conn.prepareStatement(specialtyQuery)) {
            specialtyStmt.setInt(1, vetId); // BUG: must check
            specialtyStmt.executeUpdate();
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Transactional
  public List<Vet> listVets() {
    String query = "SELECT * FROM vets";
    List<Vet> vets = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
      while (rs.next()) {
        Vet vet = new Vet();
        vet.setId(rs.getInt("id"));
        vet.setFirstName(rs.getString("first_name"));
        vet.setLastName(rs.getString("last_name"));
        // Assume we can fetch specialties in another query or map
        vets.add(vet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return vets;
  }

  // Specialty methods
  // ----------------------------------------------------------------------------------------------
  @Transactional
  public void addSpecialty(String specialtyName) {
    String query = "INSERT INTO specialties (name) VALUES (?)";
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, specialtyName);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Transactional
  public List<Specialty> listSpecialties() {
    String query = "SELECT * FROM specialties";
    List<Specialty> specialties = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
      while (rs.next()) {
        Specialty specialty = new Specialty();
        specialty.setName(rs.getString("name"));
        specialties.add(specialty);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return specialties;
  }

  // Visit Methods
  // -----------------------------------------------------------------------------------------------
  @Transactional
  public void addVisit(int petId, String visitDate, String description) {
    String query = "INSERT INTO visits (pet_id, visit_date, description) VALUES (?, ?, ?)";
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, petId);
      stmt.setDate(2, Date.valueOf(visitDate));
      stmt.setString(3, description);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Transactional
  public List<Visit> listVisits() {
    String query = "SELECT * FROM visits";
    List<Visit> visits = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
      while (rs.next()) {
        Visit visit = new Visit();
        visit.setId(rs.getInt("id"));
        visit.setDate(rs.getDate("visit_date").toLocalDate());
        visit.setDescription(rs.getString("description"));
        visits.add(visit);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return visits;
  }

  // Types Methods
  // -----------------------------------------------------------------------------------------------

  /**
   * @param id
   * @return Type if found, null if not found
   */
  @Transactional
  public Type getType(int id) {
    Type type = new Type();

    String query = "SELECT * FROM types WHERE id=?";

    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (!rs.next())
        return null;
      type.setId(id);
      type.setName(rs.getString("name"));

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return type;

  }

  @Transactional
  public void addPetType(String typeName) {
    String query = "INSERT INTO types (name) VALUES (?)";
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, typeName);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Transactional
  public List<Type> listPetTypes() {
    String query = "SELECT * FROM types";
    List<Type> types = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
      while (rs.next()) {
        Type type = new Type();
        type.setId(rs.getInt("id"));
        type.setName(rs.getString("name"));
        types.add(type);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return types;
  }

}
