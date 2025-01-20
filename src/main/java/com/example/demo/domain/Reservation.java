package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@Setter
@ToString
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int person;

    private Date checkin;

    private Date checkout;

    private String bottom;

    private Date createddate;	// 예약 생성일자

	@ColumnDefault("1")
	private String result;	// 예약/예약완료 처리 여부

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "hasreview")
    private Boolean hasreview;


    @OneToMany(mappedBy="reservation", fetch=FetchType.EAGER)
	@ToString.Exclude
	private List<ReservationDetail> reservationDetail = new ArrayList<ReservationDetail>();

}

