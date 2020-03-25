package com.cakir.service;

import java.util.List;
import java.util.Optional;

import com.cakir.model.Ort;

public interface OrtService {
	
	List<Ort> alleOrte();
	
	Ort save(Ort ort);
	
	Ort update(Ort ort);
	
	boolean deleteById(Long id);
	
	void deleteByIds(List<Ort> listOrte);
	
	Optional<Ort> findById(long id);
	
	Ort findByOrtsname(String ortsname); 
	
	List<Ort> findSomeOrte(Iterable<Long> ids);
	

}
