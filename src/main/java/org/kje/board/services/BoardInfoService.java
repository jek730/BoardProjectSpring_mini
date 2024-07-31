package org.kje.board.services;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.kje.board.controllers.BoardSearch;
import org.kje.board.controllers.RequestBoard;
import org.kje.board.entities.Board;
import org.kje.board.entities.QBoard;
import org.kje.board.exceptions.BoardNotFoundExeption;
import org.kje.board.repositories.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class BoardInfoService {

    private final BoardRepository boardRepository;

    public Board get(Long seq) {
        Board board = boardRepository.findById(seq).orElseThrow(BoardNotFoundExeption::new);

        // 2차 데이터 가공 처리

        return board;
    }

    public RequestBoard getForm(Long seq) {
        Board board = get(seq);
        RequestBoard form = new ModelMapper().map(board, RequestBoard.class);

        // 2차 처리

        return form;
    }

    public Page<Board> getList(BoardSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit() < 1 ? 20 : search.getLimit();
        String sopt = search.getSopt();
        String skey = search.getSkey();

        sopt = StringUtils.hasText(sopt) ? sopt : "ALL";

        QBoard board = QBoard.board;

        BooleanBuilder andBuilder = new BooleanBuilder();
        if (StringUtils.hasText(skey)) {
            skey = skey.trim();
            if (sopt.equals("ALL")) {
                andBuilder.and(board.poster.concat(board.content.concat(board.subject)).contains(skey));
            } else if (sopt.equals("SUBJECT")) {
                andBuilder.and(board.subject.contains(skey));
            }
        }

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));


        Page<Board> data = boardRepository.findAll(andBuilder, pageable);


        return data;

    }
}
