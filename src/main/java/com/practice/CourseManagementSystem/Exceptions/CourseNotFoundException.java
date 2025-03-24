package com.practice.CourseManagementSystem.Exceptions;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(String msg){
        super(msg);
    }
}
