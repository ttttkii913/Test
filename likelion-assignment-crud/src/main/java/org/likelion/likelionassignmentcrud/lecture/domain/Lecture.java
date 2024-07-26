package org.likelion.likelionassignmentcrud.lecture.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.likelionassignmentcrud.lecture.api.dto.request.LectureUpdateReqDto;
import org.likelion.likelionassignmentcrud.professor.domain.Professor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키 생성을 db에 위임 = 기본키가 auto increment 필드(id 값에 1부터 일련번호가 자동으로 부여됨)
    @Column(name = "lecture_id")
    private Long lectureId;         //pk

    private String title;           //강의 이름
    private Long grade;             //학점

    //private int professor_id 쓰면 안 됨, joincolumn으로 fk 설정만 해야함
    //강의는 한 명의 교수가 존재해야함 = 주인(lecture (many)) -> joincolumn으로 fk 매핑
    @ManyToOne(fetch = FetchType.LAZY)      //fetchtype: 지연 로딩(프록시로 조회)
    @JoinColumn(name="professor_id")
    private Professor professor;          //교수

    @Builder
    public Lecture(String title, Long grade, Professor professor) {
        this.title = title;
        this.grade = grade;
        this.professor = professor;
    }

    //update 강의 이름, 학점 수정
    public void update(LectureUpdateReqDto lectureUpdateReqDto) {
        this.title = lectureUpdateReqDto.title();
        this.grade = lectureUpdateReqDto.grade();
    }
}
