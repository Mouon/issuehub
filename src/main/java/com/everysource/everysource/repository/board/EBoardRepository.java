package com.everysource.everysource.repository.board;

import com.everysource.everysource.domain.board.ErrorBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EBoardRepository extends JpaRepository<ErrorBoard, Long> {
    @Query("SELECT eb FROM ErrorBoard eb LEFT JOIN FETCH eb.comment c WHERE eb.id = :id")
    Optional<ErrorBoard> findById(Long id);


}
