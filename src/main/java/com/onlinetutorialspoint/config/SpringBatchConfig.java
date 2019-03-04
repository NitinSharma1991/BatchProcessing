package com.onlinetutorialspoint.config;

import com.onlinetutorialspoint.model.Item;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class SpringBatchConfig {


    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<Item> itemReader,
                   ItemProcessor<Item, Item> itemProcessor,
                   ItemWriter<Item> itemWriter) {
        Step step = stepBuilderFactory.get("BillingStep")
                .<Item, Item>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("BillingJob")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public FlatFileItemReader<Item> itemReader(@Value("${input}") Resource resource) {
        FlatFileItemReader<Item> itemFlatFileItemReader = new FlatFileItemReader<>();
        itemFlatFileItemReader.setResource(resource);
        itemFlatFileItemReader.setName("CSV-Reader");
//        itemFlatFileItemReader.setStrict(false);
        itemFlatFileItemReader.setLinesToSkip(1);
        itemFlatFileItemReader.setLineMapper(lineMapper());

        return itemFlatFileItemReader;
    }

    @Bean
    public LineMapper<Item> lineMapper() {
        DefaultLineMapper defaultLineMapper = new DefaultLineMapper();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id", "name", "category"});

        BeanWrapperFieldSetMapper<Item> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(Item.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return defaultLineMapper;
    }
}
