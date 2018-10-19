package com.apap.tugas1.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDB pegawaiDb;

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
	public void tambahPegawai(PegawaiModel pegawai) {
		//jangan lupa tambahin set dulu buat NIP
		
		
		pegawaiDb.save(pegawai);
	}

	@Override
	public void ubahPegawai(PegawaiModel pegawai) {
		PegawaiModel archive = pegawaiDb.findByNip(pegawai.getNip());
		
		archive.setNama(pegawai.getNama());
		archive.setInstansi(pegawai.getInstansi());
		archive.setTanggalLahir(pegawai.getTanggalLahir());
		archive.setTempatLahir(pegawai.getTempatLahir());
		archive.setTahunMasuk(pegawai.getTahunMasuk());
		
//		pegawai.getInstansi().getId();
//		
//		int nipLama = Integer.parseInt(pegawai.getNip());
//		
//		
//		int nipBaru = 2;
//		pegawai.setNip(nipBaru);
//		
		pegawaiDb.save(pegawai);
	}
	
}
