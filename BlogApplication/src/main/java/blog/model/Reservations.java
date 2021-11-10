package blog.model;

import java.sql.Date;

public class Reservations {
  protected Date start;
  protected Date end;
  protected Integer size;
  protected String userName;
  protected Integer restaurantId;

  public Reservations(Date start, Date end, Integer size, String userName,
      Integer restaurantId) {
    this.start = start;
    this.end = end;
    this.size = size;
    this.userName = userName;
    this.restaurantId = restaurantId;
  }

  public Date getStart() {
    return start;
  }

  public void setStart(Date start) {
    this.start = start;
  }

  public Date getEnd() {
    return end;
  }

  public void setEnd(Date end) {
    this.end = end;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(Integer restaurantId) {
    this.restaurantId = restaurantId;
  }
}
