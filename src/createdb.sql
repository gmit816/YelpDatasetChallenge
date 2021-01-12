CREATE TABLE Users(userId VARCHAR2(50) primary key, name VARCHAR2(50), yelpingSince date,  votesFunny int, votesUseful int, votesCool int, review_count int , fans int, averageStars float );

CREATE TABLE UserFriends(userId VARCHAR2(50), friend_id VARCHAR2(50) );

CREATE TABLE Reviews(reviewId VARCHAR2(50) primary key , stars number check (stars between 1 and 5), publishDate date , text VARCHAR2(150) , businessId VARCHAR2(50) , userId VARCHAR2(50),  votesFunny number, votes_cool number, votes_useful number);

CREATE TABLE BUSINESS(businessId VARCHAR2(50) primary key, name VARCHAR2(100), address VARCHAR2(200), city VARCHAR2(50), state VARCHAR2(10), review_count int, stars float);

CREATE TABLE BUSINESS_HOURS(businessId VARCHAR2(50), day VARCHAR2(50), from_time TIMESTAMP, to_time TIMESTAMP);

CREATE TABLE BUSINESS_CAT(businessId VARCHAR2(50), category VARCHAR2(50));

CREATE TABLE BUSINESS_SUBCAT(businessId VARCHAR2(50), subcategory VARCHAR2(50));

CREATE TABLE BUSINESS_ATTR(businessId VARCHAR2(50), attr_name VARCHAR2(50), attr_value VARCHAR2(50));

create index ReviewsIndex on Reviews ( reviewId, stars, publishDate, text, userId);

create index BusinessIndex on BUSINESS (businessId, name, city, state, stars);

--ALTER TABLESPACE SYSTEM ADD DATAFILE '/u01/app/oracle/oradata/XE/system1.dbf' SIZE 3000M;