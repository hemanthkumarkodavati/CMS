package com.practice.CourseManagementSystem.servicesImpl;

import com.practice.CourseManagementSystem.dtos.InstructorRequestDto;
import com.practice.CourseManagementSystem.model.CourseEntity;
import com.practice.CourseManagementSystem.model.InstructorEntity;
import com.practice.CourseManagementSystem.repository.CourseRepository;
import com.practice.CourseManagementSystem.repository.InstructorRepository;
import com.practice.CourseManagementSystem.services.InstructorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository, CourseRepository courseRepository){
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void createInstructor(InstructorRequestDto instructorRequestDto) throws Exception {
        try {
            InstructorEntity instructorEntity = convertDtoToEntity(instructorRequestDto);
            instructorRepository.save(instructorEntity);
        }
        catch (Exception e){
            throw new Exception("Some exception");
        }
    }

    private InstructorEntity convertDtoToEntity(InstructorRequestDto instructorRequestDto) {
        InstructorEntity instructorEntity = new InstructorEntity();

        instructorEntity.setEmail(instructorRequestDto.getEmail());
        instructorEntity.setName(instructorRequestDto.getName());
        instructorEntity.setExpertise(instructorRequestDto.getExpertise());

        return instructorEntity;
    }

    @Transactional
    public void assignInstructor(Integer instructorId, Integer courseId) throws Exception {
        try {

            InstructorEntity instructorEntity = instructorRepository.findById(instructorId).
                    orElseThrow(()->new Exception("some exception"));
            CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow(()->new Exception("some exception"));
            if (courseEntity.getInstructorEntities() == null) {
                courseEntity.setInstructorEntities(new HashSet<>());
            }
            courseEntity.getInstructorEntities().add(instructorEntity);

            if (instructorEntity.getCourseEntityList() == null) {
                instructorEntity.setCourseEntityList(new HashSet<>());
            }
            instructorEntity.getCourseEntityList().add(courseEntity);

            courseRepository.save(courseEntity);
        }
        catch (Exception e){
            throw new Exception("Some exception");
        }
    }

    @Transactional
    public void deAssignInstructor(Integer instructorId, Integer courseId) throws Exception {
        try {
            InstructorEntity instructorEntity = instructorRepository.findById(instructorId)
                    .orElseThrow(() -> new Exception("Instructor not found with ID: " + instructorId));
            CourseEntity courseEntity = courseRepository.findById(courseId)
                    .orElseThrow(() -> new Exception("Course not found with ID: " + courseId));

            // Check if instructor is assigned to the course
            if (courseEntity.getInstructorEntities() == null ||
                    !courseEntity.getInstructorEntities().contains(instructorEntity)) {
                throw new Exception("Instructor is not assigned to this course");
            }

            // Remove from course side (owning side)
            courseEntity.getInstructorEntities().remove(instructorEntity);

            // Also update instructor side for consistency
            if (instructorEntity.getCourseEntityList() != null) {
                instructorEntity.getCourseEntityList().remove(courseEntity);
            }

            courseRepository.save(courseEntity);
        }
        catch (Exception e) {
            throw new Exception("Failed to de-assign instructor: " + e.getMessage());
        }
    }
}