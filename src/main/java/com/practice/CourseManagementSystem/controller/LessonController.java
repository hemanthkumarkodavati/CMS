package com.practice.CourseManagementSystem.controller;

import com.practice.CourseManagementSystem.commons.MessageType;
import com.practice.CourseManagementSystem.dtos.LessonRequestDto;
import com.practice.CourseManagementSystem.dtos.MessageDto;
import com.practice.CourseManagementSystem.model.ResponseModel;
import com.practice.CourseManagementSystem.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/lesson")
@RestController
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService){
        this.lessonService =lessonService;
    }

    @PostMapping("/add-lesson")
    public ResponseModel addLesson(@RequestBody LessonRequestDto lessonRequestDto){
        try {
            lessonService.addLesson(lessonRequestDto);

            return new ResponseModel(
                    HttpStatus.OK.value(), lessonRequestDto.getTitle(),
                    new MessageDto("lesson added successfully", MessageType.SUCCESS)
            );
        } catch (Exception e) {
            return new ResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    new MessageDto("" + e.getMessage(), MessageType.ERROR)
            );
        }
    }
}