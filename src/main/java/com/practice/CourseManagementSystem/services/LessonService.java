package com.practice.CourseManagementSystem.services;

import com.practice.CourseManagementSystem.dtos.LessonRequestDto;

public interface LessonService {
    void addLesson(LessonRequestDto lessonRequestDto) throws Exception;

}