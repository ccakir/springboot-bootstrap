package com.cakir.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cakir.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(String name);

}
