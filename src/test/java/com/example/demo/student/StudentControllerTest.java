package com.example.demo.student;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentControllerTest extends ControllerTest {
    @MockBean
    private StudentService studentService;

    @Override
    Object getController() {
        return new StudentController(studentService);
    }

    @Test
    @DisplayName("Get all students")
    public void getStudentsTest() {

        Student student1 = Student.builder()
                .name("fopa")
                .email("fopa@lao-sarl.cm")
                .dob(LocalDate.of(1999, Month.SEPTEMBER, 3))
                .build();
        Student student2 = Student.builder()
                .name("fopa3")
                .email("fopa3@lao-sarl.cm")
                .dob(LocalDate.of(1999, Month.SEPTEMBER, 9))
                .build();

        when(studentService.getStudents()).thenReturn(List.of(student1, student2));

        final var responseBody = webTestClient
                .get()
                .uri("/api/v1/student")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBodyList(Student.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody)
                .hasSize(2)
                .map(Student::getId)
                .containsExactly(student1.getId(), student2.getId());
    }

    @Test
    @DisplayName("Register a new students")
    public void registerNewStudentTest() {
        Student student1 = Student.builder()
                .name("fopa")
                .email("fopa@lao-sarl.cm")
                .dob(LocalDate.of(1999, Month.SEPTEMBER, 3))
                .build();

        webTestClient
                .post()
                .uri("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(student1)
                .exchange()
                .expectStatus().isEqualTo(200);

        verify(studentService).addNewStudent(student1);

    }

    @Test
    @DisplayName("Delete a students")
    public void deleteStudentTest() {
        Long id = 6L;
        webTestClient
                .delete()
                .uri("/api/v1/student/" + id)
                .exchange()
                .expectStatus().isEqualTo(200);

        verify(studentService).deleteStudent(id);

    }

    @Test
    @DisplayName("Update a students")
    public void updateStudentTest() {

        Long id = 5L;
        String name = "fopa";
        String email = "fopa@lao-sarl.cm";

        Student student1 = Student.builder()
                .id(id)
                .name(name)
                .email(email)
                .dob(LocalDate.of(1999, Month.SEPTEMBER, 3))
                .build();

        webTestClient
                .put()
                .uri(
                        uriBuilder -> uriBuilder
                                .pathSegment("api", "v1", "student", student1.getId().toString())
                                .queryParam("name", student1.getName())
                                .queryParam("email", student1.getEmail())
                                .build()
                )
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(200);

        verify(studentService).updateStudent(id, name, email);
    }

}


