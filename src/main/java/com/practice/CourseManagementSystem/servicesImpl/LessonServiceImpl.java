package com.practice.CourseManagementSystem.servicesImpl;

import com.practice.CourseManagementSystem.dtos.LessonRequestDto;
import com.practice.CourseManagementSystem.model.CourseEntity;
import com.practice.CourseManagementSystem.model.LessonEntity;
import com.practice.CourseManagementSystem.repository.CourseRepository;
import com.practice.CourseManagementSystem.repository.LessonRepository;
import com.practice.CourseManagementSystem.services.LessonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, CourseRepository courseRepository){
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void addLesson(LessonRequestDto lessonRequestDto) throws Exception {
        try {
            LessonEntity lessonEntity = convertDtoToEntity(lessonRequestDto);
            CourseEntity courseEntity = courseRepository.findById(lessonRequestDto.getCourseId())
                    .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + lessonRequestDto.getCourseId()));
            lessonEntity.setCourse(courseEntity);
            lessonRepository.save(lessonEntity);
        }
        catch (Exception e){
            throw new Exception("Some Exception occurred");
        }
    }

    private LessonEntity convertDtoToEntity(LessonRequestDto lessonRequestDto) {

        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setContent(lessonRequestDto.getContent());
        lessonEntity.setDuration(lessonRequestDto.getDuration());
        lessonEntity.setTitle(lessonRequestDto.getTitle());

        return  lessonEntity;
    }
}