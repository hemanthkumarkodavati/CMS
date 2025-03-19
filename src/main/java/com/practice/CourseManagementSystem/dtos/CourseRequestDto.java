package com.practice.CourseManagementSystem.dtos;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CourseRequestDto {

    private String title;

    private String  description;

    private Double price;

    private List<LessonDto> lessonDtos;

    private List<Integer> instructorIds;

}