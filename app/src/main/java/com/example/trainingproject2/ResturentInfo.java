package com.example.trainingproject2;

import java.util.List;

public class ResturentInfo {
    private String id;
    private String name;
    private String image_url;
    private Float rating;
    private Boolean is_closed;
   private List<String> photos;
   private String phone ;
   private int review_count ;
   public ResturentInfo(String id ,String name ,String image_url ,Float rating,Boolean is_open_now,List<String> photos ,String  phone,int review_count ){
       this.id=id;
       this.name=name;
       this.image_url=image_url;
       this.rating=rating;
       this.is_closed=is_open_now;
       this.photos=photos;
       this.phone=phone;
       this.review_count=review_count;
   }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Boolean getIs_closed() {
        return is_closed;
    }

    public void setIs_closed(Boolean is_open_now) {
        this.is_closed = is_open_now;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getReview_count() {
        return review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }
}
