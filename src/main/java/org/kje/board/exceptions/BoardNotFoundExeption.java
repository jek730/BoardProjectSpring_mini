package org.kje.board.exceptions;

public class BoardNotFoundExeption extends RuntimeException {
    public BoardNotFoundExeption() {
        super("게시글을 찾을 수 없습니다.");
    }
}
