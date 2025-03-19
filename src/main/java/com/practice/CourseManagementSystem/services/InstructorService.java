package com.practice.CourseManagementSystem.services;


import com.practice.CourseManagementSystem.dtos.InstructorRequestDto;

public interface InstructorService {

    void createInstructor(InstructorRequestDto instructorRequestDto) throws Exception;

    void assignInstructor(Integer instructorId, Integer courseId) throws Exception;

    void deAssignInstructor(Integer instructorId, Integer courseId) throws Exception;

}

