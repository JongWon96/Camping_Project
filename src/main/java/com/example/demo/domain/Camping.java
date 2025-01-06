package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "camping")
public class Camping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private Integer category;

    @Column(name = "roomcount")
    private Integer roomcount;

    @Column(name = "contentId")
    private String contentid;

    @Column(name = "facltNm")
    private String facltNm;

    @Column(name = "lineIntro")
    private String lineIntro;

    @Column(name = "intro")
    private String intro;

    @Column(name = "allar")
    private String allar;

    @Column(name = "insrncAt")
    private String insrncAt;

    @Column(name = "trsagntNo")
    private String trsagntNo;

    @Column(name = "bizrno")
    private String bizrno;

    @Column(name = "facltDivNm")
    private String facltDivNm;

    @Column(name = "mangeDivNm")
    private String mangeDivNm;

    @Column(name = "mgcDiv")
    private String mgcDiv;

    @Column(name = "manageSttus")
    private String manageSttus;

    @Column(name = "hvofBgnde")
    private String hvofBgnde;

    @Column(name = "hvofEnddle")
    private String hvofEnddle;

    @Column(name = "featureNm")
    private String featureNm;

    @Column(name = "induty")
    private String induty;

    @Column(name = "lctCl")
    private String lctCl;

    @Column(name = "doNm")
    private String doNm;

    @Column(name = "sigunguNm")
    private String sigunguNm;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "addr2")
    private String addr2;

    @Column(name = "mapX")
    private String mapX;

    @Column(name = "mapY")
    private String mapY;

    @Column(name = "direction")
    private String direction;

    @Column(name = "tel")
    private String tel;

    @Column(name = "homepage")
    private String homepage;

    @Column(name = "resveUrl")
    private String resveUrl;

    @Column(name = "resveCl")
    private String resveCl;

    @Column(name = "manageNmpr")
    private String manageNmpr;

    @Column(name = "gnrlSiteCo")
    private Integer gnrlSiteCo;

    @Column(name = "autoSiteCo")
    private Integer autoSiteCo;

    @Column(name = "glampSiteCo")
    private Integer glampSiteCo;

    @Column(name = "caravSiteCo")
    private Integer caravSiteCo;

    @Column(name = "indvdlCaravSiteCo")
    private Integer indvdlCaravSiteCo;

    @Column(name = "sitedStnc")
    private String sitedStnc;

    @Column(name = "siteMg1Width")
    private String siteMg1Width;

    @Column(name = "siteMg2Width")
    private String siteMg2Width;

    @Column(name = "siteMg3Width")
    private String siteMg3Width;

    @Column(name = "siteMg1Vrticl")
    private String siteMg1Vrticl;

    @Column(name = "siteMg2Vrticl")
    private String siteMg2Vrticl;

    @Column(name = "siteMg3Vrticl")
    private String siteMg3Vrticl;

    @Column(name = "siteMg1Co")
    private Integer siteMg1Co;

    @Column(name = "siteMg2Co")
    private Integer siteMg2Co;

    @Column(name = "siteMg3Co")
    private Integer siteMg3Co;

    @Column(name = "siteBottomCl1")
    private String siteBottomCl1;

    @Column(name = "siteBottomCl2")
    private String siteBottomCl2;

    @Column(name = "siteBottomCl3")
    private String siteBottomCl3;

    @Column(name = "siteBottomCl4")
    private String siteBottomCl4;

    @Column(name = "siteBottomCl5")
    private String siteBottomCl5;

    @Column(name = "tooltip")
    private String tooltip;

    @Column(name = "glampInnerFclty")
    private String glampInnerFclty;

    @Column(name = "caravInnerFclty")
    private String caravInnerFclty;

    @Column(name = "prmisnDe")
    private String prmisnDe;

    @Column(name = "operPdCl")
    private String operPdCl;

    @Column(name = "operDeCl")
    private String operDeCl;

    @Column(name = "trlerAcmpnyAt")
    private String trlerAcmpnyAt;

    @Column(name = "caravAcmpnyAt")
    private String caravAcmpnyAt;

    @Column(name = "toiletCo")
    private Integer toiletCo;

    @Column(name = "swrmCo")
    private Integer swrmCo;

    @Column(name = "wtrplCo")
    private Integer wtrplCo;

    @Column(name = "brazierCl")
    private String brazierCl;

    @Column(name = "sbrsCl")
    private String sbrsCl;

    @Column(name = "sbrsEtc")
    private String sbrsEtc;

    @Column(name = "posblFcltyCl")
    private String posblFcltyCl;

    @Column(name = "posblFcltyEtc")
    private String posblFcltyEtc;

    @Column(name = "clturEventAt")
    private String clturEventAt;

    @Column(name = "clturEvent")
    private String clturEvent;

    @Column(name = "exprnProgrmAt")
    private String exprnProgrmAt;

    @Column(name = "exprnProgrm")
    private String exprnProgrm;

    @Column(name = "extshrCo")
    private Integer extshrCo;

    @Column(name = "frprvtWrppCo")
    private Integer frprvtWrppCo;

    @Column(name = "frprvtSandCo")
    private Integer frprvtSandCo;

    @Column(name = "fireSensorCo")
    private Integer fireSensorCo;

    @Column(name = "themaEnvrnCl")
    private String themaEnvrnCl;

    @Column(name = "eqpmnLendCl")
    private String eqpmnLendCl;

    @Column(name = "animalCmgCl")
    private String animalCmgCl;

    @Column(name = "tourEraCl")
    private String tourEraCl;

    @Column(name = "firstImageUrl")
    private String firstImageUrl;

    @CreatedDate
    @Column(name = "createdtime", updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "modifiedtime")
    private LocalDateTime modifiedTime;
}
