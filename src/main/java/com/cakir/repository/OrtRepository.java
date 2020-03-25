package com.cakir.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cakir.model.Ort;

public interface OrtRepository extends JpaRepository<Ort, Long>{
	
	Ort findByOrtsname(String ortsname);

}
