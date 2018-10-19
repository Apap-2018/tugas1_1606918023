package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value="/jabatan/tambah")
	private String tambahJabatan(Model model) {
		JabatanModel jabatan = new JabatanModel();
		model.addAttribute("jabatan", jabatan);
		return "addJabatan";
	}
	
	@RequestMapping(value="/jabatan/tambah", method=RequestMethod.POST)
	private String tambahJabatanSimpan(@ModelAttribute JabatanModel jabatan, RedirectAttributes redirectAtt) {
		jabatanService.tambahJabatan(jabatan);
		String message = "Jabatan " + jabatan.getNama() + " berhasil ditambah";
		redirectAtt.addFlashAttribute("message", message);
		return "redirect:/jabatan/tambah";
	}
	
	@RequestMapping(value="/jabatan/view", method=RequestMethod.GET)
	public String viewJabatan(@RequestParam(value = "idJabatan") Long idJabatan, Model model) {
		JabatanModel archive = jabatanService.getJabatanDetailById(idJabatan);
		model.addAttribute("jabatan", archive);
		return "viewJabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.GET)
	public String ubahJabatan(@RequestParam(value = "idJabatan") Long idJabatan, Model model) {
		JabatanModel archive = jabatanService.getJabatanDetailById(idJabatan);
		model.addAttribute("jabatan", archive);
		return "ubahJabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah",method=RequestMethod.POST)
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan, RedirectAttributes redirectAtt) {
		String message = "Jabatan " + jabatan.getNama() + " berhasil diubah!";
		redirectAtt.addFlashAttribute("message", message);
		redirectAtt.addAttribute("idJabatan", jabatan.getId());
		jabatanService.ubahJabatan(jabatan, jabatan.getId());
		return "redirect:/jabatan/ubah";
	}
	
	@RequestMapping(value="/jabatan/hapus", method=RequestMethod.POST)
	public String hapusJabatan(@ModelAttribute JabatanModel jabatan, Model model, RedirectAttributes redirectAtt) {
		JabatanModel jabatannya = jabatanService.getJabatanDetailById(jabatan.getId());
		String message = "Jabatan " + jabatannya.getNama() + " berhasil dihapus!";
		redirectAtt.addFlashAttribute("message", message);
		model.addAttribute("jabatan", jabatannya.getNama());
		jabatanService.hapusJabatanById(jabatan.getId());
		return "redirect:/";
	}
		
	@RequestMapping(value="/jabatan/viewall", method=RequestMethod.GET)
	public String viewallJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		model.addAttribute("jabatan", jabatan);
		return "viewallJabatan";
	}
	
	
	
	
}
