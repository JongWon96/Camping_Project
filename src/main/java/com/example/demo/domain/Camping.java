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
    private Long id;

    
    @Column(name = "category")
    private String category;

    @Column(name = "roomcount")
    private Integer roomcount;

    @Column(name = "facltnm")
    private String facltnm;

    @Column(name = "lineintro", length=2000)
    private String lineintro;

    @Column(name = "intro", length=2000)
    private String intro;

    @Column(name = "allar")
    private String allar;

    @Column(name = "insrncat")
    private String insrncat;

    @Column(name = "trsagntno")
    private String trsagntno;

    @Column(name = "bizrno")
    private String bizrno;

    @Column(name = "facltdivnm")
    private String facltdivnm;

    @Column(name = "mangedivnm")
    private String mangedivnm;

    @Column(name = "mgcdiv")
    private String mgcdiv;

    @Column(name = "managesttus")
    private String managesttus;

    @Column(name = "hvofbgnde")
    private String hvofbgnde;

    @Column(name = "hvofenddle")
    private String hvofenddle;

    @Column(name = "featurenm", length=2000)
    private String featurenm;

    @Column(name = "induty")
    private String induty;

    @Column(name = "lctcl")
    private String lctcl;

    @Column(name = "donm")
    private String donm;

    @Column(name = "sigungunm")
    private String sigungunm;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "addr2")
    private String addr2;

    @Column(name = "mapx")
    private String mapx;

    @Column(name = "mapy")
    private String mapy;

    @Column(name = "direction", length=1000)
    private String direction;

    @Column(name = "tel")
    private String tel;

    @Column(name = "homepage")
    private String homepage;

    @Column(name = "resveurl", length=1000)
    private String resveurl;

    @Column(name = "resvecl")
    private String resvecl;

    @Column(name = "managenmpr")
    private String managenmpr;

    @Column(name = "gnrlsiteco")
    private Integer gnrlsiteco;

    @Column(name = "autositeco")
    private Integer autositeco;

    @Column(name = "glampsiteco")
    private Integer glampsiteco;

    @Column(name = "caravsiteco")
    private Integer caravsiteco;

    @Column(name = "indvdlcaravsiteco")
    private Integer indvdlcaravsiteco;

    @Column(name = "sitedstnc")
    private String sitedstnc;

    @Column(name = "sitemg1width")
    private String sitemg1width;

    @Column(name = "sitemg2width")
    private String sitemg2width;

    @Column(name = "sitemg3width")
    private String sitemg3width;

    @Column(name = "sitemg1vrticl")
    private String sitemg1vrticl;

    @Column(name = "sitemg2vrticl")
    private String sitemg2vrticl;

    @Column(name = "sitemg3vrticl")
    private String sitemg3vrticl;

    @Column(name = "sitemg1co")
    private Integer sitemg1co;

    @Column(name = "sitemg2co")
    private Integer sitemg2co;

    @Column(name = "sitemg3co")
    private Integer sitemg3co;

    @Column(name = "sitebottomcl1")
    private String sitebottomcl1;

    @Column(name = "sitebottomcl2")
    private String sitebottomcl2;

    @Column(name = "sitebottomcl3")
    private String sitebottomcl3;

    @Column(name = "sitebottomcl4")
    private String sitebottomcl4;

    @Column(name = "sitebottomcl5")
    private String sitebottomcl5;

    @Column(name = "tooltip",length=1000)
    private String tooltip;

    @Column(name = "glampinnerfclty")
    private String glampinnerfclty;

    @Column(name = "caravinnerfclty")
    private String caravinnerfclty;

    @Column(name = "prmisnde")
    private String prmisnde;

    @Column(name = "operpdcl")
    private String operpdcl;

    @Column(name = "operdecl")
    private String operdecl;

    @Column(name = "trleracmpnyat")
    private String trleracmpnyat;

    @Column(name = "caravacmpnyat")
    private String caravacmpnyat;

    @Column(name = "toiletco")
    private Integer toiletco;

    @Column(name = "swrmco")
    private Integer swrmco;

    @Column(name = "wtrplco")
    private Integer wtrplco;

    @Column(name = "braziercl")
    private String braziercl;

    @Column(name = "sbrscl")
    private String sbrscl;

    @Column(name = "sbrsetc")
    private String sbrsetc;

    @Column(name = "posblfcltycl")
    private String posblfcltycl;

    @Column(name = "posblfcltyetc")
    private String posblfcltyetc;

    @Column(name = "cltureventat")
    private String cltureventat;

    @Column(name = "clturevent")
    private String clturevent;

    @Column(name = "exprnprogrmat")
    private String exprnprogrmat;

    @Column(name = "exprnprogrm")
    private String exprnprogrm;

    @Column(name = "extshrco")
    private Integer extshrco;

    @Column(name = "frprvtwrppco")
    private Integer frprvtwrppco;

    @Column(name = "frprvtsandco")
    private Integer frprvtsandco;

    @Column(name = "firesensorco")
    private Integer firesensorco;

    @Column(name = "themaenvrncl")
    private String themaenvrncl;

    @Column(name = "eqpmnlendcl")
    private String eqpmnlendcl;

    @Column(name = "animalcmgcl")
    private String animalcmgcl;

    @Column(name = "toureracl")
    private String toureracl;

    @Column(name = "firstimageurl", length=1000)
    private String firstimageurl;

    @CreatedDate
    @Column(name = "createdtime", updatable = false)
    private LocalDateTime createdtime;

    @LastModifiedDate
    @Column(name = "modifiedtime")
    private LocalDateTime modifiedtime;
    
}
