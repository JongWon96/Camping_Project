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

    @Column(name = "facltnm")
    private String facltNm;

    @Column(name = "lineintro", length=2000)
    private String lineIntro;

    @Column(name = "intro", length=2000)
    private String intro;

    @Column(name = "allar")
    private String allar;

    @Column(name = "insrncat")
    private String insrncAt;

    @Column(name = "trsagntno")
    private String trsagntNo;

    @Column(name = "bizrno")
    private String bizrno;

    @Column(name = "facltdivnm")
    private String facltDivNm;

    @Column(name = "mangedivnm")
    private String mangeDivNm;

    @Column(name = "mgcdiv")
    private String mgcDiv;

    @Column(name = "managesttus")
    private String manageSttus;

    @Column(name = "hvofbgnde")
    private String hvofBgnde;

    @Column(name = "hvofenddle")
    private String hvofEnddle;

    @Column(name = "featurenm", length=2000)
    private String featureNm;

    @Column(name = "induty")
    private String induty;

    @Column(name = "lctcl")
    private String lctCl;

    @Column(name = "donm")
    private String doNm;

    @Column(name = "sigungunm")
    private String sigunguNm;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "addr2")
    private String addr2;

    @Column(name = "mapx")
    private String mapX;

    @Column(name = "mapy")
    private String mapY;

    @Column(name = "direction")
    private String direction;

    @Column(name = "tel")
    private String tel;

    @Column(name = "homepage")
    private String homepage;

    @Column(name = "resveurl", length=1000)
    private String resveUrl;

    @Column(name = "resvecl")
    private String resveCl;

    @Column(name = "managenmpr")
    private String manageNmpr;

    @Column(name = "gnrlsiteco")
    private Integer gnrlSiteCo;

    @Column(name = "autositeco")
    private Integer autoSiteCo;

    @Column(name = "glampsiteco")
    private Integer glampSiteCo;

    @Column(name = "caravsiteco")
    private Integer caravSiteCo;

    @Column(name = "indvdlcaravsiteco")
    private Integer indvdlCaravSiteCo;

    @Column(name = "sitedstnc")
    private String sitedStnc;

    @Column(name = "sitemg1width")
    private String siteMg1Width;

    @Column(name = "sitemg2width")
    private String siteMg2Width;

    @Column(name = "sitemg3width")
    private String siteMg3Width;

    @Column(name = "sitemg1vrticl")
    private String siteMg1Vrticl;

    @Column(name = "sitemg2vrticl")
    private String siteMg2Vrticl;

    @Column(name = "sitemg3vrticl")
    private String siteMg3Vrticl;

    @Column(name = "sitemg1co")
    private Integer siteMg1Co;

    @Column(name = "sitemg2co")
    private Integer siteMg2Co;

    @Column(name = "sitemg3co")
    private Integer siteMg3Co;

    @Column(name = "sitebottomcl1")
    private String siteBottomCl1;

    @Column(name = "sitebottomcl2")
    private String siteBottomCl2;

    @Column(name = "sitebottomcl3")
    private String siteBottomCl3;

    @Column(name = "sitebottomcl4")
    private String siteBottomCl4;

    @Column(name = "sitebottomcl5")
    private String siteBottomCl5;

    @Column(name = "tooltip")
    private String tooltip;

    @Column(name = "glampinnerfclty")
    private String glampInnerFclty;

    @Column(name = "caravinnerfclty")
    private String caravInnerFclty;

    @Column(name = "prmisnde")
    private String prmisnDe;

    @Column(name = "operpdcl")
    private String operPdCl;

    @Column(name = "operdecl")
    private String operDeCl;

    @Column(name = "trleracmpnyat")
    private String trlerAcmpnyAt;

    @Column(name = "caravacmpnyat")
    private String caravAcmpnyAt;

    @Column(name = "toiletco")
    private Integer toiletCo;

    @Column(name = "swrmco")
    private Integer swrmCo;

    @Column(name = "wtrplco")
    private Integer wtrplCo;

    @Column(name = "braziercl")
    private String brazierCl;

    @Column(name = "sbrscl")
    private String sbrsCl;

    @Column(name = "sbrsetc")
    private String sbrsEtc;

    @Column(name = "posblfcltycl")
    private String posblFcltyCl;

    @Column(name = "posblfcltyetc")
    private String posblFcltyEtc;

    @Column(name = "cltureventat")
    private String clturEventAt;

    @Column(name = "clturevent")
    private String clturEvent;

    @Column(name = "exprnprogrmat")
    private String exprnProgrmAt;

    @Column(name = "exprnprogrm")
    private String exprnProgrm;

    @Column(name = "extshrco")
    private Integer extshrCo;

    @Column(name = "frprvtwrppco")
    private Integer frprvtWrppCo;

    @Column(name = "frprvtsandco")
    private Integer frprvtSandCo;

    @Column(name = "firesensorco")
    private Integer fireSensorCo;

    @Column(name = "themaenvrncl")
    private String themaEnvrnCl;

    @Column(name = "eqpmnlendcl")
    private String eqpmnLendCl;

    @Column(name = "animalcmgcl")
    private String animalCmgCl;

    @Column(name = "toureracl")
    private String tourEraCl;

    @Column(name = "firstimageurl", length=1000)
    private String firstImageUrl;

    @CreatedDate
    @Column(name = "createdtime", updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "modifiedtime")
    private LocalDateTime modifiedTime;
}