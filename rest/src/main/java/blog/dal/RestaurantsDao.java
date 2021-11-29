package blog.dal;

import blog.model.Cuisines;
import blog.model.Restaurants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantsDao {

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static RestaurantsDao instance = null;
  protected RestaurantsDao() {
    connectionManager = new ConnectionManager();
  }
  public static RestaurantsDao getInstance() {
    if(instance == null) {
      instance = new RestaurantsDao();
    }
    return instance;
  }

  /**
   * Save the restaurant instance by storing it in your MySQL instance.
   * This runs a INSERT statement.
   */
  public Restaurants create(Restaurants restaurant) throws SQLException {
    String insertRestaurant = "INSERT INTO Restaurants(Name,Description,Menu,Hours,Active,cuisinetype,Street1,Street2,City,State,Zip,CompanyName) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertRestaurant);
      // PreparedStatement allows us to substitute specific types into the query template.
      // For an overview, see:
      // http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // For nullable fields, you can check the property first and then call setNull()
      // as applicable.
      insertStmt.setString(1, restaurant.getName());
      insertStmt.setString(2, restaurant.getDescription());
      insertStmt.setString(3, restaurant.getMenu());
      insertStmt.setString(4, restaurant.getHours());
      insertStmt.setBoolean(5, restaurant.getActive());
      insertStmt.setString(6, restaurant.getCuisine().toString());
      insertStmt.setString(7, restaurant.getStreet1());
      insertStmt.setString(8, restaurant.getStreet2());
      insertStmt.setString(9, restaurant.getCity());
      insertStmt.setString(10, restaurant.getState());
      insertStmt.setInt(11, restaurant.getZip());
      insertStmt.setString(12, restaurant.getCompanyName());
      // Note that we call executeUpdate(). This is used for a INSERT/UPDATE/DELETE
      // statements, and it returns an int for the row counts affected (or 0 if the
      // statement returns nothing). For more information, see:
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // I'll leave it as an exercise for you to write UPDATE/DELETE methods.
      insertStmt.executeUpdate();

      // Note 1: if this was an UPDATE statement, then the person fields should be
      // updated before returning to the caller.
      // Note 2: there are no auto-generated keys, so no update to perform on the
      // input param person.
      return restaurant;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(insertStmt != null) {
        insertStmt.close();
      }
    }
  }

  /**
   * Get the Persons record by fetching it from your MySQL instance.
   * This runs a SELECT statement and returns a single Persons instance.
   */
  public Restaurants getRestaurantById(int restaurantId) throws SQLException {
    String selectRestaurant = "SELECT Name,Description,Menu,Hours,Active,Cuisinetype,Street1,Street2,City,State,Zip,CompanyName FROM Restaurants WHERE restaurantId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRestaurant);
      selectStmt.setInt(1, restaurantId);
      // Note that we call executeQuery(). This is used for a SELECT statement
      // because it returns a result set. For more information, see:
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
      results = selectStmt.executeQuery();
      // You can iterate the result set (although the example below only retrieves
      // the first record). The cursor is initially positioned before the row.
      // Furthermore, you can retrieve fields by name and by type.
      if(results.next()) {
        String name =results.getString("name");
        String description = results.getString("description");
        String menu = results.getString("menu");
        String hours = results.getString("hours");
        Boolean active = results.getBoolean("active");
        String cuisine = results.getString("Cuisinetype");
        String street1 = results.getString("street1");
        String street2 = results.getString("street2");
        String city = results.getString("city");
        String state = results.getString("state");
        Integer zip  =  results.getInt("zip");
        String companyName = results.getString("companyname");
        Restaurants restaurant = new Restaurants(name,description,menu,hours,active, Cuisines.valueOf(cuisine),street1,street2,city,state,zip,companyName);
        return restaurant;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return null;
  }

  public List<Restaurants> getRestaurantsByCuisine(Cuisines cuisine) throws SQLException{
    List<Restaurants> restaurants = new ArrayList<Restaurants>();
    String selectRestaurant = "SELECT Name,Description,Menu,Hours,Active,Cuisinetype,Street1,Street2,City,State,Zip,CompanyName FROM Restaurants WHERE Cuisinetype=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRestaurant);
      selectStmt.setString(1, cuisine.toString());
      // Note that we call executeQuery(). This is used for a SELECT statement
      // because it returns a result set. For more information, see:
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
      results = selectStmt.executeQuery();
      // You can iterate the result set (although the example below only retrieves
      // the first record). The cursor is initially positioned before the row.
      // Furthermore, you can retrieve fields by name and by type.
      if(results.next()) {
        String name =results.getString("name");
        String description = results.getString("description");
        String menu = results.getString("menu");
        String hours = results.getString("hours");
        Boolean active = results.getBoolean("active");
        String resultCuisine = results.getString("Cuisinetype");
        String street1 = results.getString("street1");
        String street2 = results.getString("street2");
        String city = results.getString("city");
        String state = results.getString("state");
        Integer zip  =  results.getInt("zip");
        String companyName = results.getString("companyname");
        Restaurants restaurant = new Restaurants(name,description,menu,hours,active, Cuisines.valueOf(resultCuisine),street1,street2,city,state,zip,companyName);
        restaurants.add(restaurant);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return restaurants;
  }

  public List<Restaurants> getRestaurantsByCompanyName(String companyName) throws SQLException{
    List<Restaurants> restaurants = new ArrayList<Restaurants>();
    String selectRestaurant = "SELECT Name,Description,Menu,Hours,Active,Cuisinetype,Street1,Street2,City,State,Zip,CompanyName FROM Restaurants WHERE companyname=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRestaurant);
      selectStmt.setString(1, companyName);
      // Note that we call executeQuery(). This is used for a SELECT statement
      // because it returns a result set. For more information, see:
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
      results = selectStmt.executeQuery();
      // You can iterate the result set (although the example below only retrieves
      // the first record). The cursor is initially positioned before the row.
      // Furthermore, you can retrieve fields by name and by type.
      if(results.next()) {
        String name =results.getString("name");
        String description = results.getString("description");
        String menu = results.getString("menu");
        String hours = results.getString("hours");
        Boolean active = results.getBoolean("active");
        String cuisine = results.getString("Cuisinetype");
        String street1 = results.getString("street1");
        String street2 = results.getString("street2");
        String city = results.getString("city");
        String state = results.getString("state");
        Integer zip  =  results.getInt("zip");
        String resultCompanyName = results.getString("companyname");
        Restaurants restaurant = new Restaurants(name,description,menu,hours,active, Cuisines.valueOf(cuisine),street1,street2,city,state,zip,resultCompanyName);
        restaurants.add(restaurant);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return restaurants;
  }

  /**
   * Delete the restaurant instance.
   * This runs a DELETE statement.
   */
  public Restaurants delete(Restaurants restaurant) throws SQLException {
    String deleteRestaurant = "DELETE FROM Restaurants WHERE name=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteRestaurant);
      deleteStmt.setString(1, restaurant.getName());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the Persons instance.
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(deleteStmt != null) {
        deleteStmt.close();
      }
    }
  }

}
