package com.practice.CourseManagementSystem.services;


import com.practice.CourseManagementSystem.dtos.CourseRequestDto;
import com.practice.CourseManagementSystem.dtos.CourseResponseDto;

public interface CourseService {

    CourseResponseDto createCourse(CourseRequestDto courseRequestDto);

    void enrollStudent(int studentId, int courseId);

    void unenrollStudent(int studentId, int courseId);

    CourseResponseDto getCourseById(Integer courseId);

}