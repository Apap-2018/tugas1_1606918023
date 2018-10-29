package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel tambahPegawai(PegawaiModel pegawai);
	PegawaiModel ubahPegawai(PegawaiModel pegawai);
	PegawaiModel getPegawaiDetailByNip(String nip);
	List<PegawaiModel> getSemuaPegawai();
	long hitungGaji(PegawaiModel pegawai);
	List<PegawaiModel> getPegawaiMuda(InstansiModel instansi);
	List<PegawaiModel> getPegawaiTua(InstansiModel instansi);
	String generateNip(PegawaiModel pegawai);
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	
}
