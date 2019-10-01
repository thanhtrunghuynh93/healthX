package com.example.healthx.models;

import java.util.List;

public class FoodListModel {

    private List<ResultBean> resultBeans;

    public List<ResultBean> getResultBeans() {
        return resultBeans;
    }

    public void setResultBeans(List<ResultBean> Servers) {
        this.resultBeans = Servers;
    }

    public static class ResultBean {


        private String id;
        private String time;
        private String date;
        private String meal;
        private String food;
        private String location;

        public ResultBean(String id, String date,String time,  String meal, String food, String location) {
            this.id = id;
            this.date = date;
            this.time = time;
            this.meal = meal;
            this.food = food;
            this.location = location;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMeal() {
            return meal;
        }

        public void setMeal(String meal) {
            this.meal = meal;
        }

        public String getFood() {
            return food;
        }

        public void setFood(String food) {
            this.food = food;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
