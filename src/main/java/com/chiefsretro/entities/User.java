package com.chiefsretro.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    private Integer userId;

    private String userEmail;

    private String userFname;

    private String userLname;

    private String userGemail;

    private String userFemail;

    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
    @JsonManagedReference
    @OrderBy("productOptionId asc")
    @SortNatural
    private Set<CartItem> cartItems;


    public Integer getUserID() {
        return this.userId;
    }
    public void set(Integer userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return this.userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFname() {
        return this.userFname;
    }
    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getUserLname() {
        return this.userLname;
    }
    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public String getUserGemail() {
        return this.userGemail;
    }
    public void setUserGemail(String userGemail) {
        this.userGemail = userGemail;
    }

    public String getUserFemail() {
        return this.userFemail;
    }
    public void setUserFemail(String userFemail) {
        this.userFemail = userFemail;
    }

}
