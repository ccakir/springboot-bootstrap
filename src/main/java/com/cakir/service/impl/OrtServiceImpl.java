package com.cakir.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cakir.model.Ort;
import com.cakir.repository.OrtRepository;
import com.cakir.service.OrtService;

@Service
public class OrtServiceImpl implements OrtService{
	
	private static final Log logger = LogFactory.getLog(OrtServiceImpl.class);
	
	@Autowired
	private OrtRepository ortRepository;

	@Override
	public Ort save(Ort ort) {
		
		Ort savedOrt =  ortRepository.save(ort);
		logger.info("Neu Datensatz : " +ort);
		return savedOrt;
	}

	@Override
	public Ort update(Ort ort) {
		
		Ort updatedOrt =  ortRepository.save(ort);
		logger.info("Datensatz Update: " +updatedOrt);
		return updatedOrt;
	}

	@Override
	public boolean deleteById(Long id) {
		ortRepository.deleteById(id);
		
		Ort ort = ortRepository.findById(id).orElse(null);
		
		if(ort == null) {
			logger.info("Datensatz deleted ID : "+id);
			return true;
		} else {
			logger.error("Datensatz delete error ID : "+id);
			return false;
		}
	}

	

	@Override
	public Optional<Ort> findById(long id) {
		
		
		return ortRepository.findById(id);
	}

	@Override
	public void deleteByIds(List<Ort> listOrte) {
		
		logger.info("Alle Datensätze deleted : "+listOrte.toString());
		ortRepository.deleteInBatch(listOrte);
	}

	@Override
	public List<Ort> alleOrte() {
		
		return ortRepository.findAll();
	}

	@Override
	public Ort findByOrtsname(String ortsname) {
		
		return ortRepository.findByOrtsname(ortsname);
	}

	

	@Override
	public List<Ort> findSomeOrte(Iterable<Long> ids) {
		return ortRepository.findAllById(ids);
	}

}
