package com.example.demo_subject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_subject.entity.Subject;

@Repository
public interface SubjectDao extends JpaRepository<Subject, String>{

	public List<Subject> findBySubName(String subName);
}
