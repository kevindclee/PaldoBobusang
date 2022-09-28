package dev.pb.pb_backend.service;

import java.util.List;

import dev.pb.pb_backend.entity.Location;
import dev.pb.pb_backend.projection.LocationCountryCodeProjection;

public interface LocationService {

	// 전체 테이블 조회
	List<Location> findAllLocations();
	// 레코드(record) 조회
	Location findLocationByLocationId(int locationId);
	// 레코드 생성
	Location createLocation(Location newLocation);

	// LocationRepository 쿼리 메서드
	// localName 으로 조회
	List<LocationCountryCodeProjection> findByLocalName(String localName);
	
}
