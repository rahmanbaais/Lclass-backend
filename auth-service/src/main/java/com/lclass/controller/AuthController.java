
package com.lclass.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lclass.model.AuthRequest;
import com.lclass.model.AuthResponse;
import com.lclass.model.ChangePasswordRequest;
import com.lclass.model.Student;
import com.lclass.repository.StudentRepository;
import com.lclass.security.JwtUtil;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins="*")
public class AuthController {
	
	private final JwtUtil jwtUtil;
	private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
	
    public AuthController(JwtUtil jwtUtil, StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest loginRequest){
		String studentId = loginRequest.getStudentId();
        String password = loginRequest.getPassword();

        // 1. Look up the user in the database
        Optional<Student> studentOpt = studentRepository.findByStudentId(studentId);
        if (studentOpt.isPresent() && passwordEncoder.matches(password, studentOpt.get().getPassword())) {
            Student student = studentOpt.get();
            String token = jwtUtil.generateToken(student.getStudentId(), student.getRoles());
            return ResponseEntity.ok(new AuthResponse(token));
        }
        
        return ResponseEntity.status(401).body("Invalid school portal credentials");
    }
	
	@PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        // 1. Find the student in the database
        Optional<Student> studentOpt = studentRepository.findByStudentId(request.getStudentId());
        
        if (studentOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Student not found");
        }
        
        Student student = studentOpt.get();
        
        // 2. Verify if the typed "old password" matches the hashed password in the DB
        if (!passwordEncoder.matches(request.getOldPassword(), student.getPassword())) {
            return ResponseEntity.status(400).body("Incorrect old password");
        }
        
        // 3. Hash the new password and update the student record
        String hashedNewPassword = passwordEncoder.encode(request.getNewPassword());
        student.setPassword(hashedNewPassword);
        
        // 4. Save the updated student back to the database
        studentRepository.save(student);
        
        return ResponseEntity.ok("Password updated successfully");
    }
}
