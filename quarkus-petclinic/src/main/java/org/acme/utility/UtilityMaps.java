package org.acme.utility;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class UtilityMaps {

  public static HashMap<String, Object> petMap(String id, String name, String birthday, String ownerID,
      String petTypeID) {
    HashMap<String, Object> petHashMap = new HashMap<>();

    petHashMap.put("id", id);
    petHashMap.put("name", name);
    petHashMap.put("birthday", LocalDate.parse(birthday)); // TODO: check if it works
    petHashMap.put("ownerID", Integer.parseInt(ownerID));
    petHashMap.put("petTypeID", Integer.parseInt(petTypeID));

    return petHashMap;

  }

  public static HashMap<String, Object> vetMap(String id, String firstName, String lastName,
      List<String> specialtyIDs) {
    HashMap<String, Object> vetHashMap = new HashMap<>();

    vetHashMap.put("id", id);
    vetHashMap.put("firstName", firstName);
    vetHashMap.put("lastName", lastName);
    vetHashMap.put("specialtyIDs", specialtyIDs.stream().map(Integer::parseInt).toList());

    return vetHashMap;
  }

  public static HashMap<String, Object> specialtyMap(String id, String name) {
    HashMap<String, Object> specialtyHashMap = new HashMap<>();

    specialtyHashMap.put("id", id);
    specialtyHashMap.put("name", name);

    return specialtyHashMap;
  }

  public static HashMap<String, Object> visitMap(String id, String petID, String date, String description) {
    HashMap<String, Object> visitHashMap = new HashMap<>();

    visitHashMap.put("id", id);
    visitHashMap.put("petID", Integer.parseInt(petID));
    visitHashMap.put("date", LocalDate.parse(date)); // Ensure date is in "yyyy-MM-dd" format
    visitHashMap.put("description", description);

    return visitHashMap;
  }

  public static HashMap<String, Object> petTypeMap(String id, String name) {
    HashMap<String, Object> petTypeHashMap = new HashMap<>();

    petTypeHashMap.put("id", id);
    petTypeHashMap.put("name", name);

    return petTypeHashMap;
  }

}
