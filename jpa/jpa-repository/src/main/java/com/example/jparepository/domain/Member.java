package com.example.jparepository.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Builder
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
