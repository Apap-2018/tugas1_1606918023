package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value="/jabatan/tambah")
	private String tambahJabatan(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "addJabatan";
	}
	
	@RequestMapping(value="/jabatan/tambah", method=RequestMethod.POST)
	private String tambahJabatanSimpan(@ModelAttribute JabatanModel jabatan) {
		jabatanService.tambahJabatan(jabatan);
		return "addJabatanSukses";
	}
	
	@RequestMapping(value="/jabatan/view", method=RequestMethod.GET)
	public String viewJabatan(@RequestParam(value = "idJabatan") Long idJabatan, Model model) {
		JabatanModel archive = jabatanService.getJabatanDetailById(idJabatan);
		model.addAttribute("jabatan", archive);
		return "viewJabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.POST)
	public String ubahJabatan(@RequestParam(value = "idJabatan") Long idJabatan, Model model) {
		JabatanModel archive = jabatanService.getJabatanDetailById(idJabatan);
		model.addAttribute("jabatan", archive);
		return "ubahJabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah")
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan) {
		jabatanService.ubahJabatan(jabatan, jabatan.getId());
		return "ubahJabatan";
	}
	
	@RequestMapping(value="/jabatan/hapus", method=RequestMethod.POST)
	public String hapusJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.hapusJabatan(jabatan);
		return "hapusJabatan";
	}
		
	@RequestMapping(value="/jabatan/viewall", method=RequestMethod.GET)
	public String viewallJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		model.addAttribute("jabatan", jabatan);
		return "viewallJabatan";
	}
	
	
	
	
}
