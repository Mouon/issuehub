package com.everysource.everysource.controller;

import com.everysource.everysource.domain.board.ErrorBoardSearch;
import com.everysource.everysource.dto.board.EBoardDetailDTO;
import com.everysource.everysource.dto.board.EBoardListDTO;
import com.everysource.everysource.dto.board.EBoardWriteSearchDTO;
import com.everysource.everysource.service.EBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/error_board")
public class EBoardController {

    private final EBoardService eBoardService;


    @GetMapping("/search")
    public List<EBoardListDTO> getEBoardByKeyword(@RequestParam String keyword){
        return eBoardService.findByKeyword(keyword);
    }

    @GetMapping("/all")
    public List<EBoardListDTO> getAllEBoard(){
        return eBoardService.findAllEBoard();
    }

    @GetMapping("/detail")
    public EBoardDetailDTO getEBoardDetail(@RequestParam Long id){
        return eBoardService.getEBoardDetail(id);
    }

    @PostMapping("/write")
    public ResponseEntity<?> createBoard(@RequestBody EBoardWriteSearchDTO eBoardSave) {
        eBoardService.eBoardSave(eBoardSave);
        return ResponseEntity.ok("{\"status\":\"success\"}");
    }

}
