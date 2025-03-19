package com.practice.CourseManagementSystem.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonDto {

    private Integer id;
    private String title;
    private String content;
    private Double duration;

}
