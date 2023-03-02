package com.example.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Csv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String afCode;
    @Column
    private String afNm;
    @Column
    private String costSource;
    @Column
    private String adType;
    @Column
    private String campaign;
    @Column
    private String subCampaign;
    @Column
    private String device;
    @Column
    private String channel;
    @Column
    private String mediaNm;
    @Column
    private String productNm;
    @Column
    private String brand;
    @Column
    private String exhibitionsNum;
    @Column
    private String departmentNm;
    @Column
    private String keyword;
    @Column
    private String period;
    @Column
    private Long impCnt;
}
