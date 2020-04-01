package com.cakir.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cakir.model.Ort;
import com.cakir.repository.OrtRepository;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class OrtServiceImplTest {

	@InjectMocks
	private OrtServiceImpl ortService;
	
	@Mock
	private OrtRepository ortRepository;
	
	@Test
	public void test() {
		
		Ort ort = new Ort(1L, "ASPERN", "Hauptstrasse 54", "Wien", "1220", "Österreich");
		
		when(ortRepository.save(any(Ort.class))).thenReturn(ort);
		
		Ort result = ortService.save(ort);
		
		assertNotNull(result);
		assertEquals(result.getOrtsname(), ort.getOrtsname());
	}

}
