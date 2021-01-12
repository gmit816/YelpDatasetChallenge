package main.java.Models;

import java.util.Date;
import java.util.List;
import main.java.Models.Compliments;
import main.java.Models.Elite;
import main.java.Models.Friends;
import java.text.SimpleDateFormat;


public class User{
    private String yelpingSince;
    private int votesFunny;
    private int votesUseful;
    private int votesCool;
    private int reviewCount;
    private String name;
    private String userId;
    private List<Friends> friends;
    private int fans;
    private float averageStars;
    private String type;
    private Compliments compliments;
    private List<Elite> elite;

    public int getVotesFunny() {
        return votesFunny;
    }
    public void setVotesFunny(int votesFunny) {
        this.votesFunny = votesFunny;
    }
    public int getVotesUseful() {
        return votesUseful;
    }
    public void setVotesUseful(int votesUseful) {
        this.votesUseful = votesUseful;
    }
    public int getVotesCool() {
        return votesCool;
    }
    public void setVotesCool(int votesCool) {
        this.votesCool = votesCool;
    }
    public String getYelpingSince(){
        return yelpingSince;
    }
    public void setYelpingSince(String input){
        this.yelpingSince = input;
    }
    public int getReviewCount(){
        return reviewCount;
    }
    public void setReviewCount(int input){
        this.reviewCount = input;
    }
    public String getName(){
        return name;
    }
    public void setName(String input){
        this.name = input;
    }
    public String getUserId(){
        return userId;
    }
    public void setUserId(String input){
        this.userId = input;
    }
    public List<Friends> getFriends(){
        return friends;
    }
    public void setFriends(List<Friends> input){
        this.friends = input;
    }
    public int getFans(){
        return fans;
    }
    public void setFans(int input){
        this.fans = input;
    }
    public float getAverageStars(){
        return averageStars;
    }
    public void setAverageStars(float input){
        this.averageStars = input;
    }
    public String getType(){
        return type;
    }
    public void setType(String input){
        this.type = input;
    }
    public Compliments getCompliments(){
        return compliments;
    }
    public void setCompliments(Compliments input){
        this.compliments = input;
    }
    public List<Elite> getElite(){
        return elite;
    }
    public void setElite(List<Elite> input){
        this.elite = input;
    }

}
