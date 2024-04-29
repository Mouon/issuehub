package com.everysource.everysource.service;

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
    public List<EBoardListDTO> findByKeyword(String keyword){

        List<ErrorBoardSearch> errorBoards =eBoardSearchRepository.findByKeyword(keyword);
        return errorBoards.stream()
                .map(errorBoard -> new EBoardListDTO(errorBoard))
                .collect(Collectors.toList());
    }

    public List<EBoardListDTO> findAllEBoard(){
        List<ErrorBoardSearch> errorBoards =eBoardSearchRepository.findAll();
        return errorBoards.stream()
                .map(errorBoard -> new EBoardListDTO(errorBoard))
                .collect(Collectors.toList());
    }

    @Transactional
    public void eBoardSave(EBoardWriteSearchDTO errorBoard) {

        ErrorBoard errorBoardEntity=new ErrorBoard(errorBoard.getName(),errorBoard.getContent(),errorBoard.getPassword());
        eBoardRepository.save(errorBoardEntity);
        ErrorBoardSearch errorBoardSearch =new ErrorBoardSearch(errorBoardEntity.getId(),errorBoardEntity.getName(),errorBoardEntity.getContent());

        eBoardSearchRepository.save(errorBoardSearch);
    }


    @Transactional
    public EBoardDetailDTO getEBoardDetail(Long id) {
        ErrorBoard board = eBoardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ErrorBoard with id " + id + " not found"));
        Hibernate.initialize(board.getComment());
        return new EBoardDetailDTO(board);
    }


}
