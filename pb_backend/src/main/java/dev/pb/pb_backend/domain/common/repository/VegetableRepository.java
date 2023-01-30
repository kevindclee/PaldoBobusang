package dev.pb.pb_backend.domain.common.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.pb.pb_backend.domain.common.entity.Vegetable;

@Repository
public interface VegetableRepository extends JpaRepository<Vegetable, Integer> {

	// 쿼리 메서드
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	
	// locationId 으로 조회
	List<Vegetable> findByLocationsLocationId(int locationId);
	List<Vegetable> findDistinctByLocationsLocalEngName(String localEngName);
	List<Vegetable> findByLocationsCityName(String cityName);
	Vegetable findDistinctByItemNameAndLocationsLocalEngName(String itemName, String localEngName);

	// harvestDate 으로 조회
	List<Vegetable> findByHarvestStartBeforeAndHarvestEndAfter(LocalDate curDate1, LocalDate curDate2);
	
}
