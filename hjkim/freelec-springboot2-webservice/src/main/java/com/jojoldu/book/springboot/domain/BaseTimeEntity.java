package com.jojoldu.book.springboot.domain;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   // JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들도 컬럼으로 인식하도록 함
@EntityListeners(AuditingEntityListener.class)  // Auditing 기능 포함
public abstract class BaseTimeEntity {

    @CreatedDate    // Entity 생성되어 저장 시, 시간이 자동 저장됨
    private LocalDateTime createdDate;

    @LastModifiedDate   // Entity 조회 후 변경 시, 시간이 자동 저장됨
    private LocalDateTime modifiedDate;
}
