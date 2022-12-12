package com.example.jparepository.domain;

import lombok.*;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@EntityListeners(value = MyEntityListener.class)
@Builder
//@Table(name = "user", uniqueConstraints ={@UniqueConstraint(columnNames = {"email"})},
//indexes = {@Index(columnList = "name")})
public class Member implements Auditable{
    /** @GeneratedValue
     * IDENTITY: MySQL 등 상용 서비스에서 가장 많이 사용됨 (auto increment)
     * SEQUENCE: Oracle, H2 등에서 사용
     * TABLE: DB 종류에 상관없이 ID 값을 관리
     * return DEFAULT == AUTO (DB에 따라 자동으로 맞춰짐)
     */
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Transient
    private String testData;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Address> address;

    /*
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

     */

    public Member(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
