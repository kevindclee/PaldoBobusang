package dev.pb.pb_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.pb.pb_backend.entity.Location;
import dev.pb.pb_backend.projection.LocationCountryCodeProjection;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

	// 쿼리 메서드
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

	// localName 으로 조회
	List<LocationCountryCodeProjection> findByLocalName(String localName);
	
}
