package com.example.jparepository.domain;

import com.example.jparepository.domain.listener.MemberEntityListener;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Builder
@ToString(callSuper = true)
@EntityListeners(value = {MemberEntityListener.class})
@EqualsAndHashCode(callSuper = true)
public class Member extends BaseEntity {
    /** @GeneratedValue
     * IDENTITY: MySQL 등 상용 서비스에서 가장 많이 사용됨 (auto increment)
     * SEQUENCE: Oracle, H2 등에서 사용
     * TABLE: DB 종류에 상관없이 ID 값을 관리
     * return DEFAULT == AUTO (DB에 따라 자동으로 맞춰짐)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private List<MemberHistory> memberHistories = new ArrayList<>(); // MEMO: NullPointException 방지
}
