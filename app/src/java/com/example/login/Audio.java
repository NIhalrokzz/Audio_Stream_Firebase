package com.example.login;

public class Audio {
    private String name;
    private String dieturl,exerciseurl,symptomsurl;

    public Audio() {
    }

    public Audio(String dieturl,String exerciseurl, String name, String symptomsurl) {
        this.dieturl = dieturl;
        this.exerciseurl = exerciseurl;
        this.name = name;
        this.symptomsurl = symptomsurl;

    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getdieturl() {
        return dieturl;
    }

    public void setdieturl(String genre) {
        this.dieturl = genre;
    }


    public String getexerciseurl(){
        return exerciseurl;
    }

    public String getsymptomsurl(){
        return symptomsurl;
    }
}
