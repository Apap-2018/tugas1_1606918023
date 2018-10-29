package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping(value="/pegawai/tambah")
	public String tambahPegawai(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getJabatan();
		List<InstansiModel> listInstansi = instansiService.getInstansi();
		List<ProvinsiModel> listProvinsi = provinsiService.getProvinsi();

		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setInstansi(new InstansiModel());
		pegawai.getInstansi().setProvinsi(new ProvinsiModel());
		pegawai.setTiapJabatan(new HashSet<>());
		pegawai.getTiapJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listProvinsi", listProvinsi);
		return "tambahPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST)
	public String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		PegawaiModel pegawaiBaru = pegawaiService.tambahPegawai(pegawai);
		model.addAttribute("pegawai", pegawaiBaru);
		model.addAttribute("message", "ditambahkan");
		return "suksesPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"addRow"}, method = RequestMethod.POST)
	public String tambahBarisJabatan(@ModelAttribute PegawaiModel pegawai, Model model, BindingResult bindingResult) {
		if(pegawai.getTiapJabatan()==null) {
			pegawai.setTiapJabatan(new HashSet<JabatanModel>());
		}
		pegawai.getTiapJabatan().add(new JabatanModel());
		List<ProvinsiModel> listProvinsi = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getJabatan();
		List<InstansiModel> listInstansi = instansiService.getInstansi();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
	    model.addAttribute("pegawai", pegawai);
	    return "tambahPegawai";
	}
	
	@RequestMapping(value="/pegawai/instansi", method=RequestMethod.GET)
	public @ResponseBody  List<InstansiModel> getSemuaKategori(@PathVariable("provinsi") Long idProvinsi) {
	    return provinsiService.getSemuaInstansi(idProvinsi);
	}
	
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.GET)
	public String ubahPegawai(@RequestParam(value="nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		List<ProvinsiModel> listProvinsi = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getJabatan();
		List<InstansiModel> listInstansi = instansiService.getInstansi();
//		List<JabatanModel> jabatannya = new ArrayList<JabatanModel>(pegawai.getTiapJabatan());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
//		model.addAttribute("tiapJabatan", jabatannya);
		return "ubahPegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST, params= {"addRow"})
	public String addRowPegawai(@ModelAttribute PegawaiModel pegawai, @RequestParam(value="nip") String nip, Model model) {
		List<JabatanModel> listJabatan = jabatanService.getJabatan();
		List<InstansiModel> listInstansi = instansiService.getInstansi();
		List<ProvinsiModel> listProvinsi = provinsiService.getProvinsi();
		if (pegawai.getTiapJabatan() == null) {
			pegawai.setTiapJabatan(new HashSet<>());
		}
		pegawai.getTiapJabatan().add(new JabatanModel());
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listProvinsi", listProvinsi);
		return "ubahPegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST, params= {"update"})
	public String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, @RequestParam(value="nip") String nip, Model model) {
		List<JabatanModel> listJabatan = jabatanService.getJabatan();
		List<InstansiModel> listInstansi = instansiService.getInstansi();
		List<ProvinsiModel> listProvinsi = provinsiService.getProvinsi();
		pegawaiService.ubahPegawai(pegawai);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("message", "diubah");
		return "suksesPegawai";
	}
	
	@RequestMapping(value="/pegawai/termuda-tertua", method=RequestMethod.GET)
	public String pegawaiTuaMuda(@RequestParam(value="idInstansi") Long idInstansi, Model model) {
		InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi);
		List<PegawaiModel> listPegawaiMuda = pegawaiService.getPegawaiMuda(instansi);
		List<PegawaiModel> listPegawaiTua = pegawaiService.getPegawaiTua(instansi);
		PegawaiModel pegawaiMuda = listPegawaiMuda.get(0);
		PegawaiModel pegawaiTua = listPegawaiTua.get(0);
		String namaTua = listPegawaiTua.get(0).getNama();
		String namaInstansiMuda = pegawaiMuda.getInstansi().getNama();
		String namaInstansiTua = pegawaiTua.getInstansi().getNama();
		Set<JabatanModel> listJabatanMuda = pegawaiMuda.getTiapJabatan();
		Set<JabatanModel> listJabatanTua = pegawaiTua.getTiapJabatan();
		model.addAttribute("pegawaiMuda", pegawaiMuda);
		model.addAttribute("pegawaiTua", pegawaiTua);
		model.addAttribute("namaInstansiMuda", namaInstansiMuda);
		model.addAttribute("namaInstansiTua", namaInstansiTua);
		model.addAttribute("listJabatanMuda", listJabatanMuda);
		model.addAttribute("listJabatanTua", listJabatanTua);
		return "pegawaiTuaMuda";
	}
	
	@RequestMapping(value="/pegawai/cari", method = RequestMethod.GET)
	public String cariPegawai(@RequestParam(value = "idProvinsi", required=false) Optional<String> idProvinsi,
			@RequestParam(value="idInstansi",  required=false) Optional<String> id_instansi,
			@RequestParam(value="idJabatan", required=false) Optional<String> id_jabatan,
			Model model) {
		List<JabatanModel> listJabatan = jabatanService.getJabatan();
		List<InstansiModel> listInstansi = instansiService.getInstansi();
		List<ProvinsiModel> listProvinsi = provinsiService.getProvinsi();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listProvinsi", listProvinsi);
		List<PegawaiModel> allPegawai = pegawaiService.getSemuaPegawai();
		List<PegawaiModel> result = new ArrayList<>();
		if (idProvinsi.isPresent()) {
			if (id_instansi.isPresent() && id_jabatan.isPresent()) {
				List<PegawaiModel> temp = new ArrayList<>();
				Long idInstansi = Long.parseLong(id_instansi.get());
				InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi);
				Long idJabatan = Long.parseLong(id_jabatan.get());
				JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan);
				temp = pegawaiService.getPegawaiByInstansi(instansi);
				for (PegawaiModel pegawainya : temp) {
					for (JabatanModel jabatannya : pegawainya.getTiapJabatan()) {
						if (jabatannya.equals(jabatan)) {
							result.add(pegawainya);
						}
					}
				}
			}
			else if (!(id_instansi.isPresent()) && id_jabatan.isPresent()) {
				List<PegawaiModel> temp = new ArrayList<>();
				Long idProv = Long.parseLong(idProvinsi.get());
				ProvinsiModel prov = provinsiService.getProvinsiDetailById(idProv);
				for (PegawaiModel peg : allPegawai) {
					if (peg.getInstansi().getProvinsi().equals(prov)) {
						temp.add(peg);
					}
				}
				Long idJabatan = Long.parseLong(id_jabatan.get());
				JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan);
				for (PegawaiModel pegawainya : temp) {
					for (JabatanModel jabatannya : pegawainya.getTiapJabatan()) {
						if (jabatannya.equals(jabatan)) {
							result.add(pegawainya);
						}
					}
				}
			}
			else if(id_instansi.isPresent() && !(id_jabatan.isPresent())) { 
				Long idInstansi = Long.parseLong(id_instansi.get());
				InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi);
				result = pegawaiService.getPegawaiByInstansi(instansi);
				
			}
			else if(!(id_instansi.isPresent()) && !(id_jabatan.isPresent())) {
				Long idProv = Long.parseLong(idProvinsi.get());
				ProvinsiModel prov = provinsiService.getProvinsiDetailById(idProv);
				for (PegawaiModel peg : allPegawai) {
					if(peg.getInstansi().getProvinsi().equals(prov)) {
						result.add(peg);
					}
				}
			}
		}
		else {
			if (id_jabatan.isPresent() && id_instansi.isPresent()) {
				List<PegawaiModel> temp = new ArrayList<>();
				Long idInstansi = Long.parseLong(id_instansi.get());
				InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi);
				Long idJabatan = Long.parseLong(id_jabatan.get());
				JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan);
				temp = pegawaiService.getPegawaiByInstansi(instansi);
				for (PegawaiModel pegawainya : temp) {
					for (JabatanModel jabatannya : pegawainya.getTiapJabatan()) {
						if (jabatannya.equals(jabatan)) {
							result.add(pegawainya);
						}
					}
				}
			}
			else if(id_jabatan.isPresent() && !(id_instansi.isPresent())) {
				Long idJabatan = Long.parseLong(id_jabatan.get());
				JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan);
				for (PegawaiModel pegawainya : allPegawai) {
					for (JabatanModel jabatannya : pegawainya.getTiapJabatan()) {
						if (jabatannya.equals(jabatan)) {
							result.add(pegawainya);
						}
					}
				}
			}
			else if(!(id_jabatan.isPresent()) && id_instansi.isPresent()) {
				Long idInstansi = Long.parseLong(id_instansi.get());
				InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi);
				result = pegawaiService.getPegawaiByInstansi(instansi);
			}
			else if(!(id_jabatan.isPresent()) && !(id_instansi.isPresent())) {
				result = null;
			}
		}
		model.addAttribute("listPegawai",result);
		return "cariPegawai";
	}
}
