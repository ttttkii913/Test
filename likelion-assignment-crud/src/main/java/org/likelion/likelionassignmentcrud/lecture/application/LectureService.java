package org.likelion.likelionassignmentcrud.lecture.application;

import org.likelion.likelionassignmentcrud.common.error.ErrorCode;
import org.likelion.likelionassignmentcrud.common.exception.NotFoundException;
import org.likelion.likelionassignmentcrud.lecture.api.dto.request.LectureSaveReqDto;
import org.likelion.likelionassignmentcrud.lecture.api.dto.request.LectureUpdateReqDto;
import org.likelion.likelionassignmentcrud.lecture.api.dto.response.LectureInfoResDto;
import org.likelion.likelionassignmentcrud.lecture.api.dto.response.LectureListResDto;
import org.likelion.likelionassignmentcrud.lecture.domain.Lecture;
import org.likelion.likelionassignmentcrud.lecture.domain.repository.LectureRepository;
import org.likelion.likelionassignmentcrud.professor.domain.Professor;
import org.likelion.likelionassignmentcrud.professor.domain.repository.ProfessorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LectureService {
    private final ProfessorRepository professorRepository;
    private final LectureRepository lectureRepository;

    public LectureService(ProfessorRepository professorRepository, LectureRepository lectureRepository) {
        this.professorRepository = professorRepository;
        this.lectureRepository = lectureRepository;
    }

    // Create
    @Transactional
    public LectureInfoResDto lectureSave(LectureSaveReqDto lectureSaveReqDto) {
        Professor professor = professorRepository.findById(lectureSaveReqDto.professorId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.PROFESSORS_NOT_FOUND_EXCEPTION
                        , ErrorCode.PROFESSORS_NOT_FOUND_EXCEPTION.getMessage()
                        + lectureSaveReqDto.professorId())
        );

        Lecture lecture = Lecture.builder()
                .title(lectureSaveReqDto.title())
                .grade(lectureSaveReqDto.grade())
                .professor(professor)
                .build();

        lectureRepository.save(lecture);
        return LectureInfoResDto.from(lecture);
    }

    // Read All
    public LectureListResDto lectureFindAll(Pageable pageable) {
        Page<Lecture> lectures = lectureRepository.findAll(pageable);

        List<LectureInfoResDto> lectureInfoResDtoList = lectures.stream()
                .map(LectureInfoResDto::from)
                .toList();

        return LectureListResDto.from(lectureInfoResDtoList);
    }

    // Read(교수에 따라 강의 전체 조회)
    public LectureListResDto lectureFindProfessor(Long professorId) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(
                () -> new NotFoundException(ErrorCode.PROFESSORS_NOT_FOUND_EXCEPTION
                        , ErrorCode.PROFESSORS_NOT_FOUND_EXCEPTION.getMessage()
                + professorId)
        );

        List<Lecture> lectures = lectureRepository.findByProfessor(professor);
        List<LectureInfoResDto> lectureInfoResDtoList = lectures.stream()
                .map(LectureInfoResDto::from)
                .toList();

        return LectureListResDto.from(lectureInfoResDtoList);
    }

    // Read one(강의 Id에 따라 강의 한 개 조회)
    public LectureInfoResDto lectureFindById(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
                () -> new NotFoundException(ErrorCode.LECTURES_NOT_FOUND_EXCEPTION
                        , ErrorCode.LECTURES_NOT_FOUND_EXCEPTION.getMessage()
                        + lectureId)
        );

        return LectureInfoResDto.from(lecture);
    }

    // 강의(제목, 학점) Update
    @Transactional
    public LectureInfoResDto lectureUpdate(Long lectureId, LectureUpdateReqDto lectureUpdateReqDto) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
                () -> new NotFoundException(ErrorCode.LECTURES_NOT_FOUND_EXCEPTION
                        , ErrorCode.LECTURES_NOT_FOUND_EXCEPTION.getMessage()
                        + lectureId)
        );

        lecture.update(lectureUpdateReqDto);
        return LectureInfoResDto.from(lecture);
    }

    // 강의 Delete
    @Transactional
    public LectureInfoResDto lectureDelete(Long lectureId) {
        Lecture lecture = findLectureById(lectureId);
        lectureRepository.delete(lecture);
        return LectureInfoResDto.from(lecture);
    }

    private Lecture findLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow(
                () -> new NotFoundException(ErrorCode.LECTURES_NOT_FOUND_EXCEPTION
                        , ErrorCode.LECTURES_NOT_FOUND_EXCEPTION.getMessage()));
    }
}