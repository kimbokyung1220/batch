package com.example.batch.dto;

import lombok.*;

import javax.persistence.Column;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CsvDto {
    private String afCode;
    private String afNm;
    private String costSource;
    private String adType;
    private String campaign;
    private String subCampaign;
    private String device;
    private String channel;
    private String mediaNm;
    private String productNm;
    private String brand;
    private String brandNum;
    private String departmentNm;
    private String keyword;
    private String period;
    private Long impCnt;
}
