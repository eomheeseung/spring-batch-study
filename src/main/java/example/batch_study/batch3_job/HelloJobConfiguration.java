package example.batch_study.batch3_job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
//@Configuration
public class HelloJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("job1")
                .start(step1())
                // 커스텀 방식
                // .validator(new CustomJobParametersValidator())

                // defaultValidator
                .validator(new DefaultJobParametersValidator(new String[]{"name", "date"}, new String[]{"count"}))
                .build();
    }

    @Bean
    public Job helloJob2() {
        return jobBuilderFactory.get("job1")
                .start(step1())
                // .incrementer(new RunIdIncrementer())
                .incrementer(new CustomJobParametersIncrementer())
                .build();
    }

    /**
     * 기본적으로 job은 실패할 경우 재시작이 가능 (기본값 true)
     * 해당 옵션을 false로 주면 job의 재시작으로 지원하지 않음.
     * @return
     */
    /*@Bean
    public Job helloJob() {
        return jobBuilderFactory.get("Job1")
                .start(step1())
                // preventRestart() : job을 재시작하지 않음.
                .preventRestart().build();
    }*/

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step1 has executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .tasklet((StepContribution, ChunkContext) -> {
                    System.out.println("step1 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
