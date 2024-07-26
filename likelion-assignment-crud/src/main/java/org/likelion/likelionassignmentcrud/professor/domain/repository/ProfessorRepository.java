package org.likelion.likelionassignmentcrud.professor.domain.repository;

import org.likelion.likelionassignmentcrud.professor.domain.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//mybatis mapper에 해당하는 interface
public interface ProfessorRepository extends JpaRepository<Professor, Long> {  //pk의 타입(@id 붙은 변수의 타입)
    Page<Professor> findAll(Pageable pageable); // 전체 목록 조회에서 pagination 설정
}
