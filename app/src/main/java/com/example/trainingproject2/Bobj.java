package com.example.trainingproject2;

import java.util.List;

class Bobj {

   private List<Business> businesses ;

    public List<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

    public class Business {
        private String id;
        private String name;
        private String image_url;
        private Float rating;
        private String price ;


        public Business(String id, String name, String image_url, Float rating, String price) {
            this.id = id;
            this.name = name;
            this.image_url = image_url;
            this.rating = rating;
            this.price = price;
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


        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
