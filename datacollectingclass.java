package com.example.signup;

public class datacollectingclass {
    String name ,c_w,h,age  ;
    String gender,d,goal  ;
    public datacollectingclass() {
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getC_w() {
        return c_w;
    }

    public void setC_w(String c_w) {
        this.c_w = c_w;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public datacollectingclass(String name,String gender,String d,String c_w,String h,String age ,String goal ){
        this.name=name;
        this.gender=gender;
        this.d=d;
        this.c_w=c_w;
        this.h=h;
        this.age=age;
        this.goal=goal;
    }
}
