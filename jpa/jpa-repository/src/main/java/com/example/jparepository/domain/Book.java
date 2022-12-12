package com.example.jparepository.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@EntityListeners(value = MyEntityListener.class)
public class Book implements Auditable{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    /*
    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

     */
}
