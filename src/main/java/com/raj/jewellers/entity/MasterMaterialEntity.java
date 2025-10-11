package com.raj.jewellers.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "master_materials",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "material_name" }) })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterMaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id", nullable = false, unique = true)
    private Long materialId;

    @Column(name = "material_name", nullable = false, unique = true)
    private String materialName;
}
