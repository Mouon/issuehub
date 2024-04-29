package com.everysource.everysource.dto.board;


import com.everysource.everysource.domain.board.ErrorBoard;
import com.everysource.everysource.domain.board.ErrorComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class EBoardDetailDTO {

    private String name;

    private String content;

    private String password;

    private List<ErrorComment> comments;

    public EBoardDetailDTO(ErrorBoard errorBoard) {
        this.name = errorBoard.getName();
        this.content = errorBoard.getContent();
        this.password = errorBoard.getPassword();
        this.comments = errorBoard.getComment();
    }
}
