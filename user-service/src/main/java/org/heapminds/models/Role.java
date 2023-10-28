package org.heapminds.models;


import org.heapminds.enums.RoleEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "roles")
@Entity
@Data
public class Role extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

}
