package blog.tools;

import blog.dal.CompaniesDao;
import blog.dal.CreditCardsDao;
import blog.dal.FoodCartRestaurantsDao;
import blog.dal.RecommendationsDao;
import blog.dal.ReservationsDao;
import blog.dal.RestaurantsDao;
import blog.dal.ReviewsDao;
import blog.dal.SitDownRestaurantsDao;
import blog.dal.TakeOutRestaurantsDao;
import blog.dal.UsersDao;
import blog.model.*;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


/**
 * main() runner, used for the app demo.
 * 
 * Instructions:
 * 1. Create a new MySQL schema and then run the CREATE TABLE statements from lecture:
 * http://goo.gl/86a11H.
 * 2. Update ConnectionManager with the correct user, password, and schema.
 */
public class Inserter {

	public static void main(String[] args) throws SQLException {
		// DAO instances.
		CompaniesDao companiesDao = CompaniesDao.getInstance();
		CreditCardsDao creditCardsDao = CreditCardsDao.getInstance();
		FoodCartRestaurantsDao foodCartRestaurantsDao = FoodCartRestaurantsDao.getInstance();
		RecommendationsDao recommendationsDao = RecommendationsDao.getInstance();
		ReservationsDao blogCommentsDao = ReservationsDao.getInstance();
		RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
		ReservationsDao reservationsDao = ReservationsDao.getInstance();
		ReviewsDao reviewsDao = ReviewsDao.getInstance();
		SitDownRestaurantsDao sitDownRestaurantsDao = SitDownRestaurantsDao.getInstance();
		TakeOutRestaurantsDao takeOutRestaurantsDao = TakeOutRestaurantsDao.getInstance();
		UsersDao usersDao = UsersDao.getInstance();

		Date date = new Date(System.currentTimeMillis());
		// CREATE
		// INSERT objects from our model.
		Users user = new Users("a", "pass", "abba", "foo", "foo", 51451433);
		Users user2 = new Users("b", "pass", "baaba", "foo", "email", 123413241);
		user = usersDao.create(user);
		user2 = usersDao.create(user2);

		Companies companies1 = new Companies("google", "fooooo");
		Companies companies2 = new Companies("foobar", "fooooo");

		companies1 = companiesDao.create(companies1);
		companies2 = companiesDao.create(companies2);

		CreditCards cc1 = new CreditCards(445L, date, "a");
		CreditCards cc2 = new CreditCards(312314L, date, "b");

		cc1 = creditCardsDao.create(cc1);
		cc2 = creditCardsDao.create(cc2);

		FoodCartRestaurants fr1 = new FoodCartRestaurants("foo", "bar", "1. foo", "1234", true,
				Cuisines.AFRICAN, "foo", "b", "seattle", "wa", 98107, "google", true);
		fr1 = foodCartRestaurantsDao.create(fr1);

		Recommendations rec1 = new Recommendations("a", 1);
		rec1 = recommendationsDao.create(rec1);

		Reservations res1 = new Reservations(date, date, 2, "a", 1);
		res1 = reservationsDao.create(res1);

		Reviews rev1 = new Reviews(date, "good good", 5L, "a", 1);
		rev1 = reviewsDao.create(rev1);

		SitDownRestaurants sr1 = new SitDownRestaurants("bar", "foo", "1. foo", "1234", true,
				Cuisines.AMERICAN, "foo", "b", "seattle", "wa", 98107, "google", 15);
		sr1 = sitDownRestaurantsDao.create(sr1);

		TakeOutRestaurants tr1 = new TakeOutRestaurants("take out place", "bar", "1. foo", "1234", true,
				Cuisines.AFRICAN, "foo", "b", "seattle", "wa", 98107, "google", 15);
		tr1 = takeOutRestaurantsDao.create(tr1);

		// READ.
		Users user1 = usersDao.getUserFromUserName("a");
		System.out.println("user1 is " + user1.getUserName());

		Companies companies = companiesDao.getCompanyByCompanyName("google");
		System.out.println("company is " + companies.getCompanyName());

		Restaurants res = restaurantsDao.getRestaurantById(1);
		System.out.println("res1 is " + res.getName());
		List<Restaurants> restaurants = restaurantsDao.getRestaurantsByCuisine(Cuisines.AFRICAN);
		for (Restaurants r : restaurants) {
			System.out.println(r.getName());
		}
		System.out.println("---------------");
		List<Restaurants> companyRestaurants = restaurantsDao.getRestaurantsByCompanyName("google");
		for (Restaurants r : companyRestaurants) {
			System.out.println(r.getName());
		}
		System.out.println("---------------");
		CreditCards cc3 =  creditCardsDao.getCreditCardByCardNumber(445L);
		System.out.println("cc3 is " + cc3.getCardNumber());
		System.out.println("---------------");
		List<CreditCards> cc4 = creditCardsDao.getCreditCardsByUserName("a");
		for (CreditCards c: cc4) {
			System.out.println(c.getCardNumber());
		}
		System.out.println("---------------");
		FoodCartRestaurants fc3 = foodCartRestaurantsDao.getFoodCartRestaurantById(6);
		System.out.println("fc3 is " + fc3.getName());
		List<FoodCartRestaurants> fc4 = foodCartRestaurantsDao.getFoodCartRestaurantsByCompanyName("company2");
		for (FoodCartRestaurants fc: fc4) {
			System.out.println(fc.getName());
		}

		Recommendations rec3 = recommendationsDao.getRecommendationById(1);
		System.out.println("Rec3 is " + rec3.getUserName());
		System.out.println("---------------");
		List<Recommendations> recs = recommendationsDao.getRecommendationsByRestaurantId(3);
		for(Recommendations rec: recs) {
			System.out.println(rec.getRestaurantId());
		}
		System.out.println("---------------");
		recs = recommendationsDao.getRecommendationsByUserName("Bruce");
		for(Recommendations rec: recs) {
			System.out.println(rec.getUserName());
		}
		System.out.println("---------------");
		Reservations res4 = reservationsDao.getReservationById(1);
		System.out.println("Res4 is " + res4.getStart());
		System.out.println("---------------");
		List<Reservations> rezs =  reservationsDao.getReservationsByUserName("Bruce");
		for(Reservations r: rezs) {
			System.out.println(r.getUserName());
		}
		System.out.println("---------------");
		rezs = reservationsDao.getReservationsBySitDownRestaurantId(1);
		for(Reservations r: rezs) {
			System.out.println(r.getUserName());
		}
		System.out.println("---------------");
		Reviews rev3 = reviewsDao.getReviewById(1);
		System.out.println("rev3 is " + rev3.getUserName());
		System.out.println("---------------");
		List<Reviews> revs = reviewsDao.getReviewsByRestaurantId(9);
		for(Reviews r: revs) {
			System.out.println(r.getRestaurantId());
		}
		System.out.println("---------------");
		revs = reviewsDao.getReviewsByUserName("Bruce");
		for(Reviews r: revs) {
			System.out.println(r.getUserName());
		}
		System.out.println("---------------");
		SitDownRestaurants sr4 = sitDownRestaurantsDao.getSitDownRestaurantById(1);
		System.out.println("sr4 is " + sr4.getCapacity());
		System.out.println("---------------");
		List<SitDownRestaurants> srs = sitDownRestaurantsDao.getSitDownRestaurantsByCompanyName("company1");
		for(SitDownRestaurants sr: srs) {
			System.out.println(sr.getCompanyName());
		}
		System.out.println("---------------");
		TakeOutRestaurants tr4 = takeOutRestaurantsDao.getTakeOutRestaurantById(4);
		System.out.println("tr4 is" + tr4.getName());
		System.out.println("---------------");
		List<TakeOutRestaurants> trs = takeOutRestaurantsDao.getTakeOutRestaurantsByCompanyName("company1");
		for(TakeOutRestaurants tr: trs) {
			System.out.println(tr.getCompanyName());
		}
		System.out.println("---------------");

		// UPDATES
		companies = companiesDao.updateCompanyAbout(companies, "new about");
		System.out.println("updated about: " + companies.getAbout());
		Date d1 = new Date(112021);
		cc1 = creditCardsDao.updateExpiration(cc1,d1);
		System.out.println("New date is "+cc1.getExpiration());

		// DELETES
		companies = companiesDao.delete(companies);
		cc1 = creditCardsDao.delete(cc1);
		fr1 = foodCartRestaurantsDao.delete(fr1);
		rec1 = recommendationsDao.delete(rec1);
		res1 = reservationsDao.delete(res1);
		res = restaurantsDao.delete(res);
		rev1 = reviewsDao.delete(rev1);
		sr1 = sitDownRestaurantsDao.delete(sr1);
		tr1 = takeOutRestaurantsDao.delete(tr1);
		user1 = usersDao.delete(user1);
		try {
			System.out.println(res.getName());
		}
		catch (NullPointerException sql) {
			System.out.println("res has been deleted so can't operate on it");
		}
		try {
			user1 = usersDao.delete(user1);
		}
		catch (NullPointerException sql) {
			System.out.println("user1 already deleted, proper error thrown");


		}



	}
}
