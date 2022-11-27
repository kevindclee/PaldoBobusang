package dev.pb.pb_backend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.pb.pb_backend.entity.Price;
import dev.pb.pb_backend.projection.PriceLocationIdProjection;
import dev.pb.pb_backend.projection.PriceProjection;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

	// 쿼리 메서드
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

	// location.countryCode 으로 조회
	List<Price> findByLocationCountryCode(int countryCode);
	
	List<PriceLocationIdProjection> findDistinctByFruitItemCode(int itemCode);
	List<PriceLocationIdProjection> findDistinctByVegetableItemCode(int itemCode);
	List<PriceProjection> findByFruitItemCodeAndLocationLocationId(int itemCode, int locationId);
	List<PriceProjection> findByVegetableItemCodeAndLocationLocationId(int itemCode, int locationId);
	Price findByFruitItemCodeAndLocationLocationIdAndPriceDate(int itemCode, int locationId, Date priceDate);
	Price findByVegetableItemCodeAndLocationLocationIdAndPriceDate(int itemCode, int locationId, Date priceDate);

	List<Price> findByFruitItemNameAndLocationLocalEngName(String itemName, String localEngName);
	List<Price> findByVegetableItemNameAndLocationLocalEngName(String itemName, String localEngName);
	
}
