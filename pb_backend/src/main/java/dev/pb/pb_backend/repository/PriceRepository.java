package dev.pb.pb_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.pb.pb_backend.entity.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

	// 쿼리 메서드
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

	// location.countryCode 으로 조회
	List<Price> findByLocationCountryCode(int countryCode);
	
}
