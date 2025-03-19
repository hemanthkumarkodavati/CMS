package com.practice.CourseManagementSystem.controller;

import com.practice.CourseManagementSystem.services.CourseService;

import com.practice.CourseManagementSystem.commons.MessageType;
import com.practice.CourseManagementSystem.dtos.CourseRequestDto;
import com.practice.CourseManagementSystem.dtos.CourseResponseDto;
import com.practice.CourseManagementSystem.dtos.MessageDto;
import com.practice.CourseManagementSystem.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    public  CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }
    @PostMapping("/create-course")
    public ResponseModel createCourse(@RequestBody CourseRequestDto courseRequestDto){

        try {
            CourseResponseDto responseDto = courseService.createCourse(courseRequestDto);
            return new ResponseModel(
                    HttpStatus.CREATED.value(),
                    responseDto,
                    new MessageDto("Course created successfully", MessageType.SUCCESS)
            );
        } catch (Exception e) {
            return new ResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    new MessageDto("Failed to create course: " + e.getMessage(), MessageType.ERROR)
            );
        }
    }

    @PostMapping("/enroll-students")
    public ResponseModel enrollStudents(@RequestParam int studentId , @RequestParam int courseId){
        try {
            courseService.enrollStudent(studentId, courseId);
            return new ResponseModel(
                    HttpStatus.CREATED.value(),
                    studentId,
                    new MessageDto("student enrolled successfully", MessageType.SUCCESS)
            );
        }
        catch (Exception e){
            return new ResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    new MessageDto("Failed to enroll the student : " + e.getMessage(), MessageType.ERROR)
            );
        }
    }

    @DeleteMapping("/unroll-students")
    public ResponseModel  unrollStudents(@RequestParam int studentId , @RequestParam int courseId){
        try {
            courseService.unenrollStudent(studentId, courseId);
            return new ResponseModel(
                    HttpStatus.CREATED.value(),
                    studentId,
                    new MessageDto("student unrolled successfully", MessageType.SUCCESS)
            );
        }
        catch (Exception e){
            return new ResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    new MessageDto("Failed to unroll the student : " + e.getMessage(), MessageType.ERROR)
            );
        }

    }

    @GetMapping("/course-details")
    public ResponseModel getCourseDetails(@RequestParam int courseId){
        try {
            CourseResponseDto courseResponseDto =  courseService.getCourseById(courseId);
            return new ResponseModel(
                    HttpStatus.OK.value(), courseResponseDto,
                    new MessageDto("student enrolled successfully", MessageType.SUCCESS)
            );
        }
        catch (Exception e){
            return new ResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    new MessageDto("" + e.getMessage(), MessageType.ERROR)
            );
        }
    }

}