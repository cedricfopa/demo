package com.example.demo.student;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DisplayName("CRUD test")
    public void crudTest() {
        Student fopa = Student.builder()
                .name("fopa")
                .email("fopa@lao-sarl.cm")
                .dob(LocalDate.of(1999, Month.SEPTEMBER, 3))
                .build();
        Student fopa1 = Student.builder()
                .name("fopa1")
                .email("fopa1@lao-sarl.cm")
                .dob(LocalDate.of(1999, Month.SEPTEMBER, 3))
                .build();
        Student fopa2 = Student.builder()
                .name("fopa2")
                .email("fopa2@lao-sarl.cm")
                .dob(LocalDate.of(1999, Month.NOVEMBER, 3))
                .build();
        Student fopa3 = Student.builder()
                .name("fopa3")
                .email("fopa3@lao-sarl.cm")
                .dob(LocalDate.of(1999, Month.SEPTEMBER, 3))
                .build();
        Student fopa5 = Student.builder()
                .id(19L)
                .name("fopa5")
                .email("fopa5@lao-sarl.cm")
                .dob(LocalDate.of(1999, Month.SEPTEMBER, 3))
                .build();
        final var newStudentIds = studentRepository
                .saveAllAndFlush(List.of(fopa, fopa1, fopa2, fopa3))
                .stream()
                .map(Student::getId)
                .collect(Collectors.toList());

        assertThat(newStudentIds)
                .doesNotContainNull()
                .hasSize(4);

        final var allById = studentRepository.findAllById(newStudentIds);
        assertThat(allById)
                .hasSize(4)
                .contains(fopa, fopa1, fopa2, fopa3);


        assertThat(studentRepository.findById(fopa2.getId()))
                .isEqualTo(Optional.of(fopa2));

        assertThat(studentRepository.findStudentByEmail("fopa1@lao-sarl.cm"))
                .isEqualTo(Optional.of(fopa1));

        assertThat(studentRepository.findStudentByEmail("fopa3@lao-sarl.cm"))
                .isNotEqualTo(Optional.of(fopa2));

        assertThat(studentRepository.existsById(fopa2.getId()))
                .isTrue();
        assertThat(studentRepository.existsById(fopa5.getId()))
                .isFalse();

        studentRepository.deleteById(fopa2.getId());
        assertThat(studentRepository.existsById(fopa2.getId()))
                .isFalse();
        assertThat(studentRepository.findAll())
                .hasSize(3)
                .doesNotContain(fopa2)
                .doesNotContain(fopa5)
                .contains(fopa, fopa1, fopa3);

    }
}
