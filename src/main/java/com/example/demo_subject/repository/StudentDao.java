package com.example.demo_subject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_subject.entity.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, String>{

}
