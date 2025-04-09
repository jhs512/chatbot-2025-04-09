package com.back.global.jpa.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseTime extends BaseEntity {
    @CreatedDate
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime modifyDate;

    public void setCreateDateNow() {
        this.createDate = LocalDateTime.now();
        this.modifyDate = createDate;
    }
}