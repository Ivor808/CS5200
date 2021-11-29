package blog.model;

import java.time.LocalDate;
import java.sql.Date;

public class Reviews {
  protected Date Created;
  protected String Content;
  protected Long Rating;
  protected String userName;
  protected Integer restaurantId;

  public Reviews(Date created, String content, Long rating, String userName,
      Integer restaurantId) {
    Created = created;
    Content = content;
    Rating = rating;
    this.userName = userName;
    this.restaurantId = restaurantId;
  }

  public Date getCreated() {
    return Created;
  }

  public void setCreated(Date created) {
    Created = created;
  }

  public String getContent() {
    return Content;
  }

  public void setContent(String content) {
    Content = content;
  }

  public Long getRating() {
    return Rating;
  }

  public void setRating(Long rating) {
    Rating = rating;
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
