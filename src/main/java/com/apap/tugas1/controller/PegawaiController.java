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

import com.apap.tugas1.model.InstansiModel;
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
		List<InstansiModel> listInstansi = instansiService.getInstansi();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}
	
	@RequestMapping(value="/pegawai", method=RequestMethod.GET)
	public String view(@RequestParam(value = "nip") String nip, Model model) {
		PegawaiModel archive = pegawaiService.getPegawaiDetailByNip(nip);
		Set<JabatanModel> jabatannya = archive.getTiapJabatan();
		String namaInstansi = archive.getInstansi().getNama();
		long gaji = pegawaiService.hitungGaji(archive);
		model.addAttribute("pegawai", archive);
		model.addAttribute("listJabatan", jabatannya);
		model.addAttribute("namaInstansi", namaInstansi);
		model.addAttribute("gajiTertinggi", gaji);
		return "dataPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.GET)
	public String tambahPegawai(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getJabatan();
		List<InstansiModel> listInstansi = instansiService.getInstansi();
		List<ProvinsiModel> listProvinsi = provinsiService.getProvinsi();
		model.addAttribute("pegawai", new PegawaiModel());
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listProvinsi", listProvinsi);
		return "tambahPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah")
	public String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawaiService.tambahPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("message", "ditambahkan");
		return "suksesPegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah")
	public String ubahPegawai(@RequestParam(value="nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		model.addAttribute("pegawai", pegawai);
		return "updatePegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST)
	public String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("message", "diubah");
		return "updatePegawai";
	}
	
//	@RequestMapping(value="/pegawai/termuda-tertua", method=RequestMethod.GET)
//	public String pegawaiTuaMuda(@RequestParam(value="idInstansi") Long idInstansi, Model model) {
//		InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi);
//		
//		
//		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
//		model.addAttribute("pegawaiMuda", pegawaiMuda);
//		model.addAttribute("pegawaiTua", pegawaiTua);
//		return "pegawaiTuaMuda";
//	}
	
}
