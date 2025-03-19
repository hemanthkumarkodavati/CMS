package com.practice.CourseManagementSystem.servicesImpl;

import com.practice.CourseManagementSystem.dtos.StudentRequestDto;
import com.practice.CourseManagementSystem.dtos.StudentResponseDto;
import com.practice.CourseManagementSystem.model.StudentEntity;
import com.practice.CourseManagementSystem.repository.StudentRepository;
import com.practice.CourseManagementSystem.services.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentResponseDto createStudent(StudentRequestDto studentRequestDto) throws Exception {
        try {
            StudentEntity student = convertDtoToEntity(studentRequestDto);
            studentRepository.save(student);
        }
        catch (Exception e){
            throw new Exception();
        }
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setName(studentRequestDto.getName());
        return  studentResponseDto;

    }

    private StudentEntity convertDtoToEntity(StudentRequestDto studentRequestDto) {
        StudentEntity student = new StudentEntity();
        student.setEmail(studentRequestDto.getEmail());
        student.setName(studentRequestDto.getName());
        return student;
    }
}