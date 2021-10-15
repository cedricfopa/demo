package com.example.demo.student.e2e;

import com.example.demo.student.Student;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentGetAll extends E2eTest {

    @Test
    void execute() {
        Long id1 = Long.MAX_VALUE - 1;
        Long id2 = Long.MAX_VALUE - 2;

        jdbcTemplate.update("DELETE FROM student");
        jdbcTemplate.batchUpdate(
                "INSERT INTO student (id, name, email, dob) VALUES (?, ?, ?, ?)",
                List.of(
                        new Object[]{id1, "cedric1", "cedric1@lao-sarl.cm", LocalDate.now()},
                        new Object[]{id2, "cedric2", "cedric2@lao-sarl.cm", LocalDate.now()}
                )
        );

        final var responseBody = webTestClient
                .get()
                .uri("/api/v1/student")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Student.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody)
                .hasSize(2)
                .map(Student::getId)
                .containsExactly(id1, id2);
    }
}
