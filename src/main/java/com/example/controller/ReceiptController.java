package com.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Receipt;
import com.example.service.PdfReceiptService;  

@RestController
public class ReceiptController {


	@PostMapping(value = "/download-receipt") 
	public boolean downloadReceipt(@RequestBody Receipt receipt) {
		try {
			PdfReceiptService pdfReceipt = new PdfReceiptService();
			pdfReceipt.generateReceipt(receipt);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
