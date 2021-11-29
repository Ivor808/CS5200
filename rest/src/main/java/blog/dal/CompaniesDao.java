package blog.dal;

import blog.model.Companies;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CompaniesDao {

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static CompaniesDao instance = null;

  protected CompaniesDao() {
    connectionManager = new ConnectionManager();
  }

  public static CompaniesDao getInstance() {
    if (instance == null) {
      instance = new CompaniesDao();
    }
    return instance;

  }

  /**
   * Save the Persons instance by storing it in your MySQL instance.
   * This runs a INSERT statement.
   */
  public Companies create(Companies companies) throws SQLException {
    String insertCompany = "INSERT INTO companies(CompanyName,About) VALUES(?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertCompany);
      // PreparedStatement allows us to substitute specific types into the query template.
      // For an overview, see:
      // http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // For nullable fields, you can check the property first and then call setNull()
      // as applicable.
      insertStmt.setString(1, companies.getCompanyName());
      insertStmt.setString(2, companies.getAbout());
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
      return companies;
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
   * Get the matching Company record by fetching from your MySQL instance.
   * This runs a SELECT statement and returns a matching company.
   */
  public Companies getCompanyByCompanyName(String companyName) throws SQLException {
    Companies company = new Companies(null,null);
    String selectCompany =
        "SELECT CompanyName, About FROM companies WHERE CompanyName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCompany);
      selectStmt.setString(1, companyName);
      results = selectStmt.executeQuery();
      while(results.next()) {
        String resultCompanyName = results.getString("CompanyName");
        String resultCompanyAbout = results.getString("About");
        company = new Companies(resultCompanyName, resultCompanyAbout);
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
    return company;
  }

  /**
   * Update the About of the company instance.
   * This runs a UPDATE statement.
   */
  public Companies updateCompanyAbout(Companies company, String about) throws SQLException {
    String updateCompany = "UPDATE companies SET About=? WHERE CompanyName=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateCompany);
      updateStmt.setString(1, about);
      updateStmt.setString(2, company.getCompanyName());
      updateStmt.executeUpdate();

      // Update the company param before returning to the caller.
      company.setAbout(about);
      return company;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(updateStmt != null) {
        updateStmt.close();
      }
    }
  }

  /**
   * Delete the Company instance.
   * This runs a DELETE statement.
   */
  public Companies delete(Companies company) throws SQLException {
    String deleteCompany = "DELETE FROM companies WHERE CompanyName=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteCompany);
      deleteStmt.setString(1, company.getCompanyName());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the Company instance.
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
