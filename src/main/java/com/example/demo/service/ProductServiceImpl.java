package com.example.demo.service;

import java.sql.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Product;
import com.example.demo.domain.Reservation;
import com.example.demo.persistence.CampingRepository;
import com.example.demo.persistence.ProductRepository;
import com.example.demo.persistence.ReservationRepository;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository; // Product 저장용 리포지토리
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CampingRepository campingRepository; // Camping 조회용 리포지토리

    // 애플리케이션 시작 시 상품 생성
    @Override
    public void generateProductsForExistingCampings() {
        // DB에서 모든 캠핑장 정보 조회
        List<Camping> campings = campingRepository.findAll();

        // 각 캠핑장에 대해 3개의 상품을 생성
        for (Camping camping : campings) {
            // 해당 캠핑장의 상품이 이미 존재하는지 확인
            List<Product> existingProducts = productRepository.findByCamping_Id(camping.getId());

            // 이미 상품이 존재하면 생성하지 않음
            if (!existingProducts.isEmpty()) {
                continue; // 상품이 이미 있으면 생성하지 않고 다음 캠핑장으로 넘어감
            }

            // 캠핑장별 4개의 컬럼 값 중 가장 큰 값 구하기
            int maxSiteCount = Math.max(
                Math.max(camping.getGnrlsiteco(), camping.getAutositeco()),
                Math.max(camping.getGlampsiteco(), camping.getCaravsiteco())
            );

            // maxSiteCount가 3보다 작은 경우 처리
            int room1Count = 0, room2Count = 0, room3Count = 0;

            if (maxSiteCount < 3) {
                // 각 방에 배정할 수 있는 최소 수량을 맞춤 (방의 수가 부족하면 0개로 설정)
                room1Count = maxSiteCount > 0 ? 1 : 0;
                room2Count = maxSiteCount > 1 ? 1 : 0;
                room3Count = maxSiteCount > 2 ? 1 : 0;
            } else {
                // 최소 1개씩 배정 후, 나머지를 랜덤하게 배정
                int remainingSiteCount = maxSiteCount - 3; // 3개 방에 최소 1개씩 배정했으므로 남은 수

                // 각 방에 최소 1개씩 배정 (남은 방은 랜덤으로 배정)
                room1Count = 1 + (int)(Math.random() * (remainingSiteCount));
                room2Count = 1 + (int)(Math.random() * (remainingSiteCount - room1Count));
                room3Count = maxSiteCount - room1Count - room2Count; // 나머지 방 갯수는 자동으로 계산
            }

            // 작은 방 가격 생성 (랜덤)
            Double basePrice = generateRandomPrice(); // 작은 방 가격 (랜덤)

            // 각 방에 대해 상품 생성 (1: 작은 방, 2: 중간 방, 3: 큰 방)
            for (int room = 1; room <= 3; room++) {
                Product product = new Product();
                product.setCamping(camping); // 해당 캠핑장에 속하는 상품
                product.setRoom(room); // 방 번호 (1: 작은 방, 2: 중간 방, 3: 큰 방)

                // 방 갯수에 맞춰 상품 수량을 설정
                int roomCount = 0;
                if (room == 1) {
                    roomCount = room1Count; // 작은 방 수량
                } else if (room == 2) {
                    roomCount = room2Count; // 중간 방 수량
                } else if (room == 3) {
                    roomCount = room3Count; // 큰 방 수량
                }
                product.setRoomcount(roomCount); // roomCount 컬럼 설정

                // 가격 설정 (작은 방 기준으로 비례)
                if (room == 1) {
                    product.setPrice(basePrice); // 작은 방 가격
                } else if (room == 2) {
                    product.setPrice(basePrice * 1.2); // 중간 방 가격 (1.2배)
                } else if (room == 3) {
                    product.setPrice(basePrice * 1.4); // 큰 방 가격 (1.4배)
                }

                // 상품 저장 (DB에 추가)
                productRepository.save(product); // 각 방의 갯수만큼 상품 저장
            }
        }
    }

    // 10만 ~ 20만 사이의 랜덤 가격 생성
    private Double generateRandomPrice() {
        Random random = new Random();
        int minPrice = 100000;
        int maxPrice = 200000;

        // 랜덤 가격 생성 (10만 ~ 20만 사이)
        int price = minPrice + random.nextInt(maxPrice - minPrice + 1);

        // 만원 단위로 변환 (소수점 없이)
        return (price / 1000) * 1000.0; // 1000으로 나누고 다시 곱해서 만원 단위로
    }

    // 캠핑장 ID와 방 이름(room)으로 Product 찾기
    public Product findByCamping_IdAndRoom(Long campingId, int room) {
        // ProductRepository에서 캠핑장 ID와 방 이름(room)으로 찾기
        return productRepository.findByCamping_IdAndRoom(campingId, room);
    }

    @Override
    public int getRoomCount(Long campingId, int room) {
        // 캠핑장 ID와 방 타입에 해당하는 Product를 조회
        Product product = productRepository.findByCamping_IdAndRoom(campingId, room);
        if (product != null) {
            return product.getRoomcount();  // 총 방 수를 반환
        } else {
            return 0;  // 해당하는 상품이 없으면 0을 반환
        }
    }

    @Override
    public int getReservedRoomCount(Long campingId, int room, String checkin, String checkout) {
        // 날짜를 Date 타입으로 변환
        Date checkinDate = Date.valueOf(checkin);
        Date checkoutDate = Date.valueOf(checkout);

        // 특정 캠핑장과 방 타입에 해당하는 예약 목록 조회
        List<Reservation> reservations = reservationRepository.findReservationsByCampingIdAndRoomAndDateRange(campingId, room, checkinDate, checkoutDate);

        // 예약된 방 수를 반환
        return reservations.size();
    }
	@Autowired
	private ProductRepository productRepo;

	@Override
	public List<Product> getProducts(Long campingId) {

		return productRepo.findByCampingId(campingId);
	}

	@Override
	public Product getProduct(Long productId) {

		return productRepo.findById(productId).get();
	}

	@Override
	public Product getProductByRoom(Long campingId, Integer roomNum) {

		return productRepo.findByCamping_idAndRoom(campingId, roomNum);
	}


}
