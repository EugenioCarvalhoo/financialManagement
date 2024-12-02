package com.carvalho.solution.domain.tenant;

import com.carvalho.solution.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tenants")
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantEntity extends BaseEntity {

    @Column()
    private String name;

    @Column()
    private String domain;

    @Column( name = "bussiness_name" )
    private String bussinessName;

}
