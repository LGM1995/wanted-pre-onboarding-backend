package com.task.model.global;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    @Column(name = "create_date", updatable = false) // 생성 날짜
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "update_date") // 업데이트 날짜
    private LocalDateTime updateDate;

}