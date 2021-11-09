package blog.dal;

import blog.model.Cuisines;
import blog.model.SitDownRestaurants;
import blog.model.TakeOutRestaurants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TakeOutRestaurantsDao extends RestaurantsDao{

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static TakeOutRestaurantsDao instance = null;
  protected TakeOutRestaurantsDao() {
    connectionManager = new ConnectionManager();
  }
  public static TakeOutRestaurantsDao getInstance() {
    if(instance == null) {
      instance = new TakeOutRestaurantsDao();
    }
    return instance;
  }

  /**
   * Save the restaurant instance by storing it in your MySQL instance.
   * This runs a INSERT statement.
   */
  public TakeOutRestaurants create(TakeOutRestaurants restaurant) throws SQLException {
    String insertRestaurant = "INSERT INTO TakeOutRestaurant(Maxwaittime) VALUES(?);";
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
      insertStmt.setInt(1, restaurant.getMaxWaitTime());
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

  public TakeOutRestaurants getTakeOutRestaurantById(int takeOutRestaurantId) throws SQLException {
    String selectRestaurant = "SELECT Name,Description,Menu,Hours,Active,Cuisine,Street1,Street2,City,State,Zip,CompanyName,Takeoutrestaurant.maxwaittime as maxwaittime FROM Takeoutrestaurant inner join Restaurants on Takeoutrestaurant.RestaurantId = Restaurants.RestaurantId WHERE restaurantId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRestaurant);
      selectStmt.setInt(1, takeOutRestaurantId);
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
        Object cuisine = results.getObject("cuisine");
        String street1 = results.getString("street1");
        String street2 = results.getString("street2");
        String city = results.getString("city");
        String state = results.getString("state");
        Integer zip  =  results.getInt("zip");
        String companyName = results.getString("companyname");
        Integer maxWaitTime = results.getInt("maxwaittime");
        TakeOutRestaurants restaurant = new TakeOutRestaurants(name,description,menu,hours,active, (Cuisines) cuisine,street1,street2,city,state,zip,companyName, maxWaitTime);
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

  public List<TakeOutRestaurants> getTakeOutRestaurantsByCompanyName(String companyName) throws SQLException {
    List<TakeOutRestaurants> restaurants = new ArrayList<TakeOutRestaurants>();
    String selectRestaurant = "SELECT Name,Description,Menu,Hours,Active,Cuisine,Street1,Street2,City,State,Zip,CompanyName,TakeOutRestaurant.maxwaittime as maxwaittime FROM TakeOutRestaurant inner join Restaurants on TakeOutRestaurant.RestaurantId = Restaurants.RestaurantId WHERE companyname=?;";
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
        Object cuisine = results.getObject("cuisine");
        String street1 = results.getString("street1");
        String street2 = results.getString("street2");
        String city = results.getString("city");
        String state = results.getString("state");
        Integer zip  =  results.getInt("zip");
        String resultCompanyName = results.getString("companyname");
        Integer maxWaitTime = results.getInt("maxwaittime");
        TakeOutRestaurants restaurant = new TakeOutRestaurants(name,description,menu,hours,active, (Cuisines) cuisine,street1,street2,city,state,zip,resultCompanyName, maxWaitTime);
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

  public TakeOutRestaurants delete(TakeOutRestaurants takeOutRestaurant) throws SQLException {
    String deleteRestaurant = "DELETE FROM TakeOutRestaurant join Restaurants on TakeOutRestaurant.RestaurantId = Restaurants.RestaurantId WHERE Restaurants.name=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteRestaurant);
      deleteStmt.setString(1, takeOutRestaurant.getName());
      int affectedRows = deleteStmt.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("No records available to delete for UserName=" + takeOutRestaurant.getName());
      }

      // Then also delete from the superclass.
      // Notes:
      // 1. Due to the fk constraint (ON DELETE CASCADE), we could simply call
      //    super.delete() without even needing to delete from Administrators first.
      // 2. BlogPosts has a fk constraint on BlogUsers with the reference option
      //    ON DELETE SET NULL. If the BlogPosts fk reference option was instead
      //    ON DELETE RESTRICT, then the caller would need to delete the referencing
      //    BlogPosts before this BlogUser can be deleted.
      //    Example to delete the referencing BlogPosts:
      //    List<BlogPosts> posts = BlogPostsDao.getBlogPostsForUser(blogUser.getUserName());
      //    for(BlogPosts p : posts) BlogPostsDao.delete(p);
      super.delete(takeOutRestaurant);

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
