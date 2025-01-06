package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Builder
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@ToString
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recomandation")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}

