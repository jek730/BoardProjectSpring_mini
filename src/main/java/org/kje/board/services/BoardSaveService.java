package org.kje.board.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kje.board.controllers.RequestBoard;
import org.kje.board.entities.Board;
import org.kje.board.repositories.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardSaveService {

    private final BoardRepository boardRepository;

    public void save(RequestBoard form) {  // 게시글 번호가 없으면 추가, 있으면 수정
        Long seq = Objects.requireNonNullElse(form.getSeq(), 0L);
        Board board = boardRepository.findById(seq).orElseGet(Board::new);

        board.setSubject(form.getSubject());
        board.setContent(form.getContent());
        board.setPoster(form.getPoster());

        boardRepository.saveAndFlush(board);

    }
}
