package com.everysource.everysource.dto.board;

import com.everysource.everysource.domain.board.ErrorBoard;
import com.everysource.everysource.domain.board.ErrorBoardSearch;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class EBoardListDTO {

    private Long id;

    private String name;

    private LocalDateTime createAt;

    public EBoardListDTO(ErrorBoardSearch errorBoard) {
        this.id = errorBoard.getId();
        this.name = errorBoard.getName();
        this.createAt = errorBoard.getCreateAt();
    }
}
