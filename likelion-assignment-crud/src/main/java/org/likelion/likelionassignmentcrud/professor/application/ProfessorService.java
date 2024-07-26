package org.likelion.likelionassignmentcrud.professor.application;

import org.likelion.likelionassignmentcrud.common.error.ErrorCode;
import org.likelion.likelionassignmentcrud.common.exception.NotFoundException;
import org.likelion.likelionassignmentcrud.professor.api.dto.request.ProfessorSaveReqDto;
import org.likelion.likelionassignmentcrud.professor.api.dto.request.ProfessorUpdateReqDto;
import org.likelion.likelionassignmentcrud.professor.api.dto.response.ProfessorInfoResDto;
import org.likelion.likelionassignmentcrud.professor.api.dto.response.ProfessorListResDto;
import org.likelion.likelionassignmentcrud.professor.domain.Professor;
import org.likelion.likelionassignmentcrud.professor.domain.repository.ProfessorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service    // controller와 repository를 잇는 역할, 구체적인 작업 수행
@Transactional(readOnly = true)     // 예외가 발생하면 해당 메서드를 실행하면서 수행한 쿼리들을 모두 롤백 // 트랜잭션을 읽기전용으로 설정 insert, update, delete 실행할 때 예외 발생
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    // create
    @Transactional  // void -> ProfessorInfoResDto: 리턴 값이 없는 void 메소드는 단순히 저장하고 끝, 리턴 값 지정을 통해 교수 정보를 저장하고 즉시 사용할 수 있도록 리턴해줌
    public ProfessorInfoResDto professorSave(ProfessorSaveReqDto professorSaveReqDto) {     // 교수 정보 저장 메서드 professorsave
        Professor professor = Professor.builder()       // 이름, 나이, 이메일, 학과, 연구실 정보를 받아 professor 객체 생성
                .name(professorSaveReqDto.name())
                .age(professorSaveReqDto.age())
                .email(professorSaveReqDto.email())
                .department(professorSaveReqDto.department())
                .office(professorSaveReqDto.office())
                .build();

        professorRepository.save(professor); // 생성된 professor 객체를 저장
        return ProfessorInfoResDto.from(professor); // 저장된 교수 정보를 ProfessorInfoResDto로 변환하여 리턴
    }

    // Read All (모든 교수 조회 + pagination)
    public ProfessorListResDto professorFindAll(Pageable pageable) {
        Page<Professor> professors = professorRepository.findAll(pageable); // findall 해서 Page<Professors>에 저장

        List<ProfessorInfoResDto> professorInfoResDtoList = professors.stream()  // 교수 정보를 professorInfoResDto로 변환하고 리스트에 저장
                .map(ProfessorInfoResDto::from)    // 메소드 참조 형식, professor를 professorInfoResDto로 매핑
                .collect(Collectors.toList());     // 변환된 ProfessorInfoResDto를 리스트에 추가

        return ProfessorListResDto.from(professorInfoResDtoList);
        // 변환된 교수 정보를 ProfessorListResDto로 감싸서 리턴
    }

    // Read One
    public ProfessorInfoResDto professorFindOne(Long professorId) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(
                () -> new NotFoundException(ErrorCode.PROFESSORS_NOT_FOUND_EXCEPTION, ErrorCode.PROFESSORS_NOT_FOUND_EXCEPTION.getMessage()
                + professorId)
        );

        return ProfessorInfoResDto.from(professor);
    }

    // 교수 이름, 학과 update
    @Transactional
    public ProfessorInfoResDto professorUpdate(Long professorId, ProfessorUpdateReqDto professorUpdateReqDto) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(
                () -> new NotFoundException(ErrorCode.PROFESSORS_NOT_FOUND_EXCEPTION, ErrorCode.PROFESSORS_NOT_FOUND_EXCEPTION.getMessage()
                + professorId)
        );

        professor.update(professorUpdateReqDto);    // 조회된 professor 객체 수정
        return ProfessorInfoResDto.from(professor); // 수정된 교수 정보를 ProfessorInfoResDto로 변환하여 리턴
    }

    // 교수 Delete
    @Transactional
    public ProfessorInfoResDto professorDelete(Long professorId) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(    // 교수 id로 조회
                () -> new NotFoundException(ErrorCode.PROFESSORS_NOT_FOUND_EXCEPTION, ErrorCode.PROFESSORS_NOT_FOUND_EXCEPTION.getMessage()
                + professorId)
        );

        professorRepository.delete(professor);      // 조회된 professor 객체를 삭제
        return ProfessorInfoResDto.from(professor); // 삭제된 교수 정보를 ProfessorInfoResDto로 변환하여 리턴
    }

    /** 교수를 찾지 못하면 예외 발생
     * new NotFoundException(ErrorCode.PROFESSORS_NOT_FOUND_EXCEPTION,
     * ErrorCode.PROFESSORS_NOT_FOUND_EXCEPTION.getMessage() + professorId)
     * NotFoundException 생성자에 ErrorCode와 교수 id를 포함한 에러 메시지를 전달
     */

}
