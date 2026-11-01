package com.example.student_management;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    // Add student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return repository.save(student);
    }

    // View all students
    @GetMapping
    public List<Student> getStudents() {
        return repository.findAll();
    }

    // Search student
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Optional<Student> student = repository.findById(id);

        if (student.isPresent())
            return ResponseEntity.ok(student.get());

        return ResponseEntity.notFound().build();
    }

    // Update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student newStudent) {

        Optional<Student> result = repository.findById(id);

        if (result.isEmpty())
            return ResponseEntity.notFound().build();

        Student student = result.get();
        student.setName(newStudent.getName());
        student.setAge(newStudent.getAge());
        student.setCourse(newStudent.getCourse());

        return ResponseEntity.ok(repository.save(student));
    }

    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        if (!repository.existsById(id))
            return ResponseEntity.notFound().build();

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}