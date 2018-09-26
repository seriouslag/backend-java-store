package com.chiefsretro.entities;

import com.stripe.model.Address;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="users")
public class User {

    @Id
    private long userId;
    private String userEmail;
    private String userName;
    private Address[] userAddresses;
}
