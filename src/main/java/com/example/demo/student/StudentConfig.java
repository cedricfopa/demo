package com.example.demo.student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student cedric = Student.builder()
                    .name("cedric")
                    .email("cedric@gmail.com")
                    .dob(LocalDate.of(1998, Month.SEPTEMBER, 19))
                    .build();
            Student eric = Student.builder()
                    .name("eric")
                    .email("eric@gmail.com")
                    .dob(LocalDate.of(2001, Month.SEPTEMBER, 19))
                    .build();
            Student loic = Student.builder()
                    .name("loic")
                    .email("loic@gmail.com")
                    .dob(LocalDate.of(2016, Month.SEPTEMBER, 19))
                    .build();
            Student herman = Student.builder()
                    .name("herman")
                    .email("herman@gmail.com")
                    .dob(LocalDate.of(1992, Month.SEPTEMBER, 19))
                    .build();
            repository.saveAll(
                    List.of(cedric,eric,herman,loic)
            );

        };
    }
}
