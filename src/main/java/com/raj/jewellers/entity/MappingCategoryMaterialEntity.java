package com.raj.jewellers.entity;

import java.io.Serializable;
import java.util.Objects;

import com.raj.jewellers.entity.key.MappingCategoryMaterialId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "mapping_category_material",
        uniqueConstraints = { @UniqueConstraint(
                columnNames = { "material_id", "category_id" }) })
@Data
public class MappingCategoryMaterialEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private MappingCategoryMaterialId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("materialId")
    @JoinColumn(name = "material_id", nullable = false)
    private MasterMaterialEntity material;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", nullable = false)
    private MasterCategoryEntity category;

    // Optional equals and hashCode overrides
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MappingCategoryMaterialEntity))
            return false;
        MappingCategoryMaterialEntity that =
                (MappingCategoryMaterialEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
