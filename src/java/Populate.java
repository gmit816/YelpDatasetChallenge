package main.java;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import main.java.Helper.DBHelper;
import main.java.Models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.text.SimpleDateFormat;


public class Populate {

    public static  final String filesBase = "../YelpProject/resources/";

    public static void fetchData(){

    }
    public static void addUsersFromJSON(){
        ArrayList<JSONObject> json=new ArrayList<JSONObject>();
        JSONObject obj;
        String fileName = filesBase+"yelp_user.json";
        String line = null;
        DBHelper db = new DBHelper();
        db.DBConnect();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<User> userList = new ArrayList<User>();
            while((line = bufferedReader.readLine()) != null) {
                try{
                    obj = (JSONObject) new JSONParser().parse(line);
                    json.add(obj);
                    User user = new User();
                    user.setUserId( (String) obj.get("user_id") );
                    user.setName( (String) obj.get("name"));
                    user.setYelpingSince((String) obj.get("yelping_since"));
                    JSONObject votesObj = (JSONObject) new JSONParser().parse(obj.get("votes").toString());
                    user.setVotesFunny( Integer.parseInt(votesObj.get("funny").toString()));
                    user.setVotesCool( Integer.parseInt(votesObj.get("cool").toString()));
                    user.setVotesUseful(Integer.parseInt(votesObj.get("useful").toString()));
                    user.setReviewCount(Integer.parseInt(obj.get("review_count").toString()));
                    user.setFans(Integer.parseInt(obj.get("fans").toString()));
                    user.setAverageStars(Float.parseFloat(obj.get("average_stars").toString()));
                    JSONArray friends = (JSONArray) obj.get("friends");
                    ArrayList<Friends> friendsList = new ArrayList<Friends>();

                    for(int i=0; i<friends.size() ; i++){
                        Friends friend = new Friends();
                        friend.setUserId((String) friends.get(i));
                        friendsList.add(friend);
//                        System.out.println(friend.getUserId());
                    }
                    user.setFriends(friendsList);
                    userList.add(user);
                }catch (Exception e){
                    System.err.println("error");
                    e.printStackTrace();

                }
            }
            bufferedReader.close();
            db.TruncateUserFriends();
            db.TruncateUsers();
            db.CreateUsersTable();
            db.CreateUserFriendsTable();
            db.InsertUsers(userList);
            db.addUserFriends(userList);
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }finally {
            db.DBClose();
        }
    }


    public static void addBusinessFromJSON(){
        ArrayList<JSONObject> json=new ArrayList<JSONObject>();
        JSONObject obj;
        String fileName = filesBase+"yelp_business.json";
        String line = null;
        DBHelper db = new DBHelper();
        db.DBConnect();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<Business> businessList = new ArrayList<Business>();
            while((line = bufferedReader.readLine()) != null) {
                try{
                    obj = (JSONObject) new JSONParser().parse(line);
                    json.add(obj);
                    Business business = new Business();
                    business.setBusinessId((String) obj.get("business_id"));
                    business.setName( (String) obj.get("name"));
                    business.setFullAddress((String) obj.get("full_address"));
                    business.setCity((String) obj.get("city"));
                    business.setState((String) obj.get("state"));
                    business.setReview_count(Integer.parseInt(obj.get("review_count").toString()));
                    business.setStars(Float.parseFloat(obj.get("stars").toString()));

                    ArrayList<Hours> hrs = new ArrayList<Hours>();
                    JSONObject hoursObj = (JSONObject) new JSONParser().parse(obj.get("hours").toString());
                    for(Object key: hoursObj.keySet()){
                        Hours hr = new Hours();
                        String day = (String) key;
                        if(hoursObj.get(day) != null) {
                            JSONObject dayObj = (JSONObject) new JSONParser().parse(hoursObj.get(day).toString());
                            hr.setBusinessId((String) obj.get("business_id"));
                            hr.setDay((String) key);
                            hr.setOpen_time((String) dayObj.get("open"));
                            hr.setClose_time((String) dayObj.get("close"));
                            hrs.add(hr);
                        }
                    }

                    business.setHrs(hrs);

                    ArrayList<Attribute> attr_list = new ArrayList<Attribute>();
                    if(obj.get("attributes")!=null) {
                        JSONObject attrsObj = (JSONObject) new JSONParser().parse(obj.get("attributes").toString());
                        for (Object key : attrsObj.keySet()) {
                            String k = (String) key;
                            Object keyvalue = attrsObj.get(k);

                            if(keyvalue instanceof JSONObject){
                                JSONObject temp = (JSONObject) attrsObj.get(key);
                                for(Object key2: temp.keySet()){
                                    String k2 = (String) key2;
                                    Object keyvalue2 = temp.get(k2);
                                    String val =  keyvalue2.toString();
                                    if(keyvalue2 instanceof Boolean || keyvalue2 instanceof Integer ){
                                        k2 += "_"+val;
                                    }
                                    Attribute attr = new Attribute();
                                    attr.setBusinessId( (String) obj.get("business_id"));
                                    attr.setAttribute_name( k2 );
                                    attr.setAttribute_value( val );
                                    attr_list.add(attr);
                                }
                            }else{
                                String val = attrsObj.get(k).toString();
                                if(keyvalue instanceof Boolean || keyvalue instanceof Integer){
                                    k += "_"+val;
                                }
                                Attribute attr = new Attribute();
                                attr.setBusinessId( (String) obj.get("business_id"));
                                attr.setAttribute_name( k );
                                attr.setAttribute_value( val );
                                attr_list.add(attr);
                            }

                        }
                    }

                    business.setAttr(attr_list);

                    ArrayList<BusinessCategories> cats = new ArrayList<BusinessCategories>();
                    ArrayList<BusinessSubCategories> subcats = new ArrayList<BusinessSubCategories>();
                    ArrayList<String> allCat = getAllCategories();
                    JSONArray cat_array = (JSONArray) obj.get("categories");
                    for(int i=0; i<cat_array.size(); i++){
                        String cat = cat_array.get(i).toString();
                        if(allCat.contains(cat)){
                            BusinessCategories bc = new BusinessCategories();
                            bc.setBusinessId((String) obj.get("business_id"));
                            bc.setCategoryName(cat);
                            cats.add(bc);
                        }else{
                            BusinessSubCategories bsc = new BusinessSubCategories();
                            bsc.setBusinessId((String) obj.get("business_id"));
                            bsc.setSubCategoryName(cat);
                            subcats.add(bsc);
                        }
                    }

                    business.setCategories(cats);
                    business.setSubCategories(subcats);

                    businessList.add(business);
                }catch (Exception e){
                    System.err.println("error");
                    e.printStackTrace();

                }
            }
            bufferedReader.close();
            db.TruncateBusiness();
            db.addBusiness(businessList);

        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }finally {
            db.DBClose();
        }
    }


    public static void addReviewsFromJSON(){
        ArrayList<JSONObject> json=new ArrayList<JSONObject>();
        JSONObject obj;
        String fileName = filesBase+"yelp_review.json";
        String line = null;
        DBHelper db = new DBHelper();
        db.DBConnect();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<Reviews> reviewsList = new ArrayList<Reviews>();
            while((line = bufferedReader.readLine()) != null) {
                try{
                    obj = (JSONObject) new JSONParser().parse(line);
                    json.add(obj);
                    Reviews review = new Reviews();
                    review.setReviewId( (String) obj.get("review_id"));
                    review.setUserId( (String) obj.get("user_id"));
                    review.setBusinessId( (String) obj.get("business_id"));
                    JSONObject votesObj = (JSONObject) new JSONParser().parse(obj.get("votes").toString());
                    review.setVotesFunny( Integer.parseInt(votesObj.get("funny").toString()));
                    review.setVotesCool( Integer.parseInt(votesObj.get("cool").toString()));
                    review.setVotesUseful(Integer.parseInt(votesObj.get("useful").toString()));
                    String text = (String) obj.get("text");
                    if(text.length() > 3000){
                        text = text.substring(0,3000);
                    }
                    review.setText(text);
                    review.setDate((String) obj.get("date"));
                    review.setStars(Integer.parseInt(obj.get("stars").toString()));
                    reviewsList.add(review);
                }catch (Exception e){
                    System.err.println("error");
                    e.printStackTrace();

                }
            }
            bufferedReader.close();
            db.TruncateReviewsTable();
            db.CreateReviewsTable();
            db.addReviews(reviewsList);
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }finally {
            db.DBClose();
        }
    }


    public static ArrayList<String> getAllCategories(){
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Active Life");
        categories.add("Arts & Entertainment");
        categories.add("Car Rental");
        categories.add("Automotive");
        categories.add("Cafes");
        categories.add("Beauty and Spas");
        categories.add("Convenience Stores");
        categories.add("Dentists");
        categories.add("Doctors");
        categories.add("Drugstores");
        categories.add("Department Stores");
        categories.add("Education");
        categories.add("Event Planning & Services");
        categories.add("Flowers & Gifts");
        categories.add("Food");
        categories.add("Health & Medical");
        categories.add("Home Services");
        categories.add("Home & Garden");
        categories.add("Hospitals");
        categories.add("Hotels & Travel");
        categories.add("Hardware Stores");
        categories.add("Grocery");
        categories.add("Medical Centers");
        categories.add("Nurseries & Gardening");
        categories.add("NightLife");
        categories.add("Restaurants");
        categories.add("Shopping");
        categories.add("Transportation");

        return categories;
    }


    public static void main(String[] args){
        addUsersFromJSON();
        addReviewsFromJSON();
        addBusinessFromJSON();
    }

}
