package org.likelion.likelionassignmentcrud.lecture.domain.repository;

import org.likelion.likelionassignmentcrud.lecture.domain.Lecture;
import org.likelion.likelionassignmentcrud.professor.domain.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {   //pk 타입
    List<Lecture> findByProfessor(Professor professor);     //Professor 객체를 매개변수로 받고 Lecture 객체들의 리스트를 반환하는 findByProfessor 메소드 선언
    Page<Lecture> findAll(Pageable pageable);
}
