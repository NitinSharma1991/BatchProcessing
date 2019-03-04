package com.onlinetutorialspoint.config;

import com.onlinetutorialspoint.model.Item;
import com.onlinetutorialspoint.model.Item2;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class SpringBatchConfig {
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    ItemProcessor<Item2, Item2> itemProcessor1;
    @Autowired
    ItemWriter<Item2> itemWriter;
    @Value("${input1}")
    private String path;

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("BillingStep2")
                .<Item2, Item2>chunk(100)
                .reader(itemReader1())
                .processor(itemProcessor1)
                .writer(itemWriter)
                .build();

    }


    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<Item> itemReader,
                   ItemProcessor<Item, Item> itemProcessor,
                   ItemWriter<Item> itemWriter) {
        Step step1 = stepBuilderFactory.get("BillingStep1")
                .<Item, Item>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
        /*Step step2 = stepBuilderFactory.get("BillingStep2")
                .<Item, Item>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();*/

        return jobBuilderFactory.get("BillingJob")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .next(step2())
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
    public FlatFileItemReader<Item2> itemReader1() {
        FlatFileItemReader<Item2> itemFlatFileItemReader = new FlatFileItemReader<>();
        Resource resource = null;
        resource = new FileSystemResource(path);
        itemFlatFileItemReader.setResource(resource);
        itemFlatFileItemReader.setName("CSV-Reader");
//        itemFlatFileItemReader.setStrict(false);
        itemFlatFileItemReader.setLinesToSkip(1);
        itemFlatFileItemReader.setLineMapper(lineMapper1());

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

    @Bean
    public LineMapper<Item2> lineMapper1() {
        DefaultLineMapper defaultLineMapper = new DefaultLineMapper();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id", "name", "category"});

        BeanWrapperFieldSetMapper<Item2> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(Item2.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return defaultLineMapper;
    }
}
