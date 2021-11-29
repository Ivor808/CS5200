package blog.dal;

import blog.model.CreditCards;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreditCardsDao {

    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static CreditCardsDao instance = null;
    protected CreditCardsDao() {
        connectionManager = new ConnectionManager();
    }
    public static CreditCardsDao getInstance() {
        if(instance == null) {
            instance = new CreditCardsDao();
        }
        return instance;
    }

    /**
     * Save the CC instance by storing it in your MySQL instance.
     * This runs a INSERT statement.
     */
    public CreditCards create(CreditCards cc) throws SQLException {
        String insertCC = "INSERT INTO CreditCards(CardNumber,Expiration,UserName) VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCC);

            insertStmt.setLong(1, cc.getCardNumber());
            insertStmt.setDate(2, cc.getExpiration());
            insertStmt.setString(3, cc.getUserName());

            insertStmt.executeUpdate();

            return cc;
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

    public CreditCards getCreditCardByCardNumber(long cardNumber) throws SQLException {
        String selectCC = "SELECT CardNumber,expiration,username FROM CreditCards WHERE CardNumber=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCC);
            selectStmt.setLong(1, cardNumber);
            results = selectStmt.executeQuery();
            if(results.next()) {
                Long resultCardNumber = results.getLong("CardNumber");
                Date expiration = results.getDate("expiration");
                String UserName = results.getString("UserName");
                CreditCards cc = new CreditCards(resultCardNumber,expiration,UserName);
                return cc;
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

    public List<CreditCards> getCreditCardsByUserName(String userName) throws SQLException{
        List<CreditCards> creditCards = new ArrayList<CreditCards>();
        String selectCreditCards =
                "SELECT CardNumber,Expiration,UserName " +
                        "FROM CreditCards " +
                        "WHERE UserName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCreditCards);
            selectStmt.setString(1, userName);
            results = selectStmt.executeQuery();
            while(results.next()) {
                Long cardNumber = results.getLong("CardNumber");
                Date expiration = results.getDate("Expiration");
                String resultUserName = results.getString("UserName");
                CreditCards cc = new CreditCards(cardNumber,expiration,resultUserName);
                creditCards.add(cc);
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
        return creditCards;
    }

    public CreditCards updateExpiration(CreditCards creditCard, Date newExpiration) throws SQLException {
        String updateCC = "UPDATE creditcards SET Expiration=? WHERE CardNumber=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateCC);
            updateStmt.setDate(1, newExpiration);
            updateStmt.setLong(2, creditCard.getCardNumber());
            updateStmt.executeUpdate();

            creditCard.setExpiration(newExpiration);
            return creditCard;
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
     * Delete the cc instance.
     * This runs a DELETE statement.
     */
    public CreditCards delete(CreditCards cc) throws SQLException {
        String deleteCC = "DELETE FROM creditcards WHERE CardNumber=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteCC);
            deleteStmt.setLong(1, cc.getCardNumber());
            deleteStmt.executeUpdate();
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


    


