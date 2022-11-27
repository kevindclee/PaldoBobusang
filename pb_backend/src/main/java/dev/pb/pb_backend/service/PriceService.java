package dev.pb.pb_backend.service;

import java.util.Date;
import java.util.List;

import dev.pb.pb_backend.entity.Price;
import dev.pb.pb_backend.projection.PriceLocationIdProjection;

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
	
	List<PriceLocationIdProjection> findByFruitItemCode(int itemCode);
	List<PriceLocationIdProjection> findByVegetableitemCode(int itemCode);
	List<Price.Response> findByFruitItemCodeAndLocalEngName(int itemCode, String localEngName);
	List<Price.Response> findByVegetableItemCodeAndLocalEngName(int itemCode, String localEngName);
	
	Price findByFruitItemCodeAndLocationIdAndPriceDate(int itemCode, int locationId, Date priceDate);
	Price findByVegetableitemCodeAndLocationIdAndPriceDate(int itemCode, int locationId, Date priceDate);

	List<Price> findByFruitItemNameAndLocalEngName(String itemName, String localEngName);
	List<Price> findByVegetableItemNameAndLocalEngName(String itemName, String localEngName);
	
}
