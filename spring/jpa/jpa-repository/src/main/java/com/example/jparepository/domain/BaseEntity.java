package com.example.jparepository.domain;

import com.example.jparepository.domain.listener.Auditable;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
@Data
@MappedSuperclass // 해당 클래스의 필드를 상속받는 클래스의 필드로 추가
@EntityListeners(value = AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false,
            columnDefinition = "datetime(6) default now(6) comment '생성시간'")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(nullable = false,
            columnDefinition = "datetime(6) default now(6) comment '수정시간'")
    private LocalDateTime updatedAt;
}
