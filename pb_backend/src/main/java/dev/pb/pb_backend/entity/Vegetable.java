package dev.pb.pb_backend.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Jpa 설정
@Entity

// Lombok 설정
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Vegetable {

	@Id
	private int code;

	@Column(name = "ITEM_NAME", nullable = false)
	private String itemName;

	@Column(name = "KIND_NAME", nullable = false)
	private String kindName;

	@Column(nullable = false)
	private String image;

	@Column(name = "HARVEST_START", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date harvestStart;

	@Column(name = "HARVEST_END", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date harvestEnd;

	@Column(name = "ETC_DETAILS", nullable = false, columnDefinition = "TEXT")
	private String etcDetails;
	
	@ManyToMany
    @JoinTable(name="VEGETABLE_LOCATION",
        joinColumns=
            @JoinColumn(name="CODE", referencedColumnName="CODE"),
        inverseJoinColumns=
            @JoinColumn(name="LOCATION_ID", referencedColumnName="LOCATION_ID")
        )
	private List<Location> locations;
	
	@ManyToMany
    @JoinTable(name="VEGETABLE_PRICE",
        joinColumns=
            @JoinColumn(name="CODE", referencedColumnName="CODE"),
        inverseJoinColumns=
            @JoinColumn(name="PRICE_ID", referencedColumnName="PRICE_ID")
        )
	private List<Price> prices;
	
	// 요청 받을 때 사용할 User Entity의 DTO
	@Setter
	@Getter
	@Builder
	@ToString
	public static class Request {

		@NotNull
		private int code;

		@NotBlank(message = "itemName는 공백('', ' ')이나 Null 지정 불가")
		private String itemName;

		@NotBlank(message = "kindName는 공백('', ' ')이나 Null 지정 불가")
		private String kindName;

		@NotBlank(message = "image는 공백('', ' ')이나 Null 지정 불가")
		private String image;

		@NotNull
		private Date harvestStart;

		@NotNull
		private Date harvestEnd;

		@NotBlank(message = "etcDetails는 공백('', ' ')이나 Null 지정 불가")
		private String etcDetails;

		public static Vegetable toEntity(final Vegetable.Request request) {
			return Vegetable.builder()
					.code(request.getCode())
					.itemName(request.getItemName())
					.kindName(request.getKindName())
					.image(request.getImage())
					.harvestStart(request.getHarvestStart())
					.harvestEnd(request.getHarvestEnd())
					.etcDetails(request.getEtcDetails())
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
		
		private int code;
		private String itemName;
		private String kindName;
		private String image;
		private Date harvestStart;
		private Date harvestEnd;
		private String etcDetails;

		public static Vegetable.Response toResponse(final Vegetable vegetable) {
			return Vegetable.Response.builder()
					.code(vegetable.getCode())
					.itemName(vegetable.getItemName())
					.kindName(vegetable.getKindName())
					.image(vegetable.getImage())
					.harvestStart(vegetable.getHarvestStart())
					.harvestEnd(vegetable.getHarvestEnd())
					.etcDetails(vegetable.getEtcDetails())
					.build();
		}

		public static List<Vegetable.Response> toResponseList(final List<Vegetable> vegetables) {
			List<Vegetable.Response> resList = new ArrayList<>();
			for (Vegetable vegetable : vegetables) {
				resList.add(Vegetable.Response.toResponse(vegetable));
			}
			return resList;
		}
	}
	
}















