package com.landongavin.entities;


public class StripeToken {

    private String id;
    private String email;
    private String object;
    private String clientIp;
    private int created;
    private boolean livemode;
    private String type;
    private boolean used;
    //private Card card;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public boolean isLivemode() {
        return livemode;
    }

    public void setLivemode(boolean livemode) {
        this.livemode = livemode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    /*public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "{livemode=" + this.livemode
                + ", created=" + this.created
                + ", client_ip=" + this.client_ip
                + ", id=" + this.id
                + ", used=" + this.used
                + ", type=" + this.type
                + ", email=" + this.email
                + ", object=" + this.object


                + ", card={address_zip_check=" + this.card.getAddress_zip_check()
                + ", country=" + this.card.getCountry()
                + ", last4=" + this.card.getLast4()
                + ", funding=" + this.card.getFunding()
                + ", address_country=" + this.card.getAddress_country()
                + ", exp_month=" + this.card.getExp_month()
                + ", exp_year=" + this.card.getExp_year()
                + ", address_city=" + this.card.getAddress_city()
                + ", cvc_check=" + this.card.getCvc_check()
                + ", address_line1=" + this.card.getAddress_line1()
                + ", name=" + this.card.getName()
                + ", address_line1_check=" + this.card.getAddress_line1_check()
                + ", address_zip=" + this.card.getAddress_zip()
                + ", id=" + this.card.getId()
                + ", brand=" + this.card.getBrand()
                + ", object=" + this.card.getObject()
                + ", tokenenization_method=" + this.card.getTokenenization_method()
                + ", metadata=" + this.card.getMetadata() //this may break..... at some point
                + ", fingerprint=" + this.card.getFingerprint()
                + ", dynamic_last4=" + this.card.getDynamic_last4()
                + ", address_state=" + this.card.getAddress_state()
                + ", address_line2=" + this.card.getAddress_line2()
                + "}}";
    }
*/
}
