package com.practice.CourseManagementSystem.Exceptions;

public class StudentNotFoundException extends RuntimeException{
   public StudentNotFoundException(String msg){
       super(msg);
   }
}
