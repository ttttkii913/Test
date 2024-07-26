package org.likelion.likelionassignmentcrud.professor.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.likelionassignmentcrud.lecture.domain.Lecture;
import org.likelion.likelionassignmentcrud.professor.api.dto.request.ProfessorUpdateReqDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor {
    @Id         // PK 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 기본 키 생성을 db에 위임 = 기본키가 auto increment 필드(id 값에 1부터 일련번호가 자동으로 부여됨)
    @Column(name = "professor_id")     // 매핑할 컬럼 이름 지정(조회 결과 칼럼명
    private Long professorId;          // pk

    private String name;            // 교수 이름
    private int age;                // 교수 나이
    private String email;           // 교수 이메일 추가
    private String office;          // 교수 연구실 추가

    @Enumerated(EnumType.STRING)    // enum 이름(SOFTWARE, COMPUTER_SCIENCE) -> "SOFTWARE", "COMPUTER_SCIENCE"로 문자열(type이 String) 자체를 DB에 저장
    private Department department;              // 교수 전공 파트(Department 객체로)

    // 사용자가 여러 개의 게시물을 가짐 = 일대다
    // ex) 일: department 다: student (student쪽이 주인 = @joincolumn 사용)
    //      주인이 아닌 엔티티 클래스에서는 mappedBy <= 일(department, member)
    //     일: member     다: post    (post쪽이 주인 = @joincolumn 사용)
    //     일: professor  다: lecture
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lecture> lectures = new ArrayList<>();

    @Builder
    public Professor(String name, int age, String email, Department department, String office) {
        this.name = name;
        this.age = age;
        this.email = email; // email 추가
        this.department = department;
        this.office = office;   // office 추가
    }

    // update 이름, 학과, 이메일
    public void update(ProfessorUpdateReqDto professorUpdateReqDto) {
        this.name = professorUpdateReqDto.name();
        this.department = professorUpdateReqDto.department();
        this.email = professorUpdateReqDto.email();
    }
}
