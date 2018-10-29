package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDB;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService {
	@Autowired
	private ProvinsiDB provinsiDb;

	@Override
	public List<ProvinsiModel> getProvinsi() {
		return provinsiDb.findAll();
	}

	@Override
	public ProvinsiModel getProvinsiDetailById(Long idProv) {
		return provinsiDb.getOne(idProv);
	}

	@Override
	public List<InstansiModel> getSemuaInstansi(Long idProv) {
		return provinsiDb.findInstansiById(idProv);
	}
	
}
