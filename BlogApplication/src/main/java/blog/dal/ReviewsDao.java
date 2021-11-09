package blog.dal;

import blog.dal22222.PersonsDao;
import blog.model.Persons;
import blog.model.Reviews;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewsDao {
  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static ReviewsDao instance = null;
  protected ReviewsDao() {
    connectionManager = new ConnectionManager();
  }
  public static ReviewsDao getInstance() {
    if(instance == null) {
      instance = new ReviewsDao();
    }
    return instance;
  }

  /**
   * Save the Persons instance by storing it in your MySQL instance.
   * This runs a INSERT statement.
   */
  public Reviews create(Reviews review) throws SQLException {
    String insertReview = "INSERT INTO reviews(Created,Content,Rating,UserName,RestaurantId) VALUES(?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertReview);
      // PreparedStatement allows us to substitute specific types into the query template.
      // For an overview, see:
      // http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // For nullable fields, you can check the property first and then call setNull()
      // as applicable.
      insertStmt.setDate(1, review.getCreated());
      insertStmt.setString(2, review.getContent());
      insertStmt.setLong(3, review.getRating());
      insertStmt.setString(4,review.getUserName());
      insertStmt.setInt(5,review.getRestaurantId());
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
      return review;
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

  public Reviews getReviewById(int reviewId) throws SQLException {
    String selectReview = "SELECT Created,Content,Rating,UserName,RestaurantId FROM reviews WHERE reviewId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReview);
      selectStmt.setInt(1, reviewId);
      // Note that we call executeQuery(). This is used for a SELECT statement
      // because it returns a result set. For more information, see:
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
      results = selectStmt.executeQuery();
      // You can iterate the result set (although the example below only retrieves
      // the first record). The cursor is initially positioned before the row.
      // Furthermore, you can retrieve fields by name and by type.
      if(results.next()) {
        Date created = results.getDate("created");
        String content = results.getString("Content");
        Long rating = results.getLong("rating");
        String userName = results.getString("username");
        Integer restaurantId = results.getInt("restaurantId");
        Reviews review = new Reviews(created,content,rating,userName,restaurantId);
        return review;
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

  public List<Reviews> getReviewsByUserName(String userName) throws SQLException {
    List<Reviews> reviews = new ArrayList<Reviews>();
    String selectReviews =
        "SELECT Created,Content,Rating,UserName,RestaurantId FROM reviews WHERE username=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReviews);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();
      while(results.next()) {
        Date created = results.getDate("created");
        String content = results.getString("Content");
        Long rating = results.getLong("rating");
        String resultUserName = results.getString("username");
        Integer restaurantId = results.getInt("restaurantId");
        Reviews review = new Reviews(created,content,rating,resultUserName,restaurantId);
        reviews.add(review);
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
    return reviews;
  }

  public List<Reviews> getReviewsByRestaurantId(int restaurantId) throws SQLException {
    List<Reviews> reviews = new ArrayList<Reviews>();
    String selectReviews =
        "SELECT Created,Content,Rating,UserName,RestaurantId FROM reviews WHERE restaurantId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReviews);
      selectStmt.setInt(1, restaurantId);
      results = selectStmt.executeQuery();
      while(results.next()) {
        Date created = results.getDate("created");
        String content = results.getString("Content");
        Long rating = results.getLong("rating");
        String resultUserName = results.getString("username");
        Integer resultRestaurantId = results.getInt("restaurantId");
        Reviews review = new Reviews(created,content,rating,resultUserName,resultRestaurantId);
        reviews.add(review);
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
    return reviews;
  }

  public Reviews delete(Reviews review) throws SQLException {
    String deleteReview = "DELETE FROM reviews WHERE created=? and content=? and rating=? and username=? and RestaurantId=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteReview);
      deleteStmt.setDate(1, review.getCreated());
      deleteStmt.setString(2,review.getContent());
      deleteStmt.setLong(3,review.getRating());
      deleteStmt.setString(4,review.getUserName());
      deleteStmt.setInt(5,review.getRestaurantId());
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
    }  }



}
