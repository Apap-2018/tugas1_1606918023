package com.apap.tugas1.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getJabatan();
		model.addAttribute("listJabatan", listJabatan);
		return "home";
	}
	
	@RequestMapping(value="/pegawai", method=RequestMethod.GET)
	public String view(@RequestParam(value = "nip") String nip, Model model) {
		PegawaiModel archive = pegawaiService.getPegawaiDetailByNip(nip);
		Set<JabatanModel> jabatannya = archive.getTiapJabatan();
		String namaInstansi = archive.getInstansi().getNama();
		long gaji = pegawaiService.hitungGaji(archive);

//		double gajiTertinggi = 0;
//		for (JabatanModel jabatan : jabatannya) {
//			if(jabatan.getGajiPokok() > gajiTertinggi) {
//				gajiTertinggi = jabatan.getGajiPokok();
//			}
//		}
//		String namaInstansi = archive.getInstansi().getNama();
//		ProvinsiModel kodeProvinsi = archive.getInstansi().getProvinsi();
//		double presentaseGaji = kodeProvinsi.getPresentaseTunjangan();
//		double gaji = gajiTertinggi + ((presentaseGaji/100)*gajiTertinggi);
		
		model.addAttribute("pegawai", archive);
		model.addAttribute("listJabatan", jabatannya);
		model.addAttribute("namaInstansi", namaInstansi);
		model.addAttribute("gajiTertinggi", gaji);
		return "dataPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST)
	public String addPegawai(@ModelAttribute PegawaiModel pegawai) {
		return "add";
	}
	
	@RequestMapping(value="/pegawai/update", method=RequestMethod.POST)
	public String updatePegawai(@PathVariable(value="nip") String nip, Model model) {
		return "updatePegawai";
	}
	
}
