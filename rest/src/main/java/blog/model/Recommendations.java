package blog.model;

public class Recommendations {
  protected String userName;
  protected Integer restaurantId;


  public Recommendations(String userName, Integer restaurantId) {
    this.userName = userName;
    this.restaurantId = restaurantId;
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
