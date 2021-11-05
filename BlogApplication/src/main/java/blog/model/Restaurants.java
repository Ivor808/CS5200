package blog.model;

public class Restaurants {

    protected Integer RestaurantId;
    protected String name;
    protected String description;
    protected String menu;
    protected String hours;
    protected String active;
    protected Cuisines cuisine;
    protected String street1;
    protected String street2;
    protected String city;
    protected String state;
    protected Integer zip;
    protected String companyName;

    public Restaurants(Integer restaurantId, String name, String description, String menu, String hours, String active, Cuisines cuisine, String street1, String street2, String city, String state, Integer zip, String companyName) {
        RestaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.menu = menu;
        this.hours = hours;
        this.active = active;
        this.cuisine = cuisine;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.companyName = companyName;
    }

    public Integer getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        RestaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Cuisines getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisines cuisine) {
        this.cuisine = cuisine;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
