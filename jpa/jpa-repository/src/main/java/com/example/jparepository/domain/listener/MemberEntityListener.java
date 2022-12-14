package com.example.jparepository.domain.listener;

import com.example.jparepository.domain.Member;
import com.example.jparepository.domain.MemberHistory;
import com.example.jparepository.repository.MemberHistoryRepository;
import com.example.jparepository.support.BeanUtils;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class MemberEntityListener {
    // FIXME: EntityListener 은 spring bean 을 주입받지 못한다.
//    @Autowired
//    private MemberHistoryRepository memberHistoryRepository;
    @PostPersist
    @PostUpdate
    public void prePersistAndUpdate(Object o){
        MemberHistoryRepository memberHistoryRepository = BeanUtils.getBean(MemberHistoryRepository.class);
        Member member = (Member) o;

        MemberHistory memberHistory = MemberHistory.builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
        memberHistory.setCreatedAt(member.getCreatedAt());
        memberHistory.setUpdatedAt(member.getUpdatedAt());
        memberHistoryRepository.save(memberHistory);
    }
}
