package com.carvalho.solution.domain.refcode;

import com.carvalho.solution.domain.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ref_codes")
@Data
@EqualsAndHashCode(callSuper = false)
public class RefCodesEntity extends BaseEntity { 

  private String type;
  private String code;
  private String name;
  private String description;
  private Boolean activeFlag;
  @Column( name = "ref_codes_required", nullable = false)
  private Boolean required = true;

  @Column(name = "tenant_id")
  private Long tenantId;

}
