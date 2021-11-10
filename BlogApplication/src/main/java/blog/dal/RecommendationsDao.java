package blog.dal;

import blog.model.Recommendations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecommendationsDao {
  protected ConnectionManager connectionManager;
  private static RecommendationsDao instance = null;
  protected RecommendationsDao() {
    super();
  }
  public static RecommendationsDao getInstance() {
    if(instance == null) {
      instance = new RecommendationsDao();
    }
    return instance;
  }

  public Recommendations create(Recommendations recommendation) throws SQLException {
    String insertRecommendation = "INSERT INTO recommendations(UserName,RestaurantId) VALUES(?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertRecommendation);
      // PreparedStatement allows us to substitute specific types into the query template.
      // For an overview, see:
      // http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // For nullable fields, you can check the property first and then call setNull()
      // as applicable.
      insertStmt.setString(1, recommendation.getUserName());
      insertStmt.setInt(2, recommendation.getRestaurantId());
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
      return recommendation;
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

  public Recommendations getRecommendationById(int recommendationId) throws SQLException {
    String selectRecommendation = "SELECT UserName,RestaurantId FROM recommendations WHERE recommendationId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRecommendation);
      selectStmt.setInt(1, recommendationId);
      // Note that we call executeQuery(). This is used for a SELECT statement
      // because it returns a result set. For more information, see:
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
      results = selectStmt.executeQuery();
      // You can iterate the result set (although the example below only retrieves
      // the first record). The cursor is initially positioned before the row.
      // Furthermore, you can retrieve fields by name and by type.
      if(results.next()) {
        String resultUserName = results.getString("UserName");
        Integer restaurantId = results.getInt("restaurantId");
        Recommendations recommendation = new Recommendations(resultUserName, restaurantId);
        return recommendation;
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


  public List<Recommendations> getRecommendationsByUserName(String userName) throws SQLException {
    List<Recommendations> recs = new ArrayList<Recommendations>();
    String selectRecommendation =
        "SELECT UserName,RestaurantId FROM recommendations WHERE username=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRecommendation);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();
      while(results.next()) {
        String resultUserName = results.getString("UserName");
        Integer restaurantId = results.getInt("restaurantId");
        Recommendations rec = new Recommendations(resultUserName, restaurantId);
        recs.add(rec);
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
    return recs;
  }

  public List<Recommendations> getRecommendationsByRestaurantId(int restaurantId) throws SQLException {
    List<Recommendations> recs = new ArrayList<Recommendations>();
    String selectRecommendation =
        "SELECT UserName,RestaurantId FROM recommendations WHERE restaurantId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRecommendation);
      selectStmt.setInt(1, restaurantId);
      results = selectStmt.executeQuery();
      while(results.next()) {
        String resultUserName = results.getString("UserName");
        Integer resultRestaurantId = results.getInt("restaurantId");
        Recommendations rec = new Recommendations(resultUserName, resultRestaurantId);
        recs.add(rec);
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
    return recs;
  }

  public Recommendations delete(Recommendations recommendation) throws SQLException {
    String deleteRecommendations = "DELETE FROM recommendations WHERE UserName=? and RestaurantId=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteRecommendations);
      deleteStmt.setString(1, recommendation.getUserName());
      deleteStmt.setInt(2,recommendation.getRestaurantId());
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
