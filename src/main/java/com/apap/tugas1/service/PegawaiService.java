package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	void tambahPegawai(PegawaiModel pegawai);
	void ubahPegawai(PegawaiModel pegawai);
	PegawaiModel getPegawaiDetailByNip(String nip);
	long hitungGaji(PegawaiModel pegawai);
}
