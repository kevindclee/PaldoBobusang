package dev.pb.pb_backend.domain.common.service;

import java.util.List;

import dev.pb.pb_backend.domain.common.entity.Location;

public interface LocationService {

	// 전체 테이블 조회
	List<Location.Response> findAllLocations();
	// 레코드(record) 조회
	Location.Response findLocationByLocationId(int locationId);
	// 레코드 생성
	Location.OnlyLocation createLocation(Location.Request request);

	// LocationRepository 쿼리 메서드
	// localName 으로 조회
	List<Location.Response> findByLocalName(String localName);

	List<Location.Response> findByLocalEngName(String localEngName);
	List<Location.Response> findByCityEngName(String cityEngName);
	
}
