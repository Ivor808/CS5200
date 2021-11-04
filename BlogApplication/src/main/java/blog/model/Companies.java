package blog.model;

public class Companies {

  protected String CompanyName;
  protected String About;

  public Companies(String companyName, String about) {
    CompanyName = companyName;
    About = about;
  }

  public String getCompanyName() {
    return CompanyName;
  }

  public void setCompanyName(String companyName) {
    CompanyName = companyName;
  }

  public String getAbout() {
    return About;
  }

  public void setAbout(String about) {
    About = about;
  }
}
