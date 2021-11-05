package blog.model;

import java.time.LocalDate;
import java.sql.Date;

public class CreditCards {

    protected long CardNumber;
    protected Date Expiration;
    protected String UserName;


    public CreditCards(Long cardNumber, Date expiration, String userName) {
        CardNumber = cardNumber;
        Expiration = expiration;
        UserName = userName;
    }

    public long getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        CardNumber = cardNumber;
    }

    public Date getExpiration() {
        return Expiration;
    }

    public void setExpiration(Date expiration) {
        Expiration = expiration;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
