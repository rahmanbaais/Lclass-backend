package com.lclass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lclass.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	Optional<Student> findByStudentId(String studentId);

}
