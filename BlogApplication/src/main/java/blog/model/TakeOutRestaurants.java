package blog.model;

public class TakeOutRestaurants extends Restaurants {

  protected Integer maxWaitTime;


  public TakeOutRestaurants(String name, String description, String menu, String hours,
      boolean active, Cuisines cuisine, String street1, String street2, String city,
      String state, Integer zip, String companyName, Integer maxWaitTime) {
    super(name, description, menu, hours, active, cuisine, street1, street2, city, state, zip,
        companyName);
    this.maxWaitTime = maxWaitTime;
  }

  public Integer getMaxWaitTime() {
    return maxWaitTime;
  }

  public void setMaxWaitTime(Integer maxWaitTime) {
    this.maxWaitTime = maxWaitTime;
  }
}
