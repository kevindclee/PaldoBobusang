package dev.pb.pb_backend.service;

import java.util.Date;
import java.util.List;

import dev.pb.pb_backend.entity.Vegetable;

public interface VegetableService {

	// 전체 테이블 조회
	List<Vegetable> findAllVegetables();
	// 레코드(record) 조회
	Vegetable findVegetableByCode(int code);
	// 레코드 생성
	Vegetable createVegetable(Vegetable newFruit);
	
	// VegetableRepository 쿼리 메서드
	// locationId 으로 조회
	List<Vegetable> findVegetablesByLocationId(int locationId);
	// harvestDate 으로 조회
	List<Vegetable> findVegetablesByHarvest(Date curDate);
	
}
