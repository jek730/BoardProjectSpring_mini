package org.kje.board.services;

import lombok.RequiredArgsConstructor;
import org.kje.board.repositories.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardDeleteService {

    private final BoardRepository boardRepository;

    public void delete(Long seq) {

        boardRepository.deleteById(seq);

        boardRepository.flush();
    }
}
