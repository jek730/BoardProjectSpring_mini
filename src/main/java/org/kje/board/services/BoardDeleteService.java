package org.kje.board.services;

import lombok.RequiredArgsConstructor;
import org.kje.board.entities.Board;
import org.kje.board.exceptions.BoardNotFoundExeption;
import org.kje.board.repositories.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardDeleteService {

    private final BoardRepository boardRepository;

    public Board delete(Long seq) {
        Board board = boardRepository.findById(seq).orElseThrow(BoardNotFoundExeption::new);
        boardRepository.delete(board);
        boardRepository.flush();

        return board;
    }
}
