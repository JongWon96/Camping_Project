package com.example.demo.service;

import com.example.demo.domain.Camping;
import com.example.demo.persistence.CampingRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
@Transactional
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

    @Autowired 
    CampingRepository campRepo;

    private final CampingRepository campingRepository;
    
    public CampingServiceImpl(CampingRepository campingRepository) {
        this.campingRepository = campingRepository;
    }

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
	}

	public List<Camping> getAllCampings(String facltnm) {

		return campingRepo.findCampingsByFacltnmContainingOrderByFacltnm(facltnm);
	}

	@Override
	public List<Camping> getTmpCamping() {

		return campRepo.findAll();
	}

	public void insertCamping(Camping vo) {

		Long nextId = campingRepo.getMaxId();
		vo.setId(nextId);
		System.out.println("Camping Data="+vo);

		campingRepo.save(vo);
	}

	@Override
	public Camping getCampingByProductId(Long productId) {
		return campingRepo.findCampingByProductid(productId);
	}


//	@Override
//	public Page<Camping> getSearhResult(String donm, String sigungunm, String category, String campingName, String flooring,
//			LocalDate startDate, String petAllowed, String trailerAllowed,
//			String carananAllowed, String sort, int page, int size) 
//	{
//		
//			Pageable paging = PageRequest.of(page - 1, size, Direction.ASC, "facltnm");
//			
//		return campRepo.searchCampings(donm, sigungunm, category, campingName, flooring,
//			startDate, petAllowed, trailerAllowed, carananAllowed, paging);
//	}

    @Override
    public List<Camping> getCampingByAvgRatingDesc() {
        return campingRepository.findAllByAvgRatingDesc();
    }

    @Override
    public List<Camping> getCampingByLowestPriceAsc() {
        return campingRepository.findAllByLowestPriceAsc();
    }

    @Override
    public List<Camping> getCampingByHighestPriceDesc() {
        return campingRepository.findAllByHighestPriceDesc();
    }

	public void updateCamping(Camping vo) {
		// 기존 데이터 조회
		Camping existingCamping = campingRepo.findById(vo.getId())
				.orElseThrow(() -> new IllegalArgumentException("캠핑 데이터가 존재하지 않습니다: " + vo.getId()));

		// 기존의 등록일 유지
		vo.setCreatedtime(existingCamping.getCreatedtime());

		// 업데이트된 엔티티 저장
		campingRepo.save(vo);
	}


	@Override
	public Page<Camping> getAllCampingsByFacltnm (String facltnm, int page, int size){
		// page 번호는 0부터 시작함. 제품명(name)순으로 정렬
		Pageable paging = PageRequest.of(page - 1, size, Direction.ASC, "facltnm");

		return campingRepo.findAllCampingsByFacltnmContaining(facltnm, paging);
	}

	public Page<Camping> getAllCamping(Pageable pageable) {

		return campingRepo.findAll(pageable);
	}
}
