package com.practice.CourseManagementSystem.repository;

import com.practice.CourseManagementSystem.model.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<LessonEntity, Integer> {

}