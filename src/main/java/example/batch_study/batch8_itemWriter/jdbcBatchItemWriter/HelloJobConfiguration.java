package example.batch_study.batch8_itemWriter.jdbcBatchItemWriter;

import example.batch_study.customer.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//@Configuration
@RequiredArgsConstructor
@Slf4j
public class HelloJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step")
                .<Customer, Customer>chunk(chunkSize)
                .reader(customItemReader())
                .writer(customItemWriter())
                .build();
    }

    @Bean
    public ItemReader<Customer> customItemReader() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("age", 25);

        return new JpaPagingItemReaderBuilder<Customer>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("select c from Customer c where c.age >= :age order by c.id")
                .parameterValues(parameters)
                .build();
    }

    @Bean
    public ItemWriter<Customer> customItemWriter() {
        return new JdbcBatchItemWriterBuilder<Customer>()
                .dataSource(dataSource)
                .sql("insert into customer2 values(:id, :age, :name)")
                .beanMapped()
                .build();
    }
}
