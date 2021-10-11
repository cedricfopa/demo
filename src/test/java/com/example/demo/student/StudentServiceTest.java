package com.example.demo.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;


public class StudentServiceTest {

    @Mock
    Optional<Student> optionalStudent ;
    @Mock
    StudentRepository studentRepository = mock(StudentRepository.class);
    private StudentService objectToTest =new StudentService(studentRepository);


    @Test
    public void addNewStudentTestTrue(){

        String email ="cedric@gmail.com";
        Student student = Student.builder()
                .name("herman")
                .email(email)
                .dob(LocalDate.of(1992, Month.SEPTEMBER, 19))
                .build();
        Mockito.when(optionalStudent).thenReturn(Optional.of(student));
        objectToTest.addNewStudent(student);
        assertTrue(optionalStudent.isPresent());
    }
    @Test
    public void addNewStudentTestFalse(){
        Student student = Student.builder()
                .name("herman")
                .email("cedric@gmail.com")
                .dob(LocalDate.of(1992, Month.SEPTEMBER, 19))
                .build();
        optionalStudent= studentRepository.findStudentByEmail(student.getEmail());
        Mockito.when(optionalStudent.isPresent()).thenReturn(false);
        objectToTest.addNewStudent(student);
        Mockito.verify(studentRepository).save(student);
    }
    @Test
    public void deleteStudentTestTrue(){
        Long studentId = Long.valueOf(0);
        Mockito.when(studentRepository.existsById(studentId)).thenReturn(true);
        objectToTest.deleteStudent(studentId);
        Mockito.verify(studentRepository).deleteById(studentId);
    }
    @Test
    public void deleteStudentTestfalse() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            //Code under test
            Long studentId = Long.valueOf(0);
            Mockito.when(studentRepository.existsById(studentId)).thenReturn(false);
            objectToTest.deleteStudent(studentId);
            Mockito.verify(studentRepository).deleteById(studentId);
            verify(studentRepository, times(0)).deleteById(studentId);
        });
    }
    @Test
    public void updateStudentTest(){
        Student student = Student.builder()
                .name("herman")
                .email("cedric@gmail.com")
                .dob(LocalDate.of(1992, Month.SEPTEMBER, 19))
                .build();
        Long studentId = Long.valueOf(0);
        String name = "cedric0";
        String email = "cedric@gmail.com";
        Mockito.when(studentRepository.existsById(studentId)).thenReturn(false);
        objectToTest.updateStudent(studentId,name,email);
        Mockito.verify(student).setName(name);
        Mockito.verify(student).setName(email);
        /*verify(studentRepository, times(0)).deleteById(studentId);*/

    }
    @Test
    public void getStudentsTest(){
        Student student = Student.builder()
                .name("herman")
                .email("cedric@gmail.com")
                .dob(LocalDate.of(1992, Month.SEPTEMBER, 19))
                .build();
        List<Student> list = new ArrayList<>();
        list.add(student);
        //list= objectToTest.getStudents();
        Mockito.when(studentRepository.findAll()).thenReturn(list);
        assertEquals(objectToTest.getStudents(),list);
        assertTrue(list.size()==1);
    }

}
