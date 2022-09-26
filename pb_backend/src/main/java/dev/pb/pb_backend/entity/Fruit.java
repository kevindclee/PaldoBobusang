package dev.pb.pb_backend.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
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
public class Fruit {

	@Id
	private int code;

	@Column(nullable = false)
	private String item_name;

	@Column(nullable = false)
	private String kind_name;

	@Column(nullable = false)
	private String image;

	@Column(nullable = false)
	private Date harvest_start;

	@Column(nullable = false)
	private Date harvest_end;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String etc_details;

	@Column(nullable = false)
	private int brix;
	
	@ManyToMany
    @JoinTable(name="FRUIT_LOCATION",
        joinColumns=
            @JoinColumn(name="CODE", referencedColumnName="CODE"),
        inverseJoinColumns=
            @JoinColumn(name="LOCATION_ID", referencedColumnName="LOCATION_ID")
        )
	private List<Location> locations;
	

	@ManyToMany
    @JoinTable(name="FRUIT_PRICE",
        joinColumns=
            @JoinColumn(name="CODE", referencedColumnName="CODE"),
        inverseJoinColumns=
            @JoinColumn(name="PRICE_ID", referencedColumnName="PRICE_ID")
        )
	private List<Price> prices;
	
}
