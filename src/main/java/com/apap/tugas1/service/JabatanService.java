package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	void tambahJabatan(JabatanModel jabatan);
	void ubahJabatan(JabatanModel jabatan, Long id);
	void hapusJabatanById(Long id);
	JabatanModel getJabatanDetailById(Long id);
	List<JabatanModel> getJabatan();
}
