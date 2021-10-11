package com.example.demo.student;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
//construction d'un objet avec des atributs specifique
@Builder
//construction d'un objet avec tout les atributs
@AllArgsConstructor
//construction d'un objet sans atributs
@NoArgsConstructor
//creation de la methode ToString
@ToString
//creation des methodes Getter
@Getter
//creation des methodes Setter
@Setter
@Entity
@Table
public class Student {
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
