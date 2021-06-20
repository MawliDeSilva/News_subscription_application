package com.company.newsPortal;

public interface Subscriber {

    String name="";
    String email_address="";

    public void notifySubbscriber(String news);

    public String getName();
}
