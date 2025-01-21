package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Inquiry;
import com.example.demo.persistence.InquiryRepository;

@Service
public class InquiryServiceImpl implements InquiryService {

	@Autowired
	private InquiryRepository InquiryRepo;
	
	@Override
	public void insertInquiry(Inquiry Inquiry) {
		
		InquiryRepo.save(Inquiry);
	}
	
	@Override
	public Inquiry getInquiry(long qseq) {
		
		return InquiryRepo.findById(qseq).get();
	}
	

	@Override
	public List<Inquiry> getAllInquiry() {
		
		return InquiryRepo.findAll();
	}

	@Override
	public void updateInquiry(Inquiry vo) {
		Optional<Inquiry> result = InquiryRepo.findById(vo.getId());
		
		if (result.isPresent()) {
			System.out.println("Update Q&A = " + vo);
			InquiryRepo.save(result.get());
		}
		
	}

	@Override
	public List<Inquiry> getInquiryList(Long id) {
	
		return InquiryRepo.getInquiryList(id);
	}
}









