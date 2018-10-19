package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class InstansiController {
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
//	@RequestMapping(value="/pegawai/termuda-tertua", method=RequestMethod.GET)
//	public String viewJabatan(@RequestParam(value = "idInstansi") Long idInstansi, Model model) {
//		//InstansiModel archive = instansiService
//		
//		
//		model.addAttribute(null, attributeValue);
//		model.addAttribute("jabatan", archive);
//		return "pegawaiInstansi";
//	}

}
