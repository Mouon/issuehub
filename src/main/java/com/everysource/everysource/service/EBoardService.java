package com.everysource.everysource.service;

import com.everysource.everysource.domain.api.Issue;
import com.everysource.everysource.domain.api.IssueSearch;
import com.everysource.everysource.domain.board.ErrorBoard;
import com.everysource.everysource.domain.board.ErrorBoardSearch;
import com.everysource.everysource.dto.board.EBoardDetailDTO;
import com.everysource.everysource.dto.board.EBoardListDTO;
import com.everysource.everysource.dto.board.EBoardWriteSearchDTO;
import com.everysource.everysource.repository.board.EBoardRepository;
import com.everysource.everysource.repository.board.EBoardSearchRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EBoardService {

    private final EBoardSearchRepository eBoardSearchRepository;
    private final EBoardRepository eBoardRepository;

    /** 에러허브 검색 기능 서비스 */
    public List<EBoardListDTO> findByKeyword(String keyword){
        List<ErrorBoardSearch> errorBoards =eBoardSearchRepository.findByKeyword(keyword);
        return errorBoards.stream()
                .map(errorBoard -> new EBoardListDTO(errorBoard))
                .collect(Collectors.toList());
    }

    /** 에러허브 최초 전체 로드
     * JPA 아니고 엘라스틱 서치로 로드 */
    public List<EBoardListDTO> findAllEBoard(){
        List<ErrorBoardSearch> errorBoards =eBoardSearchRepository.findAll();
        return errorBoards.stream()
                .map(errorBoard -> new EBoardListDTO(errorBoard))
                .collect(Collectors.toList());
    }

    /** 에러허브 게시물 작성
     * JPA를 통해 RDS에 저장과 동시에 엘라스틱서치에도 저장
     * id 자동 부여등 데이터 무결성위해 JPA통해 생긴 객체를 엘라스틱 서치 객체에 매핑시킴*/
    @Transactional
    public void eBoardSave(EBoardWriteSearchDTO errorBoard) {
        ErrorBoard errorBoardEntity=new ErrorBoard(errorBoard.getName(),errorBoard.getContent(),errorBoard.getPassword());
        eBoardRepository.save(errorBoardEntity);
        ErrorBoardSearch errorBoardSearch = convertToErrorBoardSearch(errorBoardEntity);
        eBoardSearchRepository.save(errorBoardSearch);
    }


    @Transactional
    public EBoardDetailDTO getEBoardDetail(Long id) {
        ErrorBoard board = eBoardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ErrorBoard with id " + id + " not found"));
        Hibernate.initialize(board.getComment());
        return new EBoardDetailDTO(board);
    }

    private ErrorBoardSearch convertToErrorBoardSearch(ErrorBoard errorBoard) {
        return ErrorBoardSearch.builder()
                .id(errorBoard.getId())
                .name(errorBoard.getName())
                .content(errorBoard.getContent())
                .build();
    }


}
