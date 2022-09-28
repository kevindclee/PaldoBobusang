package dev.pb.pb_backend.service;

import java.util.Date;
import java.util.List;

import dev.pb.pb_backend.entity.Fruit;

public interface FruitService {

	// 전체 테이블 조회
	List<Fruit> findAllFruits();
	// 레코드(record) 조회
	Fruit findFruitByCode(int code);
	// 레코드 생성
	Fruit createFruit(Fruit newFruit);
	
	// FruitRepository 쿼리 메서드
	// locationId 으로 조회
	List<Fruit> findFruitsByLocationId(int locationId);
	// harvestDate 으로 조회
	List<Fruit> findFruitsByHarvest(Date curDate);
	
}
