package example.batch_study.batch5;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

//@Configuration
@RequiredArgsConstructor
public class HelloJobConfiguration3 {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("job")
                .start(step())
                // step이 완료되면 decider 실행
                .next(decider())
                // decider의 반환값에 따른 분기
                .from(decider())
                    .on("ODD").to(oddStep())
                .from(decider())
                    .on("EVEN").to(evenStep())
                .end() // SimpleFlow 객체 생성
                .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new CustomDecider();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step completed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory.get("evenStep")
                .tasklet((StepContribution, ChunkContext) -> {
                    System.out.println("even completed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step oddStep() {
        return stepBuilderFactory.get("oddStep")
                .tasklet((StepContribution, ChunkContext) -> {
                    System.out.println("odd completed");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
