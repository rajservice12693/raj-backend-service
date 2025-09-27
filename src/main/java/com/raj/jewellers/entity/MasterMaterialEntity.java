package com.raj.jewellers.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "master_materials",
        uniqueConstraints = { @UniqueConstraint(
                columnNames = { "material_name", "category_id" }) })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterMaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialId;

    @Column(name = "material_name", nullable = false, unique = true)
    private String materialName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private MasterCategoryEntity category;
}
