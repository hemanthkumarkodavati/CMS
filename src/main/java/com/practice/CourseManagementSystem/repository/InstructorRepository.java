package com.practice.CourseManagementSystem.repository;

import com.practice.CourseManagementSystem.model.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository  extends JpaRepository<InstructorEntity, Integer> {

}