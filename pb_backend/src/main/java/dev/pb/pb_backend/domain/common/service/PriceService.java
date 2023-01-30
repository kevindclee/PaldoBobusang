package dev.pb.pb_backend.domain.common.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import dev.pb.pb_backend.domain.common.entity.Price;

public interface PriceService {

	// 전체 테이블 조회
	Map<String, List<Object>> findAllPrices();
	// 레코드(record) 조회
	Object findPriceByPriceId(int priceId);
	// 레코드 생성
	Object createPrice(Price.Request request);

	// PriceRepository 쿼리 메서드
	// location.countryCode 으로 조회
	List<Object> findByLocationCountryCode(int countryCode);
	
	List<Object> findByItemCode(int itemCode);
	List<Object> findByItemCodeAndLocationId(int itemCode, int locationId);
	List<Object> findByItemNameAndLocalEngName(String itemName, String localEngName);
	
	Object findByItemCodeAndLocationIdAndPriceDate(int itemCode, int locationId, LocalDate priceDate);
	
}
