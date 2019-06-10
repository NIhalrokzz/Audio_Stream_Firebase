package com.example.login;

/**
 * Created by Lincoln on 15/01/16.
 */
public class Movie {
    private int name;
    private int dieturl,excersiseurl,symptomsurl;

    public Movie() {
    }

    public Movie(int name, int dieturl, int excersiseurl, int symptomsurl) {
        this.name = name;
        this.dieturl = dieturl;
        this.excersiseurl = excersiseurl;
        this.symptomsurl = symptomsurl;
    }

    public int getname() {
        return name;
    }

    public void setname(int name) {
        this.name = name;
    }

    public int getdieturl() {
        return dieturl;
    }

    public void setdieturl(int genre) {
        this.dieturl = genre;
    }

    public int getExcersiseurl(){
        return excersiseurl;
    }

    public int getSymptomsurl(){
        return symptomsurl;
    }
}
