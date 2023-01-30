package dev.pb.pb_backend.domain.common.service;

import java.util.List;

import dev.pb.pb_backend.domain.common.entity.Fruit;

public interface FruitService {

	// 전체 테이블 조회
	List<Fruit.Response> findAllFruits();
	// 레코드(record) 조회
	Fruit.Response findFruitByCode(int code);
	// 레코드 생성
	Fruit.Response createFruit(Fruit.Request request);

	Fruit.Response updateFruit(Fruit.Request request);
	
	
	// FruitRepository 쿼리 메서드
	// locationId 으로 조회
	List<Fruit.Response> findFruitsByLocationId(int locationId);
	List<Fruit.Response> findFruitsByLocalEngName(String localEngName);
	Fruit.Response findFruitsByItemNameLocalEngName(String itemName, String localEngName);
	
	// harvestDate 으로 조회
	List<Fruit.Response> findFruitsByHarvest();
	
}
