package com.practice.CourseManagementSystem.servicesImpl;

import com.practice.CourseManagementSystem.Exceptions.CourseException;
import com.practice.CourseManagementSystem.Exceptions.CourseNotFoundException;
import com.practice.CourseManagementSystem.Exceptions.ResourceNotFoundException;
import com.practice.CourseManagementSystem.Exceptions.StudentNotFoundException;
import com.practice.CourseManagementSystem.dtos.CourseRequestDto;
import com.practice.CourseManagementSystem.dtos.CourseResponseDto;
import com.practice.CourseManagementSystem.dtos.InstructorDto;
import com.practice.CourseManagementSystem.dtos .LessonDto;
import com.practice.CourseManagementSystem.model.CourseEntity;
import com.practice.CourseManagementSystem.model.InstructorEntity;
import com.practice.CourseManagementSystem.model.LessonEntity;
import com.practice.CourseManagementSystem.model.StudentEntity;
import com.practice.CourseManagementSystem.repository.CourseRepository;
import com.practice.CourseManagementSystem.repository.InstructorRepository;
import com.practice.CourseManagementSystem.repository.LessonRepository;
import com.practice.CourseManagementSystem.repository.StudentRepository;
import com.practice.CourseManagementSystem.services.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;

    public CourseServiceImpl(
            CourseRepository courseRepository, InstructorRepository instructorRepository,
            LessonRepository lessonRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public CourseResponseDto createCourse(CourseRequestDto courseRequestDto) {
        CourseEntity courseEntity = convertDtoToEntity(courseRequestDto);

        checkInstructorIsAvailable(courseRequestDto.getInstructorIds());

        CourseEntity savedCourse = courseRepository.save(courseEntity);

        // Assign instructors
        if (courseRequestDto.getInstructorIds() != null && !courseRequestDto.getInstructorIds().isEmpty()) {
            Set<InstructorEntity> instructors = new HashSet<>(instructorRepository.findAllById(
                    courseRequestDto.getInstructorIds()));
            savedCourse.setInstructorEntities(instructors);
        }

        if (courseRequestDto.getLessonDtos() != null && !courseRequestDto.getLessonDtos().isEmpty()) {

            List<LessonEntity> lessons = new ArrayList<>();
            for (LessonDto lessonDto : courseRequestDto.getLessonDtos()) {
                LessonEntity lesson = new LessonEntity();
                lesson.setTitle(lessonDto.getTitle());
                lesson.setContent(lessonDto.getContent());
                lesson.setDuration(lessonDto.getDuration());
                lesson.setCourse(savedCourse);  // Set the course reference

                lessons.add(lesson);
            }
            lessonRepository.saveAll(lessons);
        }
        CourseEntity refreshedCourse = courseRepository.findById(savedCourse.getId())
                .orElseThrow(() -> new CourseException("Failed to fetch created course"));

        // Convert to response DTO
        return convertToResponseDto(refreshedCourse);
    }

    private void checkInstructorIsAvailable(List<Integer> instructorIds) {
        for (Integer id: instructorIds) {
            InstructorEntity instructorEntity = instructorRepository.findById(id).
                    orElseThrow(() -> new EntityNotFoundException("Instructor not found with ID: " + id));
        }
    }

    private CourseResponseDto convertToResponseDto(CourseEntity course) {
        CourseResponseDto responseDto = new CourseResponseDto();
        responseDto.setId(course.getId());
        responseDto.setName(course.getName());
        responseDto.setDescription(course.getDescription());
        responseDto.setPrice(course.getPrice());

        // Map instructors
        if (course.getInstructorEntities() != null) {
            Set<InstructorDto> instructorDtos = convertInstructorEntityToDto(course.getInstructorEntities());

            responseDto.setInstructors(instructorDtos);
        }

        // Map lessons
        if (course.getLessonEntities() != null) {
            List<LessonDto> lessonDtos = convertLessonEntityToDto(course.getLessonEntities());

            responseDto.setLessons(lessonDtos);
        }

        return responseDto;
    }

    private  List<LessonDto> convertLessonEntityToDto(List<LessonEntity> course){
        return course.stream()
                .map(lesson -> {
                    LessonDto dto = new LessonDto();
                    dto.setId(lesson.getId());
                    dto.setTitle(lesson.getTitle());
                    dto.setContent(lesson.getContent());
                    dto.setDuration(lesson.getDuration());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private Set<InstructorDto> convertInstructorEntityToDto(Set<InstructorEntity> instructorEntities){
        return instructorEntities.stream().map(instructorEntity -> {
            InstructorDto dto = new InstructorDto();
            dto.setId(instructorEntity.getId());
            dto.setName(instructorEntity.getName());
            return dto;
        }).collect(Collectors.toSet());
    }

    private CourseEntity convertDtoToEntity(CourseRequestDto courseRequestDto) {

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName(courseRequestDto.getTitle());
        courseEntity.setDescription(courseRequestDto.getDescription());
        courseEntity.setPrice(courseRequestDto.getPrice());
        return courseEntity;
    }

    @Transactional
    public void enrollStudent(int studentId, int courseId) {
        CourseEntity courseEntity = courseRepository.findById(courseId).
                orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
        courseEntity.getStudentEntityList().add(studentEntity);
        courseRepository.save(courseEntity);
    }

    public void unenrollStudent(int studentId, int courseId){
        CourseEntity courseEntity = courseRepository.findById(courseId).
                orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
        courseEntity.getStudentEntityList().remove(studentEntity);
        courseRepository.save(courseEntity);
    }

    public CourseResponseDto getCourseById(Integer courseId){
        CourseEntity courseEntity = courseRepository.findById(courseId).
                orElseThrow(()->new CourseNotFoundException("courseId is not available"));

        return convertEntityToDto(courseEntity);

    }

    private CourseResponseDto convertEntityToDto(CourseEntity courseEntity) {
        CourseResponseDto courseResponseDto = new CourseResponseDto();
        courseResponseDto.setName(courseEntity.getName());
        courseResponseDto.setDescription(courseEntity.getDescription());
        courseResponseDto.setLessons(convertLessonEntityToDto(courseEntity.getLessonEntities()));
        courseResponseDto.setId(courseEntity.getId());
        courseResponseDto.setInstructors(convertInstructorEntityToDto(courseEntity.getInstructorEntities()));
        courseResponseDto.setPrice(courseEntity.getPrice());
        return courseResponseDto;
    }
}