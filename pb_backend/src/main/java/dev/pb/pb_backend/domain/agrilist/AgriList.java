package dev.pb.pb_backend.domain.agrilist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pb.pb_backend.domain.common.service.FruitService;
import dev.pb.pb_backend.domain.common.service.VegetableService;

@RestController
@RequestMapping(path="/AgriList")
public class AgriList {

	@Autowired
	private FruitService fruitService;
	
	@Autowired
	private VegetableService vegetableService;
	
	@GetMapping
	public List<Object> findItems() {
//		Date today = new Date().
		List<Object> resList = new ArrayList<Object>();
		
		
		return resList;
	}
	
}
