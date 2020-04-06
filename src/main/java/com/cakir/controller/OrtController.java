package com.cakir.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cakir.model.Ort;
import com.cakir.repository.OrtRepository;
import com.cakir.service.OrtService;
import com.cakir.service.impl.OrtServiceImpl;

@Controller
@RequestMapping("auth/admin/ort/")
public class OrtController {

	@Autowired
	private OrtService ortService;

	@GetMapping(value = "alleorte")
	public String listOrte(ModelMap model) {

		model.put("listOrt", ortService.alleOrte());

		return "ort/ortList";
	}

	@GetMapping(value = "addort")
	public String addOrtForm(ModelMap model) {

		model.put("ortForm", new Ort());

		return "ort/ortAdd";
	}

	@PostMapping(value = "addort")
	public String addOrt(@ModelAttribute("ortForm") @Valid Ort ort, BindingResult bindingResult) {

		Ort ortExisting = ortService.findByOrtsname(ort.getOrtsname());

		if (ortExisting != null) {
			bindingResult.rejectValue("ortsname", null, "Ortsname ist vorhanden!");
		}

		if (bindingResult.hasErrors()) {
			return "ort/ortAdd";
		}

		ortService.save(ort);
		return "redirect:addort?stand=success";

	}
	
	@GetMapping(value = "updateort")
	public String listOrteUpdate(ModelMap model) {

		model.put("listOrt", ortService.alleOrte());

		return "ort/ortUpdateList";
	}
	
	@GetMapping(value = "updateort/{id}")
	public String updateOrtId(@PathVariable("id") long id, ModelMap model) {
		
		model.put("ortForm", ortService.findById(id));
		
		return "ort/ortUpdate";
	}
	
	@PostMapping(value = "updateort/{id}")
	public String updateOrtId(@PathVariable("id") long id, @ModelAttribute("ortForm") @Valid Ort ort, BindingResult bindingResult) {
		
		Ort ortExisting = ortService.findByOrtsname(ort.getOrtsname());

		if (ortExisting != null && ortExisting.getId() != id) {
			bindingResult.rejectValue("ortsname", null, "Ortsname ist vorhanden!");
		}

		if (bindingResult.hasErrors()) {
			return "ort/ortUpdate";
		}
		
		ortService.save(ort);
		
		return "redirect:"+id+"?update=success";
		
	}
	
	@GetMapping(value = "deleteort")
	public String deleteOrtList(ModelMap model) {
		
		model.put("ortDeleteList", ortService.alleOrte());
		
		return "ort/ortDeleteList";
	}
	
	@GetMapping(value = "deleteort/{id}")
	public String deleteOrt(@PathVariable("id") Long id) {
		
		Optional<Ort> ort = ortService.findById(id);
		
		if(!ort.isPresent()) {
			
			return "redirect:?delete=error";
		} 
		
		ortService.deleteById(id);
		
		return "redirect:?delete=success";
		
	}
	
	@PostMapping(value = "deleteort")
	public String deleteOrte(@RequestParam(name = "ids", required = false) Long[] ids) {
		
		if(ids == null) {
			return "redirect:deleteort/?delete=null";
		}
		Iterable<Long> listIds = Arrays.asList(ids);
		
		List<Ort> listOrte = ortService.findSomeOrte(listIds);
		
		if(listOrte.isEmpty()) {
			return "redirect:deleteort/?delete=error";
		}
		
		ortService.deleteByIds(listOrte);
		
		List<Ort> listOrteDeleted = ortService.findSomeOrte(listIds);
		
		if(listOrteDeleted.isEmpty()) {
			return "redirect:deleteort/?delete=success";
		}
		return "redirect:deleteort/?delete=errorrrrr";
		
	}

}
