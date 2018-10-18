package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class InstansiController {
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
}
