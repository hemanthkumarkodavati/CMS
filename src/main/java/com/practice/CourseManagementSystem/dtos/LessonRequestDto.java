package com.practice.CourseManagementSystem.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonRequestDto {

    private String title;
    private String content;
    private Double duration;
    private Integer courseId;

}

