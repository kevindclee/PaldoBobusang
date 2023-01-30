package dev.pb.pb_backend.domain.common.service;

import java.util.List;

import dev.pb.pb_backend.domain.common.entity.Vegetable;

public interface VegetableService {

	// 전체 테이블 조회
	List<Vegetable.Response> findAllVegetables();
	// 레코드(record) 조회
	Vegetable.Response findVegetableByCode(int code);
	// 레코드 생성
	Vegetable.Response createVegetable(Vegetable.Request request);

	Vegetable.Response updateVegetable(Vegetable.Request request);
	
	// VegetableRepository 쿼리 메서드
	// locationId 으로 조회
	List<Vegetable.Response> findVegetablesByLocationId(int locationId);
	List<Vegetable.Response> findVegetablesByLocalEngName(String localEngName);
	Vegetable.Response findVegetablesByItemNameLocalEngName(String itemName, String localEngName);
	
	// harvestDate 으로 조회
	List<Vegetable.Response> findVegetablesByHarvest();
	
}
