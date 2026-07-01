package com.lclass;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lclass.model.Student;
import com.lclass.repository.StudentRepository;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if our test student already exists so we don't duplicate him on every restart
            if (studentRepository.findByStudentId("S12345").isEmpty()) {
                
                // 1. Securely hash the password 'password123'
                String hashedPwd = passwordEncoder.encode("password123");
                
                // 2. Create a dummy student instance (id is null because MySQL auto-generates it)
                Student dummyStudent = new Student(
                    null, 
                    "S12345", 
                    hashedPwd, 
                    List.of("ROLE_STUDENT")
                );
                
                // 3. Save to MySQL
                studentRepository.save(dummyStudent);
                System.out.println(">>> LMS Test Student Inserted Successfully!");
            }
        };
    }
}