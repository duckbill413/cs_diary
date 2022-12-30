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
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "home_city")),
            @AttributeOverride(name = "district", column = @Column(name = "home_district")),
            @AttributeOverride(name = "detail", column = @Column(name = "home_address_detail")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "home_zip_code"))
    })
    private Address homeAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "company_city")),
            @AttributeOverride(name = "district", column = @Column(name = "company_district")),
            @AttributeOverride(name = "detail", column = @Column(name = "company_address_detail")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "company_zip_code"))
    })
    private Address companyAddress;
    @OneToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @Builder.Default
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private List<MemberHistory> memberHistories = new ArrayList<>(); // MEMO: NullPointException 방지

    @OneToMany
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
}
