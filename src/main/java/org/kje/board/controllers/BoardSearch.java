package org.kje.board.controllers;

import lombok.Data;

@Data
public class BoardSearch {
    private int page = 1;
    private int limit = 20;

    private String sopt;
    private String skey;

}
