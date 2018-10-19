package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;

public interface InstansiService {
	InstansiModel getInstansiDetailById(Long id);
	List<InstansiModel> getInstansi();
//	InstansiModel getPegawaiTermudaBy();
	

//	InstansiModel pegawaiTertua(Long id);
}
