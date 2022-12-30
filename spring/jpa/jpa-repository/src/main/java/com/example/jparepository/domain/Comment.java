package com.example.jparepository.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
//@DynamicInsert
public class Comment extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String comment;
    @ManyToOne
    @ToString.Exclude
    private Review review;
    @Column(columnDefinition = "datetime")
    private LocalDateTime commentedAt;
}
