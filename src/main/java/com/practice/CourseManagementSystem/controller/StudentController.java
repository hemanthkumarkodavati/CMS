package com.practice.CourseManagementSystem.controller;

import com.practice.CourseManagementSystem.commons.MessageType;
import com.practice.CourseManagementSystem.dtos.MessageDto;
import com.practice.CourseManagementSystem.dtos.StudentRequestDto;
import com.practice.CourseManagementSystem.dtos.StudentResponseDto;
import com.practice.CourseManagementSystem.model.ResponseModel;
import com.practice.CourseManagementSystem.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create-student")
    public ResponseModel createStudent(@RequestBody StudentRequestDto studentRequestDto) {
        try {
            StudentResponseDto studentResponseDto = studentService.createStudent(studentRequestDto);
            return new ResponseModel(HttpStatus.OK.value(), studentResponseDto,
                    new MessageDto("created" , MessageType.SUCCESS));
        }
        catch (Exception e){
            return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    new MessageDto("created" , MessageType.ERROR));
        }
    }
}