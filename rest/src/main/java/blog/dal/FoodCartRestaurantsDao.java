package blog.dal;

import blog.model.Cuisines;
import blog.model.FoodCartRestaurants;
import blog.model.Restaurants;
import blog.model.SitDownRestaurants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodCartRestaurantsDao extends RestaurantsDao{

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static FoodCartRestaurantsDao instance = null;
  protected FoodCartRestaurantsDao() {
    connectionManager = new ConnectionManager();
  }
  public static FoodCartRestaurantsDao getInstance() {
    if(instance == null) {
      instance = new FoodCartRestaurantsDao();
    }
    return instance;
  }

  /**
   * Save the restaurant instance by storing it in your MySQL instance.
   * This runs a INSERT statement.
   */
  public FoodCartRestaurants create(FoodCartRestaurants foodCartRestaurant) throws SQLException {
    create(new Restaurants(foodCartRestaurant.getName(), foodCartRestaurant.getDescription(),
        foodCartRestaurant.getMenu(), foodCartRestaurant.getHours(), foodCartRestaurant.getActive(),
        foodCartRestaurant.getCuisine(), foodCartRestaurant.getStreet1(),
        foodCartRestaurant.getStreet2(), foodCartRestaurant.getCity(), foodCartRestaurant.getState(),
        foodCartRestaurant.getZip(), foodCartRestaurant.getCompanyName()));
    String insertRestaurant = "INSERT INTO FoodCartRestaurant(RestaurantId,licensed) VALUES((select restaurantId from restaurants where name=? and description=? and menu=? and hours=? and active=? and cuisinetype=? and street1=? and street2=? and city=? and state=? and zip=? and companyName=?),?);";

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
      insertStmt.setString(1, foodCartRestaurant.getName());
      insertStmt.setString(2, foodCartRestaurant.getDescription());
      insertStmt.setString(3, foodCartRestaurant.getMenu());
      insertStmt.setString(4, foodCartRestaurant.getHours());
      insertStmt.setBoolean(5, foodCartRestaurant.getActive());
      insertStmt.setString(6, foodCartRestaurant.getCuisine().toString());
      insertStmt.setString(7, foodCartRestaurant.getStreet1());
      insertStmt.setString(8, foodCartRestaurant.getStreet2());
      insertStmt.setString(9, foodCartRestaurant.getCity());
      insertStmt.setString(10, foodCartRestaurant.getState());
      insertStmt.setInt(11, foodCartRestaurant.getZip());
      insertStmt.setString(12, foodCartRestaurant.getCompanyName());
      insertStmt.setBoolean(13, foodCartRestaurant.getLicensed());
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
      return foodCartRestaurant;
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

  public FoodCartRestaurants getFoodCartRestaurantById(int foodCartRestaurantId) throws SQLException {
    String selectRestaurant = "SELECT Name,Description,Menu,Hours,Active,Cuisinetype,Street1,Street2,City,State,Zip,CompanyName,foodCartRestaurant.licensed as licensed FROM foodCartRestaurant inner join Restaurants on foodCartRestaurant.RestaurantId = Restaurants.RestaurantId WHERE foodcartrestaurant.restaurantId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRestaurant);
      selectStmt.setInt(1, foodCartRestaurantId);
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
        Boolean licensed = results.getBoolean("licensed");
        FoodCartRestaurants restaurant = new FoodCartRestaurants(name,description,menu,hours,active, Cuisines.valueOf(cuisine),street1,street2,city,state,zip,companyName, licensed);
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

  public List<FoodCartRestaurants> getFoodCartRestaurantsByCompanyName(String companyName) throws SQLException{
    List<FoodCartRestaurants> restaurants = new ArrayList<FoodCartRestaurants>();
    String selectRestaurant = "SELECT Name,Description,Menu,Hours,Active,Cuisinetype,Street1,Street2,City,State,Zip,CompanyName,foodcartrestaurant.licensed as licensed FROM FoodCartRestaurant inner join Restaurants on FoodCartRestaurant.RestaurantId = Restaurants.RestaurantId WHERE companyname=?;";
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
        Boolean licensed = results.getBoolean("licensed");
        FoodCartRestaurants restaurant = new FoodCartRestaurants(name,description,menu,hours,active, Cuisines.valueOf(cuisine),street1,street2,city,state,zip,resultCompanyName, licensed);
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

  public FoodCartRestaurants delete(FoodCartRestaurants foodCartRestaurant) throws SQLException{
    String deleteFoodCartRestaurants = "DELETE FoodCartRestaurant FROM FoodCartRestaurant inner join Restaurants on FoodCartRestaurant.RestaurantId = Restaurants.RestaurantId WHERE Restaurants.name=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteFoodCartRestaurants);
      deleteStmt.setString(1, foodCartRestaurant.getName());
      int affectedRows = deleteStmt.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("No records available to delete for UserName=" + foodCartRestaurant.getName());
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
      super.delete(foodCartRestaurant);

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
