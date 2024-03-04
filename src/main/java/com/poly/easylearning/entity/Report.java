package com.poly.easylearning.entity;

import com.poly.easylearning.enums.ReportStatus;
import com.poly.easylearning.enums.ReportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name="`report`")
@Entity
public class Report extends BaseEntity{
	private LocalDateTime timeReport;

	@Column(columnDefinition = "TEXT")
	private String reason;

	@Enumerated(EnumType.STRING)
	private ReportStatus status;

	@Enumerated(EnumType.STRING)
	private ReportType type;

	private UUID targetId;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
}
