package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.InstansiDB;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
	@Autowired
	private InstansiDB instansiDb;

	@Override
	public List<InstansiModel> getInstansi() {
		return instansiDb.findAll();
	}

	@Override
	public InstansiModel getInstansiDetailById(Long id) {
		return instansiDb.getOne(id);
	}
}
