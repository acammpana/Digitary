package models;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User extends Model {
    @Id
    @Constraints.Required
    public Integer id;
    @Constraints.Required
    public String name;
    @Constraints.Required
    public String email;
    public String address1;
    public String address2;
    public String town;
    public String postcode;
    public String country;
    public String phone;

    public User(){}

    public User(Integer id, String name, String email, String address1, String address2, String town, String postcode, String country, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.town = town;
        this.postcode = postcode;
        this.country = country;
        this.phone = phone;
    }
    public static Finder<Integer, User> find = new Finder<>(User.class);



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
