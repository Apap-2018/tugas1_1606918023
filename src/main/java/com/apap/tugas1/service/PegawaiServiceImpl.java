package com.apap.tugas1.service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDB;
import com.apap.tugas1.repository.JabatanDB;
import com.apap.tugas1.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDB pegawaiDb;
	
	@Autowired
	private InstansiDB instansiDb;

	@Autowired
	private JabatanDB jabatanDb;
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public long hitungGaji(PegawaiModel pegawai) {
		Set<JabatanModel> jabatannya = pegawai.getTiapJabatan();
		double gajiTertinggi = 0;
		for (JabatanModel jabatan : jabatannya) {
			if(jabatan.getGajiPokok() > gajiTertinggi) {
				gajiTertinggi = jabatan.getGajiPokok();
			}
		}
		double presentaseGaji = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
		double gaji = gajiTertinggi + ((presentaseGaji/100)*gajiTertinggi);
		return (long) gaji;
	}

	@Override
	public PegawaiModel tambahPegawai(PegawaiModel pegawai) {
		Set<JabatanModel> tiapJabatan = new HashSet<>();
		for(JabatanModel jabatan : pegawai.getTiapJabatan()) {
			tiapJabatan.add(jabatanDb.findJabatanById(jabatan.getId()));
		}
		pegawai.setTiapJabatan(tiapJabatan);
//		pegawai.setInstansi(instansiDb.getOne(pegawai.getInstansi().getId()));
		pegawai.setNip(generateNip(pegawai));
		pegawaiDb.save(pegawai);
		return pegawai;
	}

	@Override
	public PegawaiModel ubahPegawai(PegawaiModel pegawai) {
		PegawaiModel dataPegawai = pegawaiDb.findByNip(pegawai.getNip());
		System.out.println(pegawai.getNama());
		dataPegawai.setNama(pegawai.getNama());
		dataPegawai.setInstansi(pegawai.getInstansi());
		dataPegawai.setTanggalLahir(pegawai.getTanggalLahir());
		dataPegawai.setTempatLahir(pegawai.getTempatLahir());
		dataPegawai.setTahunMasuk(pegawai.getTahunMasuk());
		dataPegawai.setTiapJabatan(pegawai.getTiapJabatan());
		dataPegawai.getInstansi().setProvinsi(pegawai.getInstansi().getProvinsi());
		dataPegawai.setNip(generateNip(pegawai));
		pegawaiDb.save(dataPegawai);
		return dataPegawai;
	}

	@Override
	public List<PegawaiModel> getPegawaiMuda(InstansiModel instansi) {
		return pegawaiDb.findAllByInstansiOrderByTanggalLahirDesc(instansi);
	}

	@Override
	public List<PegawaiModel> getPegawaiTua(InstansiModel instansi) {
		return pegawaiDb.findAllByInstansiOrderByTanggalLahirAsc(instansi);
	}

	@Override
	public String generateNip(PegawaiModel pegawai) {
		InstansiModel instansi = pegawai.getInstansi();
		ProvinsiModel provinsi = pegawai.getInstansi().getProvinsi();
		DateFormat date = new SimpleDateFormat("ddMMyy");
		String nipIdProvinsi = Long.toString(provinsi.getId());
		String idInstansi = Long.toString(instansi.getId());
		String nipInstansi = idInstansi.substring(Math.max(idInstansi.length() - 2, 0));
		String nipTanggalLahir = date.format(pegawai.getTanggalLahir());
		String nipTahunMasuk = pegawai.getTahunMasuk();
		int urutanPegawai = pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk()).size()+1;
		String nipUrutanPegawai = "";
		if (urutanPegawai<10) {
			nipUrutanPegawai += "0" + urutanPegawai;
		} else {
			nipUrutanPegawai += urutanPegawai;
		}
		String nip = nipIdProvinsi + nipInstansi + nipTanggalLahir + nipTahunMasuk + nipUrutanPegawai;		
		return nip;
	}

	@Override
	public List<PegawaiModel> getSemuaPegawai() {
		return pegawaiDb.findAll();
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		return pegawaiDb.findPegawaiByInstansi(instansi);
	}
	
}
