package com.finalyearproject.fyp.common;


public class Message {

    public static final String updated= "UPDATED";
    public static final String deleted= "DELETED";
    public static final String added= "ADDED";
    public static final String removed= "REMOVED";
    public static final String invalidInput= "Invalid Input Error";

    public static String resourceNotFound(ResourceType resourceType, Long id){
        return resourceType +": " + id+ " NOT FOUND";
    }

    public static String fieldNotEmpty(String field){ return field.toUpperCase() + "CANNOT BE EMPTY"; }

    public static String updated(ResourceType resourceType){ return resourceType + updated;}

    public static String deleted(ResourceType resourceType){ return resourceType + deleted;}

    public static String added(ResourceType resourceType){ return resourceType + added;}

    public static String removed(ResourceType resourceType){ return resourceType + removed;}

}
