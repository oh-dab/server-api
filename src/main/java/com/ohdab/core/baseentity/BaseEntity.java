package com.ohdab.core.baseentity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@MappedSuperclass
public class BaseEntity {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersistFunction() {
        LocalDateTime now = getLocalDateTime();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    private void preUpdateFunction() {
        updatedAt = getLocalDateTime();
    }

    private LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }
}
