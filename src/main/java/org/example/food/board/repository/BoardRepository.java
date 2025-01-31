package org.example.food.board.repository;

import org.example.food.board.entity.Category;
import org.example.food.board.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByCategoryIn(List<Category> categories);
    Slice<Board> findSliceBy(final Pageable pageable);
}
