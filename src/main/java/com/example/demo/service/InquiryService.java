package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Inquiry;

public interface InquiryService {

	void insertInquiry(Inquiry Inquiry);

	Inquiry getInquiry(long qseq);

	List<Inquiry> getInquiryList(Long id);
	
	List<Inquiry> getAllInquiry();

	void updateInquiry(Inquiry vo);
}