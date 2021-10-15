package com.example.demo.student.e2e;

import com.example.demo.student.Student;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentSave extends E2eTest{
    @Test
    void execute(){

        Long id2 = Long.MAX_VALUE - 5;
        String name = "fopa";
        String email = "fopa@lao-sarl.cm";

        Student student1 = Student.builder()
                .id(id2)
                .name(name)
                .email(email)
                .dob(LocalDate.of(1999, Month.SEPTEMBER, 3))
                .build();

        jdbcTemplate.update("DELETE FROM student");
        webTestClient
                .post()
                .uri("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(student1)
                .exchange()
                .expectStatus().isEqualTo(200);

        final var students = jdbcTemplate.queryForList("SELECT * FROM student");

        assertThat(students.get(0).get("name").equals(name));
        assertThat(students.get(0).get("email").equals(email));
        assertThat(students).hasSize(1);

    }
}
