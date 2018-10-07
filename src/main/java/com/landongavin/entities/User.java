package com.landongavin.entities;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String email;
    private String name;
    private String stripeCustomerId;
    private String firebaseUid;

//    @ManyToMany(fetch= FetchType.LAZY)
//    @JoinTable(name = "user_addresses",
//            joinColumns = { @JoinColumn(name = "user_id") },
//            inverseJoinColumns = { @JoinColumn(name = "address_id")}
//    )
//    private Set<Address> address;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStripeCustomerId() { return stripeCustomerId; }
    public void setStripeCustomerId(String stripeCustomerId) { this.stripeCustomerId = stripeCustomerId; }
//    public Set<Address> getAddress() { return address; }
//    public void setAddress(Set<Address> address) { this.address = address; }
    public String getFirebaseUid() { return firebaseUid; }
    public void setFirebaseUid(String firebaseUid) { this.firebaseUid = firebaseUid; }
}
