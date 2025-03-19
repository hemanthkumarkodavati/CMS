package com.practice.CourseManagementSystem.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "course")
@Getter
@Setter
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String  description;

    private Double price;

    @ManyToMany()
    @JoinTable(name = "course_student",
            joinColumns = @JoinColumn( name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<StudentEntity> studentEntityList;

    @ManyToMany()
    @JoinTable(
            name = "course_instructor",  // name of the join table
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id")
    )
    private Set<InstructorEntity> instructorEntities;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<LessonEntity> lessonEntities = new ArrayList<>();
}