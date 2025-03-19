package com.practice.CourseManagementSystem.services;

import com.practice.CourseManagementSystem.dtos.StudentRequestDto;
import com.practice.CourseManagementSystem.dtos.StudentResponseDto;

public interface StudentService {
    StudentResponseDto createStudent(StudentRequestDto studentRequestDto) throws Exception;

}