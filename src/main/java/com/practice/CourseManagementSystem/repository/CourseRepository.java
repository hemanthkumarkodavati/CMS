package com.practice.CourseManagementSystem.repository;

import com.practice.CourseManagementSystem.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {

}