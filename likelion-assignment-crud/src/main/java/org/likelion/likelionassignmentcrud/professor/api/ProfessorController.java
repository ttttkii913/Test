package org.likelion.likelionassignmentcrud.professor.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.likelion.likelionassignmentcrud.common.dto.BaseResponse;
import org.likelion.likelionassignmentcrud.common.error.SuccessCode;
import org.likelion.likelionassignmentcrud.professor.api.dto.request.ProfessorSaveReqDto;
import org.likelion.likelionassignmentcrud.professor.api.dto.request.ProfessorUpdateReqDto;
import org.likelion.likelionassignmentcrud.professor.api.dto.response.ProfessorInfoResDto;
import org.likelion.likelionassignmentcrud.professor.api.dto.response.ProfessorListResDto;
import org.likelion.likelionassignmentcrud.professor.application.ProfessorService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    // 새 교수 등록
    @Operation(summary = "로그인한 사람이 작성한 리뷰 리스트 조회", description = "로그인한 사람이 작성한 리뷰 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping   // requestBody <= 서버에 전달하려는 데이터
    @ResponseStatus(HttpStatus.CREATED)     // 성공 시 HTTP 201 Created 상태 코드 반환
    public BaseResponse<ProfessorInfoResDto> professorSave(@RequestBody @Valid ProfessorSaveReqDto professorSaveReqDto) {
        ProfessorInfoResDto professorInfoResDto = professorService.professorSave(professorSaveReqDto); // professorservice의 professorsave 메서드 호출해서 교수 저장
        return BaseResponse.success(SuccessCode.PROFESSOR_SAVE_SUCCESS, professorInfoResDto); // 성공 응답 반환(SuccessCode)
    }

    // 모든 교수 조회(request body 필요 x = 전달할 데이터가 없기에
    @Operation(summary = "로그인한 사람이 작성한 리뷰 리스트 조회", description = "로그인한 사람이 작성한 리뷰 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ProfessorListResDto> professorFindAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "professorId,asc") String sort
    ) {
        Pageable pageable;
        if (sort.isEmpty()) {   // 교수 Id 오름차순으로 정렬
            pageable = PageRequest.of(page, size, Sort.by("professorId").ascending());
        } else {
            String[] sortParams = sort.split(",");  // 쉼표 기준으로 정렬
            Sort sortOrder = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]); // 정렬 방향, 정렬 기준 추출
            pageable = PageRequest.of(page, size, sortOrder);
        }
        ProfessorListResDto professorListResDto = professorService.professorFindAll(pageable);  // professorservice의 findall 메소드 호출해서 전체 교수(리스트) 조회
        return BaseResponse.success(SuccessCode.GET_SUCCESS, professorListResDto);  //성공 응답 반환(SuccessCode)
    }

    // 교수 id에 따라 교수 한 명 조회
    @Operation(summary = "로그인한 사람이 작성한 리뷰 리스트 조회", description = "로그인한 사람이 작성한 리뷰 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/{professorId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ProfessorInfoResDto> professorFindOne(@PathVariable("professorId") Long professorId) {
        ProfessorInfoResDto professorInfoResDto = professorService.professorFindOne(professorId);  // professorservice의 findone 메소드 호출해서 특정 교수 조회
        return BaseResponse.success(SuccessCode.GET_SUCCESS, professorInfoResDto); // 성공 응답 반환(SuccessCode)
    }

    // update
    @Operation(summary = "로그인한 사람이 작성한 리뷰 리스트 조회", description = "로그인한 사람이 작성한 리뷰 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PatchMapping("/{professorId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ProfessorInfoResDto> professorUpdate(@PathVariable("professorId") Long professorId,
                                                  @RequestBody @Valid ProfessorUpdateReqDto professorUpdateReqDto) {
        ProfessorInfoResDto professorInfoResDto = professorService.professorUpdate(professorId, professorUpdateReqDto);
        return BaseResponse.success(SuccessCode.PROFESSOR_UPDATE_SUCCESS, professorInfoResDto); // 성공 응답 반환(SuccessCode)
    }

    // delete
    @Operation(summary = "로그인한 사람이 작성한 리뷰 리스트 조회", description = "로그인한 사람이 작성한 리뷰 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @DeleteMapping("{professorId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ProfessorInfoResDto> professorDelete(@PathVariable("professorId") Long professorId) {
        ProfessorInfoResDto professorInfoResDto = professorService.professorDelete(professorId);
        return BaseResponse.success(SuccessCode.PROFESSOR_DELETE_SUCCESS, professorInfoResDto); // 성공 응답 반환(SuccessCode)
    }
}
