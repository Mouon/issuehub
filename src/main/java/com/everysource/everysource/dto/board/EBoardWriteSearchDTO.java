package com.everysource.everysource.dto.board;

import com.everysource.everysource.domain.board.ErrorBoardSearch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter @Setter
@NoArgsConstructor
public class EBoardWriteSearchDTO {

    private Long id;

    private String name;

    private String content;

    private String password;

    public EBoardWriteSearchDTO(ErrorBoardSearch errorBoard) {
        this.id=errorBoard.getId();
        this.name = errorBoard.getName();
        this.content=errorBoard.getContent();
    }

}
