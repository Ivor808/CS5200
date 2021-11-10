package blog.dal;


import blog.model.Reservations;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationsDao {
  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static ReservationsDao instance = null;
  protected ReservationsDao() {
    connectionManager = new ConnectionManager();
  }
  public static ReservationsDao getInstance() {
    if(instance == null) {
      instance = new ReservationsDao();
    }
    return instance;
  }


  public Reservations create(Reservations reservation) throws SQLException {
    String insertRes = "INSERT INTO reservations(start,end,size,username,restaurantId) VALUES(?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertRes);
      // PreparedStatement allows us to substitute specific types into the query template.
      // For an overview, see:
      // http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // For nullable fields, you can check the property first and then call setNull()
      // as applicable.
      insertStmt.setDate(1, reservation.getStart());
      insertStmt.setDate(2, reservation.getEnd());
      insertStmt.setInt(3, reservation.getSize());
      insertStmt.setString(4, reservation.getUserName());
      insertStmt.setInt(5, reservation.getSize());
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
      return reservation;
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

  public Reservations getReservationById(int reservationId) throws SQLException {
    String selectRes = "SELECT start,end,size,username,restaurantId FROM reservations WHERE reservationId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRes);
      selectStmt.setInt(1, reservationId);
      // Note that we call executeQuery(). This is used for a SELECT statement
      // because it returns a result set. For more information, see:
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
      results = selectStmt.executeQuery();
      // You can iterate the result set (although the example below only retrieves
      // the first record). The cursor is initially positioned before the row.
      // Furthermore, you can retrieve fields by name and by type.
      if(results.next()) {
        Date start = results.getDate("start");
        Date end = results.getDate("end");
        Integer size = results.getInt("size");
        String userName = results.getString("username");
        Integer restaurantId = results.getInt("restaurantId");
        Reservations res = new Reservations(start,end,size,userName,restaurantId);
        return res;
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

  public List<Reservations> getReservationsByUserName(String userName) throws SQLException {
    List<Reservations> reservations = new ArrayList<Reservations>();
    String selectRes = "SELECT start,end,size,username,restaurantId FROM reservations WHERE username=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRes);
      selectStmt.setString(1, userName);
      // Note that we call executeQuery(). This is used for a SELECT statement
      // because it returns a result set. For more information, see:
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
      results = selectStmt.executeQuery();
      // You can iterate the result set (although the example below only retrieves
      // the first record). The cursor is initially positioned before the row.
      // Furthermore, you can retrieve fields by name and by type.
      if(results.next()) {
        Date start = results.getDate("start");
        Date end = results.getDate("end");
        Integer size = results.getInt("size");
        String resultUserName = results.getString("username");
        Integer restaurantId = results.getInt("restaurantId");
        Reservations res = new Reservations(start,end,size,resultUserName,restaurantId);
        reservations.add(res);
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
    return reservations;
  }

  public List<Reservations> getReservationsBySitDownRestaurantId(int sitDownRestaurantId) throws SQLException {
    List<Reservations> reservations = new ArrayList<Reservations>();
    String selectRes = "SELECT start,end,size,username,restaurantId FROM reservations WHERE restaurantId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRes);
      selectStmt.setInt(1, sitDownRestaurantId);
      // Note that we call executeQuery(). This is used for a SELECT statement
      // because it returns a result set. For more information, see:
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
      results = selectStmt.executeQuery();
      // You can iterate the result set (although the example below only retrieves
      // the first record). The cursor is initially positioned before the row.
      // Furthermore, you can retrieve fields by name and by type.
      if(results.next()) {
        Date start = results.getDate("start");
        Date end = results.getDate("end");
        Integer size = results.getInt("size");
        String resultUserName = results.getString("username");
        Integer restaurantId = results.getInt("restaurantId");
        Reservations res = new Reservations(start,end,size,resultUserName,restaurantId);
        reservations.add(res);
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
    return reservations;
  }

  public Reservations delete(Reservations reservation) throws SQLException {
    String deleteReservation = "DELETE FROM reservations WHERE start=? and end=? and size=? and username=? and restaurantId=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteReservation);
      deleteStmt.setDate(1, reservation.getStart());
      deleteStmt.setDate(2, reservation.getEnd());
      deleteStmt.setInt(3, reservation.getSize());
      deleteStmt.setString(4, reservation.getUserName());
      deleteStmt.setInt(5, reservation.getRestaurantId());

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
