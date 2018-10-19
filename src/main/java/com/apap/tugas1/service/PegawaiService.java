package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	void tambahPegawai(PegawaiModel pegawai);
	void ubahPegawai(PegawaiModel pegawai);
	PegawaiModel getPegawaiDetailByNip(String nip);
	long hitungGaji(PegawaiModel pegawai);
	List<PegawaiModel> getPegawaiMuda(InstansiModel instansi);
	List<PegawaiModel> getPegawaiTua(InstansiModel instansi);
	Long getNip(PegawaiModel pegawai);
}
