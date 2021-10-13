package com.example.demo.student;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table
public class Student {
    @EqualsAndHashCode.Include
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    private String name;
    private String email;
    private LocalDate dob;
    @Transient
    private Integer age;

    public Integer getAge() {
        return Period.between(this.dob,LocalDate.now()).getDays();
    }


}
