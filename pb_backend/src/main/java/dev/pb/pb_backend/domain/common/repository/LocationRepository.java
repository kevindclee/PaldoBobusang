package dev.pb.pb_backend.domain.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.pb.pb_backend.domain.common.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

	// localName 으로 조회
	List<Location> findByLocalName(String localName);
	
	// localEngName 으로 조회
	List<Location> findByLocalEngName(String localEngName);

	// cityEngName 으로 조회
	List<Location> findByCityEngName(String cityEngName);
	
	// fruit 으로 조회
	List<Location> findByFruitsItemName(String fruitName);

	// vegetable 로 조회
	List<Location> findByVegetablesItemName(String fruitName);
}
