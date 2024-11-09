package org.acme.rest;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
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

  // NOTE: Owner methods
  // ---------------------------------------------------------------------------------------

  /**
   * @param ownerId
   * @return owner with that id if exits or null if it doesn't
   * @throws SQLException
   */
  @Transactional
  public Owner getOwner(long ownerId) throws SQLException {
    String query = "SELECT * FROM owners WHERE id = ?";
    Owner owner = new Owner();
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setLong(1, ownerId);
      ResultSet rs = stmt.executeQuery();
      if (!rs.next()) {
        return null;
      }
      owner.setId(rs.getInt("id"));
      owner.setFirstName(rs.getString("first_name"));
      owner.setLastName(rs.getString("last_name"));
      owner.setCity(rs.getString("city"));
      owner.setTelephone(rs.getString("telephone"));

    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException();
    }
    return owner;
  }

  /*
   *
   * try (Connection conn = dataSource.getConnection();
   * PreparedStatement stmt = conn.prepareStatement(query,
   * Statement.RETURN_GENERATED_KEYS)) {
   * stmt.setString(1, pet.getName());
   * stmt.setDate(2, Date.valueOf(pet.getBirthDate()));
   * stmt.setLong(3, pet.getType().getId());
   * stmt.executeUpdate();
   * try (ResultSet keys = stmt.getGeneratedKeys()) {
   * if (!keys.next()) {
   * throw new SQLException("No key");
   * }
   * Long key = keys.getLong(1);
   * pet.setId(key.intValue());
   * 
   * }
   * 
   * } catch (SQLException e) {
   * e.printStackTrace();
   * }
   */
  @Transactional
  public void addOwner(Owner owner) throws SQLException, IllegalArgumentException {
    String query = "INSERT INTO owners (first_name, last_name, address, city, telephone) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      if (owner.getFirstName() == null || owner.getFirstName().isEmpty() ||
          owner.getLastName() == null || owner.getLastName().isEmpty() ||
          owner.getAddress() == null || owner.getAddress().isEmpty() ||
          owner.getCity() == null || owner.getCity().isEmpty() ||
          owner.getTelephone() == null || owner.getTelephone().isEmpty()) {

        throw new IllegalArgumentException("All fields of the owner must be filled.");
      }
      stmt.setString(1, owner.getFirstName());
      stmt.setString(2, owner.getLastName());
      stmt.setString(3, owner.getAddress());
      stmt.setString(4, owner.getCity());
      stmt.setString(5, owner.getTelephone());
      stmt.executeUpdate();

      try (ResultSet rs_keys = stmt.getGeneratedKeys()) {
        rs_keys.next();
        owner.setId(rs_keys.getLong(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException();
    }
  }

  @Transactional
  public List<Owner> listOwners() throws SQLException {
    String query = "SELECT * FROM owners";
    List<Owner> owners = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        Owner owner = new Owner();
        owner.setId(rs.getInt("id"));
        owner.setFirstName(rs.getString("first_name"));
        owner.setLastName(rs.getString("last_name"));
        owner.setCity(rs.getString("city"));
        owner.setAddress(rs.getString("address"));
        owner.setTelephone(rs.getString("telephone"));

        owners.add(owner);
      }
      return owners;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException();
    }
  }

  /**
   * update Owner details
   * 
   * @param ownerId
   * @param owner
   * @return the owners with the id
   * @throws SQLException
   * @throws NotFoundException
   * @throws IllegalArgumentException
   */
  public Owner updateOwner(long ownerId, Owner owner) throws SQLException, NotFoundException, IllegalArgumentException {

    String query = "UPDATE owners SET first_name = ?, last_name = ?, address = ?, city = ?, telephone = ? WHERE id = ?";
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

      if (getOwner(ownerId) == null) {
        throw new NotFoundException();
      }

      if (owner.getFirstName() == null || owner.getFirstName().isEmpty() ||
          owner.getLastName() == null || owner.getLastName().isEmpty() ||
          owner.getAddress() == null || owner.getAddress().isEmpty() ||
          owner.getCity() == null || owner.getCity().isEmpty() ||
          owner.getTelephone() == null || owner.getTelephone().isEmpty()) {

        throw new IllegalArgumentException("All fields of the owner must be filled.");
      }

      stmt.setString(1, owner.getFirstName());
      stmt.setString(2, owner.getLastName());
      stmt.setString(3, owner.getAddress());
      stmt.setString(4, owner.getCity());
      stmt.setString(5, owner.getTelephone());
      stmt.setLong(6, ownerId);
      stmt.executeUpdate();

      owner.setId(ownerId);
      return owner;

    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException();
    }
  }

  /**
   * Delete the owner with that id
   * 
   * @param ownerId
   * @return the owner deleted, null if has a relation
   * @throws NotFoundException
   * @throws SQLException
   */
  public Owner deleteOwner(int ownerId) throws NotFoundException, SQLException, ObjectReferenceException {

    Owner owner = getOwner(ownerId);
    if (owner == null) {
      throw new NotFoundException("Owner not found");
    }

    String query = "DELETE FROM owners WHERE id = ?";
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, ownerId);
      stmt.executeUpdate();

      return owner;
    } catch (SQLException e) {
      e.printStackTrace();
      if (e.getSQLState().equals("23503")) {
        throw new ObjectReferenceException();

      }

      throw new SQLException();

    }
  }

  private Pet getPetByName(String name) throws SQLException, NotFoundException {
    Pet pet = new Pet();
    String query = "SELECT * FROM pets WHERE name = ?";

    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);) {
      stmt.setString(1, name);
      ResultSet rs = stmt.executeQuery();
      if (!rs.next()) {
        throw new NotFoundException("Pet not found from name");
      }

      pet.setId(rs.getLong("id"));
      pet.setName(name);
      pet.setBirthDate(LocalDate.parse(rs.getDate("birth_date").toString()));
      pet.setType(getType(rs.getLong("type_id")));
      long ownerId = rs.getLong("owner_id");
      if (ownerId != 0) {
        pet.setOwner(getOwner(ownerId));
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException();
    }
    return pet;

  }

  @Transactional
  public void addPetToOwner(long ownerId, Pet pet) throws SQLException, NotFoundException {
    String query = "UPDATE pets SET owner_id=? WHERE id=?";
    System.out.println("------------------------------------");
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setLong(1, ownerId);
      Pet petCaughtByName = getPetByName(pet.getName());
      pet.setId(petCaughtByName.getId());
      stmt.setLong(2, petCaughtByName.getId());
      int affected_rows = stmt.executeUpdate();
      if (affected_rows == 0) {
        throw new NotFoundException("Owner or Pet not found");
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e.getMessage());
    } catch (NotFoundException e) {
      e.printStackTrace();
      throw new NotFoundException(e.getMessage());
    }
  }

  // PET methods
  // ----------------------------------------------------------------------------------------------

  /**
   * @param petId
   * @return the Pet Found
   * @throws NotFoundException
   */
  public Pet getPet(long petId) throws NotFoundException {
    String query = "SELECT * FROM pets WHERE id = ?";
    Pet pet = null;
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setLong(1, petId);
      ResultSet rs = stmt.executeQuery();
      if (!rs.next()) {
        throw new NotFoundException("Pet not found");
      }
      pet = new Pet();
      pet.setId(rs.getInt("id"));
      pet.setName(rs.getString("name"));
      pet.setBirthDate(rs.getDate("birth_date").toLocalDate());
      pet.setOwner(getOwner(rs.getInt("owner_id")));

      pet.setType(getType(rs.getInt("type_id")));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return pet;
  }

  @Transactional
  public List<Pet> listPets() throws SQLException {
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
        long ownerId = rs.getLong("owner_id");
        if (ownerId != 0) {
          pet.setOwner(getOwner(ownerId));
        }
        long typeID = rs.getLong("type_id");
        if (typeID == 0) {
          throw new SQLException("type ID not found");
        }
        pet.setType(getType(typeID));
        pets.add(pet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException();
    }
    return pets;
  }

  /**
   * @param pet
   *            add a new Pet if the Type exists
   * @exception BadRequestException if type do not exists or its id does not match
   */
  public void addPet(Pet pet) { // Tested
    String query = "INSERT INTO pets (name, birth_date,type_id) VALUES (?,?,?)";

    Type type_from_request = pet.getType();
    System.out.println(type_from_request.getId() + ": TYPE ID");

    Type type = getType(type_from_request.getId());
    System.out.println(type);
    System.out.println(type_from_request.getName() + " == " + type.getName() + "?");
    if (type == null || !type.getName().equals(type_from_request.getName())) {
      System.out.println("bad request" + "\n" + type.toString());
      throw new BadRequestException();
    }
    System.out.println("check passed");
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, pet.getName());
      stmt.setDate(2, Date.valueOf(pet.getBirthDate()));
      stmt.setLong(3, pet.getType().getId());
      stmt.executeUpdate();
      try (ResultSet keys = stmt.getGeneratedKeys()) {
        if (!keys.next()) {
          throw new SQLException("No key");
        }
        Long key = keys.getLong(1);
        pet.setId(key.intValue());

      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public void updatePet(long petId, Pet pet) throws NotFoundException, SQLException {
    if (getPet(petId) == null) {
      throw new NotFoundException();

    }

    String query = "UPDATE pets SET name = ?, birth_date = ?, type_id = ? WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, pet.getName());
      stmt.setDate(2, Date.valueOf(pet.getBirthDate()));
      // WARNING: TYPE id and name mismatch not checked
      stmt.setLong(3, pet.getType().getId());
      stmt.setLong(4, petId);

      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException();
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
  public void addVisit(long petId, String visitDate, String description) {
    String query = "INSERT INTO visits (pet_id, visit_date, description) VALUES (?, ?, ?)";
    try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setLong(1, petId);
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
  public Type getType(long id) {

    Type type = new Type();

    String query = "SELECT * FROM types WHERE id=?";

    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();
      if (!rs.next()) {
        System.out.println("type not found from query where id:" + id);
        return null;
      } else {
        System.out.println("type found:" + rs.getString("name"));
      }

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

  /**
   * @param petId
   * @return
   * @throws NotFoundException
   * @throws SQLException
   */
  public Pet deletePet(long petId) throws NotFoundException, SQLException {

    Pet pet = getPet(petId);

    String query_delete = "DELETE FROM pets WHERE id=?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt_delete = connection.prepareStatement(query_delete);) {

      stmt_delete.setLong(1, petId);
      stmt_delete.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException();
    }

    return pet;

  }

  /**
   * @param ownerId
   * @param petId
   * @return Pet data if that pet is related to that owner
   * @throws SQLException
   * @throws NotFoundException
   */
  public Pet getOwnerPet(long ownerId, long petId) throws SQLException, NotFoundException {

    Pet pet = getPet(petId);
    Owner owner = getOwner(ownerId);
    if (pet.getOwner().getId() != owner.getId()) {
      throw new NotFoundException("Not that owner's pet");
    }
    return pet;

  }
}
