package blog.model;

public class FoodCartRestaurants extends Restaurants{
  protected Boolean Licensed;

  public FoodCartRestaurants(String name, String description, String menu, String hours,
      boolean active, Cuisines cuisine, String street1, String street2, String city,
      String state, Integer zip, String companyName, Boolean licensed) {
    super(name, description, menu, hours, active, cuisine, street1, street2, city, state, zip,
        companyName);
    Licensed = licensed;
  }

  public Boolean getLicensed() {
    return Licensed;
  }

  public void setLicensed(Boolean licensed) {
    Licensed = licensed;
  }
}
