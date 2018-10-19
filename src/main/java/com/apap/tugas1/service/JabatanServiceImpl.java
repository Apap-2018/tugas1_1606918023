package com.apap.tugas1.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDB;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDB jabatanDb;

	@Override
	public void tambahJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}

	@Override
	public JabatanModel getJabatanDetailById(Long id) {
		return jabatanDb.getOne(id);
	}

	@Override
	public void ubahJabatan(JabatanModel jabatan, Long id) {
		JabatanModel archive = jabatanDb.getOne(id);
		archive.setNama(jabatan.getNama());
		archive.setDeskripsi(jabatan.getDeskripsi());
		archive.setGajiPokok(jabatan.getGajiPokok());
		jabatanDb.save(archive);
	}

	@Override
	public void hapusJabatanById(Long id) {
		jabatanDb.deleteById(id);
	}

	@Override
	public List<JabatanModel> getJabatan() {
		return jabatanDb.findAll();
	}
}
