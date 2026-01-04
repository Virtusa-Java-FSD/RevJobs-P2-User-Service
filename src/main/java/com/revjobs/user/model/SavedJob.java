package com.revjobs.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "saved_jobs", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "job_id" }), indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_job_id", columnList = "job_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavedJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "saved_date", nullable = false)
    private LocalDateTime savedDate;

    @PrePersist
    protected void onCreate() {
        savedDate = LocalDateTime.now();
    }
}
