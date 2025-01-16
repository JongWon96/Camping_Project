package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

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
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=2000)
    private String content;

    private Date reviewdate;

    private String img;

    @Column(name = "danger" ,nullable = false)
    private Integer danger;

    private Integer rate;

    @ManyToOne
    @JoinColumn(name = "camping_id")
    private Camping camping;
    

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}