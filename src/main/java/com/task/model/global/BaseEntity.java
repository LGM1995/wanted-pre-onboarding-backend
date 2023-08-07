package com.task.model.global;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 스프링 시큐리티와 메인 클래스에서 @EnableJpaAuditing를 선언하는 경우 트랜잭션 커밋 시점에서 다양한 기능을 적용 시킬수 있다.
public class BaseEntity {

    @CreatedDate
    @Column(name = "create_date", updatable = false) // 생성 날짜
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "update_date") // 업데이트 날짜
    private LocalDateTime updateDate;

}