package com.example.demo.student;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class StudentServiceTest {

    @Mock
    StudentRepository studentRepository = mock(StudentRepository.class);
    private final StudentService objectToTest = new StudentService(studentRepository);


    @Test
    @DisplayName("Add new student that exist")
    public void addNewStudentThatExistTest() {
        Student student = mock(Student.class);
        when(studentRepository.findStudentByEmail(student.getEmail())).thenReturn(Optional.of(student));
        Assertions.assertThatThrownBy(
                        () -> objectToTest.addNewStudent(student)
                )
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("email taken");
    }

    @Test
    @DisplayName("Add new student successfully")
    public void addNewStudentSuccessfullyTest() {
        Student student = mock(Student.class);
        try {
            when(studentRepository.findStudentByEmail(student.getEmail())).thenReturn(null);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            objectToTest.addNewStudent(student);
        }
    }

    @Test
    @DisplayName("delete student when student not found")
    public void deleteStudentNotFoundTest() {
        when(studentRepository.existsById(1L)).thenReturn(false);
        Assertions.assertThatThrownBy(
                        () -> objectToTest.deleteStudent(1L)
                )
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("student with id '1' does not exists");
    }

    @Test
    @DisplayName("delete student successfully")
    public void deleteStudentSuccessfullyTest() {
        Mockito.when(studentRepository.existsById(1L)).thenReturn(true);
        objectToTest.deleteStudent(1L);
        verify(studentRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Update student successfully")
    public void updateStudentSuccessfullyTest() {
        final var newName = "cedric";
        final var newEmail = "cedric@lao-sarl.cm";
        final var id = 1L;

        Student student = mock(Student.class);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        objectToTest.updateStudent(id, newName, newEmail);

        verify(student).setName(newName);
        verify(student).setEmail(newEmail);
        verify(studentRepository).save(student);
    }

    @Test
    @DisplayName("Update student when student not found")
    public void updateStudentStudentNotFoundTest() {
        Assertions.assertThatThrownBy(
                        () -> objectToTest.updateStudent(1L, "cedric", "cedric@lao-sarl.cm")
                )
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("student with id '1' does not exists");
    }

    @Test
    @DisplayName("Get all the student")
    public void getStudentsTest() {
        final var student1 = mock(Student.class);
        final var student2 = mock(Student.class);
        Mockito.when(studentRepository.findAll()).thenReturn(List.of(student1, student2));

        final var students = objectToTest.getStudents();

        assertThat(students)
                .hasSize(2)
                .contains(student1, student2);
    }

}
