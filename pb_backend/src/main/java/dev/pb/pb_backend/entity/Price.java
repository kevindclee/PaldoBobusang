package dev.pb.pb_backend.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//Jpa 설정
@Entity

//Lombok 설정
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Price {

	@Id
	private int price_id;
	
	private int price;
	
	private Date date;
	
	@ManyToMany(mappedBy="prices")
	private List<Vegetable> vegetables;

	@ManyToMany(mappedBy="prices")
	private List<Fruit> fruits;
	
	@ManyToMany(mappedBy="prices")
	private List<Location> locations;
	
}






