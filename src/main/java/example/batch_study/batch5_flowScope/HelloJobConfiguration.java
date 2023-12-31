package example.batch_study.batch5_flowScope;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

//@Configuration
@Slf4j
@RequiredArgsConstructor
public class HelloJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("job")
                .start(step1())
                    .on("COMPLETED")
                    .to(step2())
                    .on("FAILED")
                    .to(step3())
                .from(step1())
                    .on("FAILED")
                    .end()
                .from(step2())
                    .on("COMPLETED")
                    .to(step4())
                    .next(step5())
                .end() //SimpleFlow 객체 생성
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step1");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step2");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step3");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step4");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step5");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    /*@Bean
    public Job helloJob() {
        return jobBuilderFactory.get("job")
                .start(step1())
                    .on("COMPLETED")
                    .stop()
                .from(step1())
                    .on("*")
                    .to(step2())
                    .on("FAILED")
                    .stopAndRestart(step3())
                .end()// SimpleFlow 객체 생성
                .build();
    }*/

    /*@Bean
    public Job helloJob() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .on("FAILED")
                .to(step2())
                .on("PASS")
                .stop()
                .end() // SimpleFlow 객체 생성
                .build();
    }*/
}
