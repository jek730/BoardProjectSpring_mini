package org.kje.board.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kje.board.entities.Board;
import org.kje.board.services.BoardDeleteService;
import org.kje.board.services.BoardInfoService;
import org.kje.board.services.BoardSaveService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardSaveService saveService;
    private final BoardInfoService infoService;
    private final BoardDeleteService deleteService;

    @GetMapping("/write")
    public String write(@ModelAttribute RequestBoard form) {

        return "front/board/write";
    }

    @GetMapping("/update/{seq}")
    public String update(@PathVariable("seq") Long seq, Model model) {
        RequestBoard form = infoService.getForm(seq);
        model.addAttribute("requestBoard", form);

        return "front/board/update";
    }

    // 게시글 작성, 수정 처리
    @PostMapping("/save")
    public String save(@Valid RequestBoard form, Errors errors) {
        String mode = form.getSeq() == null ? "update" : "write";

        if(errors.hasErrors()) {
            return "front/board/" + mode;
        }

        saveService.save(form);

        return "redirect:/board/list";
    }

    // 게시글 보기
    @GetMapping("/view/{seq}")
    public String View(@PathVariable("seq") Long seq, Model model) {
        Board board = infoService.get(seq);
        model.addAttribute("data", board);

        return "front/board/view";
    }

    // 게시글 목록 보기
    @GetMapping("list")
    public String list(@ModelAttribute BoardSearch search, Model model) {
        Page<Board> data = infoService.getList(search);

        model.addAttribute("items", data.getContent());
        return "front/board/list";
    }


    // 게시글 삭제
    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq) {

        deleteService.delete(seq);

        return "redirect:/board/list";
    }
}
