package com.practice.CourseManagementSystem.controller;

import com.practice.CourseManagementSystem.commons.MessageType;
import com.practice.CourseManagementSystem.dtos.InstructorRequestDto;
import com.practice.CourseManagementSystem.dtos.MessageDto;
import com.practice.CourseManagementSystem.model.ResponseModel;
import com.practice.CourseManagementSystem.services.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService){
        this.instructorService = instructorService;
    }

    @PostMapping("/create-instructor")
    public ResponseModel createInstructor(@RequestBody InstructorRequestDto instructorRequestDto){
        try {
            instructorService.createInstructor(instructorRequestDto);
            return new ResponseModel(
                    HttpStatus.CREATED.value(),
                    instructorRequestDto,
                    new MessageDto("Instructor created successfully", MessageType.SUCCESS)
            );
        } catch (Exception e) {
            return new ResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    new MessageDto("Failed to create instructor: " + e.getMessage(), MessageType.ERROR)
            );
        }
    }

    @PostMapping("/instructor-assignment")
    public ResponseModel assignInstructor(@RequestParam Integer instructorId , @RequestParam Integer courseId){
        try {
            instructorService.assignInstructor(instructorId,courseId);

            return new ResponseModel(
                    HttpStatus.CREATED.value(),
                    instructorId,
                    new MessageDto("Instructor with id :" + instructorId +
                            "is successfully assigned to course with id :"+courseId , MessageType.SUCCESS)
            );
        } catch (Exception e) {
            return new ResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    new MessageDto("Failed to assign instructor: " + e.getMessage(), MessageType.ERROR)
            );
        }
    }

    @PostMapping("/instructor-deassignment")
    public ResponseModel deAssignInstructor(@RequestParam Integer instructorId , @RequestParam Integer courseId){
        try {
            instructorService.deAssignInstructor(instructorId,courseId);

            return new ResponseModel(
                    HttpStatus.CREATED.value(),
                    instructorId,
                    new MessageDto("Instructor with id :" + instructorId +
                            "is successfully de assigned to course with id :"+courseId , MessageType.SUCCESS)
            );
        } catch (Exception e) {
            return new ResponseModel(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    new MessageDto("Failed to de assign instructor: " + e.getMessage(), MessageType.ERROR)
            );
        }
    }
}
