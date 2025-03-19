package com.practice.CourseManagementSystem.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CourseResponseDto {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Set<InstructorDto> instructors;
    private List<LessonDto> lessons;

}