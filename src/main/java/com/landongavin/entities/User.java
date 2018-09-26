package com.landongavin.entities;

import com.stripe.model.Address;

import javax.persistence.*;
import java.util.Set;

@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long userId;
    private String userEmail;
    private String userName;
    private Address[] userAddresses;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "rating_id", insertable = false, updatable = false)
    private Set<Rating> userRatings;

}
