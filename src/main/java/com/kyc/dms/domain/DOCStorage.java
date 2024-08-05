package com.kyc.dms.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doc_storage")
//, schema = "${com.opl.bucket.audit.schema}", catalog = "${com.opl.bucket.audit.catalog}"
public class DOCStorage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "reference_id", unique = true, nullable = false, columnDefinition = "VARCHAR(50) default ''")
	private String referenceId;

	@Column(name = "file_type")
	private String fileType;

	@Column(name = "original_name", length = 1000)
	private String originalName;

	@Column(name = "doc_url")
	private String docUrl;

	@Column(name = "file_size")
	private Long fileSize;

	@Column(name = "message")
	private String message;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdDate;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;

	public DOCStorage(String referenceId, Boolean isActive) {
		super();
		this.referenceId = referenceId;
		this.isActive = isActive;
		this.createdDate = new Date();
	}

	public DOCStorage(String referenceId, String fileType, String originalName, String docUrl, Long fileSize,
			String message, Boolean isActive) {
		super();
		this.referenceId = referenceId;
		this.fileType = fileType;
		this.originalName = originalName;
		this.docUrl = docUrl;
		this.fileSize = fileSize;
		this.message = message;
		this.isActive = isActive;
		this.createdDate = new Date();
		this.modifiedDate = new Date();
	}

}

