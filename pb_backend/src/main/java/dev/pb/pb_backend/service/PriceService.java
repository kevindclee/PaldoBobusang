package dev.pb.pb_backend.service;

import java.util.List;

import dev.pb.pb_backend.entity.Price;

public interface PriceService {

	// 전체 테이블 조회
	List<Price> findAllPrices();
	// 레코드(record) 조회
	Price findPriceByPriceId(int priceId);
	// 레코드 생성
	Price createPrice(Price newPrice);
	
	// ??
//	List<Price>

	// PriceRepository 쿼리 메서드
	// location.countryCode 으로 조회
	List<Price> findByLocationCountryCode(int countryCode);
	
}
