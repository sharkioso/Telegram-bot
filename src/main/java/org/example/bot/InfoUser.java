package org.example.bot;

import java.util.Objects;

public class InfoUser {

    private String name;
    private String gender;
    private String age;
    private String town;
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return  Integer.parseInt(age);
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Boolean getGender() {
        return gender.equalsIgnoreCase( "Мужчина");
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAbout() {
        return description;
    }

    public void setAbout(String about) {
        this.description = about;
    }

    public String allInfo() {
        return (this.name + " " + this.age + " " + this.town + "\n" + this.description);
    }
}
