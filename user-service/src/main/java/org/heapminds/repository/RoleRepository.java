package org.heapminds.repository;


import java.util. Optional;

import org.heapminds.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
Optional<Role> findByName(String name);
}