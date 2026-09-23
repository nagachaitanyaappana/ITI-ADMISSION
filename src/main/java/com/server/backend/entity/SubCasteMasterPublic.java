package com.server.backend.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Sub-caste reference built from this database (see migration notes). The
 * numeric ids are preserved exactly as stored in
 * student_application.sub_caste; sub_caste_name mirrors the stored value
 * ("" where only the id is known) and caste_code is the dominant caste
 * for that id, used to filter the dropdown per selected caste.
 */
@Entity
@Table(name = "subcaste_master", schema = "public")
@Data
public class SubCasteMasterPublic {
    @Id
    @Column(name = "subcaste_id")
    private Long subCasteId;
    @Column(name = "sub_caste_name")
    private String subCasteName;
    @Column(name = "caste_code")
    private String casteCode;
    @Column(name = "usage_count")
    private Integer usageCount;
}
