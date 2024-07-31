package org.kje.board.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.kje.global.entities.BaseEntity;

@Data
@Entity
public class Board extends BaseEntity {
    @Id @GeneratedValue
    private Long seq;

    @Column(length=40, nullable = false)
    private String poster;

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;
}
