package main.java.Helper;
import main.java.Models.*;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBHelper {
    public  Connection connection;
    public DBHelper(){

    }
    public void DBConnect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        } catch (Exception e) {
            System.err.println("Unable to load driver.");
            e.printStackTrace();
        }
        try {
            InputStream input = new FileInputStream("resources/config.properties");
            Properties prop = new Properties();
            prop.load(input);

            String username = prop.getProperty("DB_USERNAME");
            String password = prop.getProperty("DB_PASSWORD");
            String host = prop.getProperty("DB_HOST");

            connection = DriverManager.getConnection(host, username, password);
        }
        catch (FileNotFoundException e) {
            System.err.println("unable to find config.properties file");
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Unable to connect to DB");
            e.printStackTrace();
        }

    }

    public void DBClose(){
        try{
            connection.close();
        }
        catch (Exception e){
            System.err.println("Unable to close db");
            e.printStackTrace();
        }
    }

    public void TruncateUsers(){
        try {
            PreparedStatement statement = connection.prepareStatement("DROP TABLE USERS");
            statement.execute();
            statement.close();

        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void TruncateBusiness(){
        try {
            PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE BUSINESS");
            statement.execute();
            statement.close();

            PreparedStatement statement1 = connection.prepareStatement("TRUNCATE TABLE BUSINESS_HOURS");
            statement1.execute();
            statement1.close();

            PreparedStatement statement2= connection.prepareStatement("TRUNCATE TABLE BUSINESS_CAT");
            statement2.execute();
            statement2.close();

            PreparedStatement statement3 = connection.prepareStatement("TRUNCATE TABLE BUSINESS_SUBCAT");
            statement3.execute();
            statement3.close();

            PreparedStatement statement4 = connection.prepareStatement("TRUNCATE TABLE BUSINESS_ATTR");
            statement4.execute();
            statement4.close();


        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void CreateUsersTable(){
        try {
            PreparedStatement st = connection.prepareStatement("CREATE TABLE Users(userId VARCHAR2(50) primary key, name VARCHAR2(50), yelpingSince date,  votesFunny int, votesUseful int, votesCool int, review_count int , fans int, averageStars float )");
            st.execute();
            st.close();
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void InsertUsers(List<User> users){
        PreparedStatement statement;
        try{
            statement = connection.prepareStatement("INSERT INTO USERS (userId , name , yelpingSince,  votesFunny, votesUseful, votesCool, review_count , fans, averageStars ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for(User user: users){
                statement.setString(1,user.getUserId());
                statement.setString(2,user.getName());
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
                java.util.Date date = sdf1.parse(user.getYelpingSince());
                java.sql.Date sqlYelpSince = new java.sql.Date(date.getTime());
                statement.setDate(3, sqlYelpSince);
                statement.setInt(4,user.getVotesFunny());
                statement.setInt(5,user.getVotesUseful());
                statement.setInt(6,user.getVotesCool());
                statement.setInt(7,user.getReviewCount());
                statement.setInt(8,user.getFans());
                statement.setFloat(9, user.getAverageStars());
                statement.addBatch();
            }
            statement.executeBatch();
            statement.close();

            System.out.println("rows inserted ");

        }catch (Exception e){
            System.err.println("Query error");
            e.printStackTrace();
        }
    }

    public void TruncateUserFriends(){
        try {
            PreparedStatement statement = connection.prepareStatement("DROP TABLE UserFriends");
            statement.execute();
            statement.close();

        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void CreateUserFriendsTable(){
        try{
            PreparedStatement st = connection.prepareStatement("CREATE TABLE UserFriends(userId VARCHAR2(50), friend_id VARCHAR2(50))");
            st.execute();
            st.close();
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void addUserFriends(List<User> users){
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO UserFriends (userId , friend_id ) VALUES (?, ?)");

            for(User user: users){
                for(Friends friend: user.getFriends()){
                    statement.setString(1,user.getUserId());
                    statement.setString(2, friend.getUserId());
                    statement.addBatch();
                }
            }
            statement.executeBatch();

            System.out.println("rows inserted ");

        }catch (Exception e){
            System.err.println("Query error");
            e.printStackTrace();
        }
    }

    public void TruncateReviewsTable(){
        try {
            PreparedStatement statement = connection.prepareStatement("DROP TABLE Reviews");
            statement.execute();
            statement.close();
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void CreateReviewsTable(){
        try{
            PreparedStatement st = connection.prepareStatement("CREATE TABLE Reviews(reviewId VARCHAR2(50) primary key , stars int check (stars between 1 and 5), publishDate date , text VARCHAR2(4000) , businessId VARCHAR2(50) , userId VARCHAR2(50),  votesFunny int, votes_cool int, votes_useful int)");
            st.execute();
            st.close();
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public void addReviews(List<Reviews> reviews){
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO REVIEWS (reviewId, stars, publishDate, text, businessId, userId,  votesFunny, votes_cool, votes_useful ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for(Reviews review: reviews){
                statement.setString(1,review.getReviewId());
                statement.setInt(2,review.getStars());
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
                java.util.Date date = sdf1.parse(review.getDate());
                java.sql.Date publishDate = new java.sql.Date(date.getTime());
                statement.setDate(3, publishDate);
                statement.setString(4, review.getText());
                statement.setString(5, review.getBusinessId());
                statement.setString(6, review.getUserId());
                statement.setInt(7, review.getVotesFunny());
                statement.setInt(8, review.getVotesCool());
                statement.setInt(9, review.getVotesUseful());
                statement.addBatch();
            }
            statement.executeBatch();
            System.out.println("rows inserted for reviews table");

        }catch (Exception e){
            System.err.println("Query inserted");
        }
    }

    public void addBusiness(List<Business> businesses){
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO  BUSINESS(businessId, name, address, city, state, review_count, stars) VALUES (?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement statement1 = connection.prepareStatement("INSERT  INTO BUSINESS_HOURS(businessId, day, from_time, to_time) VALUES(?, ?, ?, ?)");
            PreparedStatement statement2 = connection.prepareStatement("INSERT  INTO BUSINESS_CAT(businessId, category) VALUES(?, ?)");
            PreparedStatement statement3 = connection.prepareStatement("INSERT INTO BUSINESS_SUBCAT(businessid, subcategory)  VALUES(?,?)");
            PreparedStatement statement4 = connection.prepareStatement("INSERT INTO BUSINESS_ATTR(businessId, attr_name , attr_value)  VALUES(?,?,?)");

            for(Business business: businesses){
                statement.setString(1,business.getBusinessId());
                statement.setString(2,business.getName());
                statement.setString(3,business.getFullAddress());
                statement.setString(4,business.getCity());
                statement.setString(5,business.getState());
                statement.setInt(6,business.getReview_count());
                statement.setFloat(7, business.getStars());
                statement.addBatch();

                List<Hours> hrs = business.getHrs();
                for(Hours hr: hrs){
                    statement1.setString(1, business.getBusinessId());
                    statement1.setString(2, hr.getDay());

                    DateFormat formatter = new SimpleDateFormat("hh:mm");//2015-05-11 18:26:55
                    java.util.Date openObj = formatter.parse(hr.getOpen_time());
                    java.sql.Timestamp openTime = new java.sql.Timestamp(openObj.getTime());
                    java.util.Date closeObj = formatter.parse(hr.getClose_time());
                    java.sql.Timestamp closeTime = new java.sql.Timestamp(closeObj.getTime());

                    statement1.setTimestamp(3, openTime);
                    statement1.setTimestamp(4, closeTime);
                    statement1.addBatch();
                }

                List<BusinessCategories> bcats = business.getCategories();
                for(BusinessCategories bcat: bcats){
                    statement2.setString(1, bcat.getBusinessId());
                    statement2.setString(2, bcat.getCategoryName());
                    statement2.addBatch();
                }

                List<BusinessSubCategories> bsubcats = business.getSubCategories();
                for(BusinessSubCategories bsubcat: bsubcats){
                    statement3.setString(1, bsubcat.getBusinessId());
                    statement3.setString(2, bsubcat.getSubCategoryName());
                    statement3.addBatch();
                }

                List<Attribute> attrs = business.getAttr();
                for(Attribute attr : attrs){
                    statement4.setString(1, attr.getBusinessId());
                    statement4.setString(2, attr.getAttribute_name());
                    statement4.setString(3, attr.getAttribute_value());
                    statement4.addBatch();
                }

            }
            statement.executeBatch();
            statement1.executeBatch();
            statement2.executeBatch();
            statement3.executeBatch();
            statement4.executeBatch();
            System.out.println("rows inserted for business table");

        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
    }

    public ArrayList<Attribute> getAttributes(ArrayList<String> selectedSubCategories, ArrayList<String> selectedCategories, String condition){
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        try {
            String query = "";
            if(condition == "AND"){
                String subq = "(SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(0)+"')";
                if(selectedCategories.size() > 1){
                    for(int i=1; i<selectedCategories.size(); i++){
                        subq += " INTERSECT (SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(i) +"' )";
                    }
                }
                query = "SELECT DISTINCT ba.attr_name FROM BUSINESS_ATTR ba JOIN BUSINESS_SUBCAT bs on bs.BUSINESSID = ba.BUSINESSID JOIN BUSINESS_CAT BC on ba.BUSINESSID = BC.BUSINESSID WHERE ba.businessId IN ( "+subq+" )";

            }else {

                query = "SELECT DISTINCT ba.attr_name FROM BUSINESS_ATTR ba JOIN BUSINESS_SUBCAT bs on bs.BUSINESSID = ba.BUSINESSID JOIN BUSINESS_CAT BC on ba.BUSINESSID = BC.BUSINESSID WHERE bc.category = ";
                query += "'" + selectedCategories.get(0) + "' ";

                if (selectedCategories.size() > 1) {
                    for (int i = 1; i < selectedCategories.size(); i++) {
                        query += condition + " bc.category = '" + selectedCategories.get(i) + "' ";
                    }
                }

            }

            query += " AND bs.subcategory = '" + selectedSubCategories.get(0) + "' ";
            if (selectedSubCategories.size() > 1) {
                for (int i = 1; i < selectedSubCategories.size(); i++) {
                    query += condition + " bs.subcategory = '" + selectedSubCategories.get(i) + "' ";
                }
            }
            System.out.println(query);

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString(1));
                Attribute attribute = new Attribute();
                attribute.setAttribute_name(rs.getString(1));
                attrs.add(attribute);
            }
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        return attrs;
    }

    public  ArrayList<BusinessSubCategories> getSubCategories(ArrayList<String> selectedCategories, String condition){
        ArrayList<BusinessSubCategories> subs = new ArrayList<BusinessSubCategories>();
        try {
            String query;
            if(condition == "AND"){
                String subq = "(SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(0)+"')";
                if(selectedCategories.size() > 1){
                    for(int i=1; i<selectedCategories.size(); i++){
                        subq += " INTERSECT (SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(i) +"' )";
                    }
                }
                query = "SELECT DISTINCT bs.subcategory FROM BUSINESS_SUBCAT bs JOIN BUSINESS_CAT bc on bs.BUSINESSID = bc.BUSINESSID  WHERE bs.businessId IN ( "+subq+" )";

            }else{

                 query = "SELECT DISTINCT bs.subcategory FROM BUSINESS_SUBCAT bs JOIN BUSINESS_CAT bc on bs.BUSINESSID = bc.BUSINESSID  WHERE bc.CATEGORY = ";
                query += "'"+selectedCategories.get(0) +"' ";
                if(selectedCategories.size() > 1){
                    for(int i=1; i<selectedCategories.size(); i++){
                        query += condition+" bc.category = '"+selectedCategories.get(i) +"' ";
                    }
                }
            }
            System.out.println(query);
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString(1));
                BusinessSubCategories sub = new BusinessSubCategories();
                sub.setSubCategoryName(rs.getString(1));
                subs.add(sub);
            }
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        return subs;
    }

    public ArrayList<BusinessCategories> getAllCategories(){
        ArrayList<BusinessCategories> cats = new ArrayList<BusinessCategories>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT category FROM BUSINESS_CAT  ORDER BY category");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString(1));
                BusinessCategories cat = new BusinessCategories();
                cat.setCategoryName(rs.getString(1));
                cats.add(cat);
            }
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        return cats;
    }

    public ArrayList<Business> queryBusinessByCategory(ArrayList<String> selectedCategories, String condition){
        ArrayList<Business> businesses = new ArrayList<Business>();
        String query;
        if(condition == "AND"){
        String subq = "SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(0)+"'";
            if(selectedCategories.size() > 1){
                for(int i=1; i<selectedCategories.size(); i++){
                    subq += " INTERSECT SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(i) +"' ";
                }
            }
            query = "SELECT DISTINCT b.businessId, b.name, b.city, b.state, b.stars  FROM BUSINESS b JOIN BUSINESS_CAT bc on bc.BUSINESSID = b.BUSINESSID  WHERE b.businessId IN ( "+subq+" )";

        }else{
            query = "SELECT DISTINCT b.businessId, b.name, b.city, b.state, b.stars  FROM BUSINESS b JOIN BUSINESS_CAT bc on bc.BUSINESSID = b.BUSINESSID  WHERE bc.category = ";
            query += "'"+selectedCategories.get(0) +"' ";
            if(selectedCategories.size() > 1){
                for(int i=1; i<selectedCategories.size(); i++){
                    query += condition+" bc.category = '"+selectedCategories.get(i) +"' ";
                }
            }
        }
        System.out.println(query);
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Business b = new Business();
                b.setBusinessId(rs.getString(1));
                b.setName(rs.getString(2));
                b.setCity(rs.getString(3));
                b.setState(rs.getString(4));
                b.setStars(rs.getFloat(5));
                businesses.add(b);
            }
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        return businesses;
    }

    public ArrayList<Business> queryBusinessByCategorySubCategory(ArrayList<String> selectedCategories, ArrayList<String> selectedSubCategories, String condition){
        ArrayList<Business> businesses = new ArrayList<Business>();
        String query = "";
        if(condition == "AND"){
            String subq = "(SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(0)+"')";
            if(selectedCategories.size() > 1){
                for(int i=1; i<selectedCategories.size(); i++){
                    subq += " INTERSECT (SELECT businessId FROM BUSINESS_CAT WHERE  category = '"+selectedCategories.get(i) +"' )";
                }
            }
            query = "SELECT DISTINCT b.businessId, b.name, b.city, b.state, b.stars  FROM BUSINESS b JOIN BUSINESS_CAT bc on bc.BUSINESSID = b.BUSINESSID  JOIN BUSINESS_SUBCAT bs ON bs.BUSINESSID = b.BUSINESSID WHERE  b.businessId IN ( "+subq+" )";

        }else {
             query = "SELECT DISTINCT b.businessId, b.name, b.city, b.state, b.stars  FROM BUSINESS b JOIN BUSINESS_CAT bc on bc.BUSINESSID = b.BUSINESSID  JOIN BUSINESS_SUBCAT bs ON bs.BUSINESSID = b.BUSINESSID WHERE bc.category = ";
            query += "'" + selectedCategories.get(0) + "' ";
            if (selectedCategories.size() > 1) {
                for (int i = 1; i < selectedCategories.size(); i++) {
                    query += condition + " bc.category = '" + selectedCategories.get(i) + "' ";
                }
            }

        }
        query += " AND bs.subcategory = '" + selectedSubCategories.get(0) + "' ";
        if (selectedSubCategories.size() > 1) {
            for (int i = 1; i < selectedSubCategories.size(); i++) {
                query += condition + " bs.subcategory = '" + selectedSubCategories.get(i) + "' ";
            }
        }
        System.out.println(query);
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Business b = new Business();
                b.setBusinessId(rs.getString(1));
                b.setName(rs.getString(2));
                b.setCity(rs.getString(3));
                b.setState(rs.getString(4));
                b.setStars(rs.getFloat(5));
                businesses.add(b);
            }
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        return businesses;
    }

    public ArrayList<Business> queryBusiness(ArrayList<String> selectedAttributes, ArrayList<String> selectedCategories, ArrayList<String> selectedSubCategories, String condition){
        ArrayList<Business> businesses = new ArrayList<Business>();
        try {
            String query = "SELECT DISTINCT b.businessId, b.name, b.city, b.state, b.stars  FROM BUSINESS b JOIN BUSINESS_ATTR ba on b.BUSINESSID = ba.BUSINESSID   JOIN BUSINESS_CAT bc on bc.BUSINESSID = b.BUSINESSID  JOIN BUSINESS_SUBCAT bs ON bs.BUSINESSID = b.BUSINESSID  WHERE ba.attr_name = ";
            query += "'"+selectedAttributes.get(0) +"' ";
            if(selectedAttributes.size() > 1){
                for(int i=1; i<selectedAttributes.size(); i++){
                    query += condition+"  ba.attr_name = '"+selectedAttributes.get(i) +"' ";
                }
            }
            query += " AND bc.category = '"+selectedCategories.get(0) +"' ";
            if(selectedCategories.size() > 1){
                for(int i=1; i<selectedCategories.size(); i++){
                    query += condition+" bc.category = '"+selectedCategories.get(i) +"' ";
                }
            }
            query += " AND bs.subcategory = '"+selectedSubCategories.get(0)+"' ";
            if(selectedSubCategories.size() > 1){
                for(int i=1; i<selectedSubCategories.size(); i++){
                    query += condition+" bs.subcategory = '"+selectedSubCategories.get(i) +"' ";
                }
            }
            System.out.println(query);

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Business b = new Business();
                b.setBusinessId(rs.getString(1));
                b.setName(rs.getString(2));
                b.setCity(rs.getString(3));
                b.setState(rs.getString(4));
                b.setStars(rs.getFloat(5));
                businesses.add(b);
            }
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        return businesses;
    }


    public ArrayList<Business> advancedQueryBusiness(ArrayList<String> selectedCategories, ArrayList<String> selectedSubCategories, ArrayList<String> selectedAttributes, String city, String state, String day, String from, String to, String condition){
        ArrayList<Business> businesses = new ArrayList<Business>();
        try {
            String query = "SELECT DISTINCT b.businessId, b.name, b.city, b.state, b.stars  FROM BUSINESS b  LEFT JOIN BUSINESS_ATTR ba ON b.BUSINESSID = ba.BUSINESSID LEFT JOIN BUSINESS_CAT bc on bc.BUSINESSID = b.BUSINESSID  LEFT JOIN BUSINESS_SUBCAT bs ON bs.BUSINESSID = b.BUSINESSID LEFT JOIN BUSINESS_HOURS bh ON b.BUSINESSID = bh.BUSINESSID  WHERE bc.category =  ";
            query += " '"+selectedCategories.get(0) +"' ";
            if(selectedCategories.size() > 1){
                for(int i=1; i<selectedCategories.size(); i++){
                    query += condition+" bc.category = '"+selectedCategories.get(i) +"' ";
                }
            }
            query += " AND ba.attr_name =  '"+selectedAttributes.get(0) +"' ";
            if(selectedAttributes.size() > 1){
                for(int i=1; i<selectedAttributes.size(); i++){
                    query += condition+"  ba.attr_name = '"+selectedAttributes.get(i) +"' ";
                }
            }

            query += " AND bs.subcategory = '"+selectedSubCategories.get(0)+"' ";
            if(selectedSubCategories.size() > 1){
                for(int i=1; i<selectedSubCategories.size(); i++){
                    query += condition+" bs.subcategory = '"+selectedSubCategories.get(i) +"' ";
                }
            }

            if(city.length() > 0 && city!= null && !city.isEmpty()){
                query += " AND b.city  LIKE  '%"+ city+"%' ";
            }
            if(state.length() > 0 && state!= null && !state.isEmpty()){
                query += "AND b.state  LIKE  '%"+ state+"%' ";
            }
            if(day.length() > 0 && day != null && !day.isEmpty()){
                query += "AND bh.day = '"+day+"'";
            }
            if(from.length() > 0 && from != null && !from.isEmpty()){
                String fromDate = "1970-01-01 "+from+":00";
                query += "AND from_time >= to_timestamp('"+fromDate+"', 'yyyy-mm-dd hh24:mi:ss')";
            }
            if(to.length() > 0 && to != null && !to.isEmpty()){
                String toDate = "1970-01-01 "+to+":00";
                query += "AND to_time <= to_timestamp('"+toDate+"', 'yyyy-mm-dd hh24:mi:ss')";
            }

            System.out.println(query);

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Business b = new Business();
                b.setBusinessId(rs.getString(1));
                b.setName(rs.getString(2));
                b.setCity(rs.getString(3));
                b.setState(rs.getString(4));
                b.setStars(rs.getFloat(5));
                businesses.add(b);
            }
        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        return businesses;
    }

    public ArrayList<Reviews> getReviews(String businessId){
        ArrayList<Reviews> reviews = new ArrayList<Reviews>();
        try {
            String query = "SELECT r.publishDate, r.stars, r.text, u.name,  r.VOTES_USEFUL FROM  REVIEWS r JOIN Users u ON u.userId = r.userId WHERE  r.BUSINESSID = '"+businessId+"'" ;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Reviews r = new Reviews();
                r.setDate(rs.getDate(1).toString());
                r.setStars(rs.getInt(2));
                r.setText(rs.getString(3));
                r.setUserId(rs.getString(4));
                r.setVotesUseful(rs.getInt(5));
                reviews.add(r);
            }

        }catch (SQLException se){
            System.err.println("Query error: SQL Exception");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("Query error ");
            e.printStackTrace();
        }
        return reviews;
    }


}
