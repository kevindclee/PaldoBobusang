package dev.pb.pb_backend.domain.common.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.pb.pb_backend.domain.common.entity.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

	// 쿼리 메서드
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

	// location.countryCode 으로 조회
	List<Price> findByLocationCountryCode(int countryCode);
	
	List<Price> findByFruitItemCodeAndLocationLocationId(int itemCode, int locationId);
	List<Price> findByVegetableItemCodeAndLocationLocationId(int itemCode, int locationId);
	Price findByFruitItemCodeAndLocationLocationIdAndPriceDate(int itemCode, int locationId, LocalDate priceDate);
	Price findByVegetableItemCodeAndLocationLocationIdAndPriceDate(int itemCode, int locationId, LocalDate priceDate);
	
	List<Price> findByFruitItemCode(int itemCode);
	List<Price> findByVegetableItemCode(int itemCode);
	
	List<Price> findByFruitItemName(String itemName);
	List<Price> findByVegetableItemName(String itemName);

	List<Price> findByFruitItemNameAndLocationLocalEngName(String itemName, String localEngName);
	List<Price> findByVegetableItemNameAndLocationLocalEngName(String itemName, String localEngName);
	
}
