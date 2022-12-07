package com.example.jpah2.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class User {
    @NonNull
    private String name;
    @NonNull
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
