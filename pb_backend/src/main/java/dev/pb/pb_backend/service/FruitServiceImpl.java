package dev.pb.pb_backend.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.pb.pb_backend.entity.Fruit;
import dev.pb.pb_backend.repository.FruitRepository;

@Service
public class FruitServiceImpl implements FruitService {
	
	private final Long MILLI_SECONDS_IN_DAY = (long) 86400000;

	@Autowired
	private FruitRepository fruitRepository;
	
	@Override
	public List<Fruit> findAllFruits() {
		return fruitRepository.findAll();
	}

	@Override
	public Fruit findFruitByCode(int code) {
		return fruitRepository.findById(code).orElseThrow(() -> new RuntimeException(String.format("%d 코드에 해당하는 Fruit가 존재하지 않습니다.", code)));
	}

	@Override
	public Fruit createFruit(Fruit newFruit) {
		return fruitRepository.save(newFruit);
	}

	@Override
	public List<Fruit> findFruitsByLocationId(int locationId) {
		return fruitRepository.findByLocationsLocationId(locationId);
	}

	@Override
	public List<Fruit> findFruitsByHarvest(Date curDate) {
		Long curMilliSeconds = curDate.getTime();
		Date plusOneDay = new Date();
		plusOneDay.setTime(curMilliSeconds + MILLI_SECONDS_IN_DAY);
		Date minusOneDay = new Date();
		minusOneDay.setTime(curMilliSeconds - MILLI_SECONDS_IN_DAY);
		return fruitRepository.findByHarvestStartBeforeAndHarvestEndAfter(plusOneDay, minusOneDay);
	}

}









