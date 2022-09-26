package dev.pb.pb_backend.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
public class Location {

	@Id
	private int location_id;
	private String local_name;
	private String city_name;
	private int country_code;
	
	@ManyToMany(mappedBy="locations")
	private List<Vegetable> vegetables;

	@ManyToMany(mappedBy="locations")
	private List<Fruit> fruits;
	
	@ManyToMany
    @JoinTable(name="LOCATION_PRICE",
        joinColumns=
            @JoinColumn(name="COUNTRY_CODE", referencedColumnName="COUNTRY_CODE"),
        inverseJoinColumns=
            @JoinColumn(name="PRICE_ID", referencedColumnName="PRICE_ID")
        )
	private List<Price> prices;
}







