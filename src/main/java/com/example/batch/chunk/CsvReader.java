//package com.example.batch.chunk;
//
//import com.example.batch.entity.Csv;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//@Configuration
//@RequiredArgsConstructor
//public class CsvReader {
//    /* file read */
//    @Bean
//    public FlatFileItemReader<Csv> csvFileReader() {
//
//        // FlatFileItemReader 객체 생성
//        FlatFileItemReader<Csv> flatFileItemReader = new FlatFileItemReader<>();
//        // 파일 경로
//        flatFileItemReader.setResource(new ClassPathResource("/file/sample_encoding.csv"));
//        //맨 윗줄(header)는 읽지 않고 skip [맨 윗줄이 제목 줄인 경우 사용]
//        flatFileItemReader.setLinesToSkip(1);
//        // encoding
//        flatFileItemReader.setEncoding("UTF-8");
//
//        /*  Csv파일을 VO Class로 바인딩 => [데이터를 내부적으로 LineMapper을 통해 Mapping] */
//        DefaultLineMapper<Csv> defaultLineMapper = new DefaultLineMapper<>();
//
//        /* delimitedLineTokenizer : setNames를 통해 각각의 데이터의 이름 설정 */
////        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",");
//        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_COMMA); // rbnqnZZZ
//        // 각각의 데이터 이름 설정 -> 엔티티 필드의 이름과 동일하게 설정하면 됨
//        delimitedLineTokenizer.setNames("afCode", "afNm", "costSource", "adType", "campaign", "subCampaign", "device", "channel", "mediaNm", "productNm", "brand", "brandNum", "departmentNm", "keyword", "period", "impCnt");
//        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
//
//        /* beanWrapperFieldSetMapper : Tokenizer에서 가지고온 데이터들을 VO로 바인드하는 역할 */
//        BeanWrapperFieldSetMapper<Csv> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        beanWrapperFieldSetMapper.setTargetType(Csv.class);
//
//        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
//
//        /* lineMapper 지정 */
//        flatFileItemReader.setLineMapper(defaultLineMapper);
//
//        return flatFileItemReader;
//    }
//
//}
