package dev.pb.pb_backend.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.pb.pb_backend.projection.PriceLocationIdProjection;
import dev.pb.pb_backend.projection.PriceProjection;
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
@ToString(exclude={"locations", "fruits", "vegetables"})
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRICE_ID")
	private int priceId;
	
	private int priceValue;
	
	@Column(name = "PRICE_DATE")
	@Temporal(TemporalType.DATE)
	private Date priceDate;
	
	@ManyToOne
	@JoinColumn(name = "LOCATION_ID")
	@JsonIgnore
	private Location location;

	@ManyToOne
	@JoinColumn(name = "VEGETABLE_CODE")
	@JsonIgnore
	private Vegetable vegetable;

	@ManyToOne
	@JoinColumn(name = "FRUIT_CODE")
	@JsonIgnore
	private Fruit fruit;
	
	// 요청 받을 때 사용할 User Entity의 DTO
	@Setter
	@Getter
	@Builder
	@ToString
	public static class Request {

		@NotNull
		private int priceId;

		@NotNull
		private int priceValue;
		
		@NotNull
		private Date priceDate;
		
		Location location;
		Vegetable vegetable;
		Fruit fruit;

		public static Price toEntity(final Price.Request request) {
			return Price.builder()
					.priceId(request.getPriceId())
					.priceValue(request.getPriceValue())
					.priceDate(request.getPriceDate())
					.location(request.getLocation())
					.vegetable(request.getVegetable())
					.fruit(request.getFruit())
					.build();
		}
		
	}

	// 서버가 응답할 때 사용할 User Entity의 DTO
	@Setter
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {

		private int priceValue;
		private Date priceDate;
		

		public static Price.Response toResponse(final PriceProjection price) {
			return Price.Response.builder()
					.priceValue(price.getPriceValue())
					.priceDate(price.getPriceDate()).build();
		}

		public static List<Price.Response> toResponseList(final List<PriceProjection> prices) {
			List<Price.Response> resList = new ArrayList<>();
			for (PriceProjection price : prices) {
				resList.add(Price.Response.toResponse(price));
			}
			return resList;
		}
	}
}






