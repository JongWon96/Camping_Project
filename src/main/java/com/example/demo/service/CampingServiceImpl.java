package com.example.demo.service;

import com.example.demo.domain.Camping;
import com.example.demo.persistence.CampingRepository;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.example.demo.domain.Camping;
import com.example.demo.persistence.CampingRepository;
//import com.example.demo.persistence.CampingSiteRepository;
//import com.example.demo.persistence.ProductRepository;

import org.springframework.stereotype.Service;


@Service
public class CampingServiceImpl implements CampingService{
	@Autowired
	private CampingRepository campingRepo;

	@Override
	public Page<Camping> getAllCampingByFacltnm(String facltnm, int page, int size) {
		// page 번호는 0부터 시작함. 제품명(name)순으로 정렬
		Pageable paging = PageRequest.of(page-1, size, Direction.ASC, "facltnm");
		//List<CampingSite> campingSites = campingSiteRepository.findAll();
		return (campingRepo != null) ? campingRepo.findByFacltnmContainingIgnoreCase(facltnm, paging): Page.empty();
	}




    @Autowired CampingRepository campRepo;

    @Override
    public Camping findById(Long id) {
        return campRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Camping not found"));
    }
	@Override
	public Camping getCamping(long Id) {

		return campingRepo.findById(Id).get();
	}

    @Override
    public Camping getCampingDetail(Long campingId) {

        return campRepo.findById(campingId).get();
    }
	@Override
	public List<Camping> getCampingListByCategory(String category) {

		return campingRepo.findCampingByCategoryContaining(category);
	}

	@Override
	public Page<Camping> getAllCamping(int page, int size) {

		Pageable paging = PageRequest.of(page - 1, size, Direction.ASC, "facltnm");

		return campRepo.findAll(paging);
	public List<Camping> getAllCampings(String facltnm) {

		return campingRepo.findCampingsByFacltnmContainingOrderByFacltnm(facltnm);
	}

	@Override
	public List<Camping> getTmpCamping() {

		return campRepo.findAll();
	public void insertCamping(Camping vo) {

		Long nextId = campingRepo.getMaxId();
		vo.setId(nextId);
		System.out.println("Camping Data="+vo);

		campingRepo.save(vo);
	}

	@Override
	public Camping getCampingByProductId(Long productId) {
	public void updateCamping(Camping vo) {
		Camping p = campingRepo.findById(vo.getId()).get();

		vo.setCreatedtime(p.getCreatedtime());  // 기존의 등록일 사용
		campingRepo.save(vo);
	}

		return campRepo.findCampingByProductid(productId);
	@Override
	public Page<Camping> getAllCampingsByFacltnm(String facltnm, int page, int size) {
		// page 번호는 0부터 시작함. 제품명(name)순으로 정렬
		Pageable paging = PageRequest.of(page-1, size, Direction.ASC, "facltnm");

		return campingRepo.findAllCampingsByFacltnmContaining(facltnm, paging);
	}

	public Page<Camping> getAllCamping(Pageable pageable) {
	    return campingRepo.findAll(pageable);
	}
}
