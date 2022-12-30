package com.example.jparepository.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private float score;

    @ManyToOne
    private Member member;
    @ManyToOne
    private Book book;
    @OneToMany
    @JoinColumn(name = "review_id")
    private List<Comment> comments = new ArrayList<>();
}
