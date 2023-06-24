package example.batch_study.batch1;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

//@Configuration
@RequiredArgsConstructor
public class HelloJobConfiguration {
    // job을 생성하는 빌더 팩토리로, job을 생성할 때 new job가 아니라 jobBuilderFactory을 사용해 쉽게 job을 생성한다.
    private final JobBuilderFactory jobBuilderFactory;

    // JobBuilderFactory와 같은 맥락이다.
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        // helloJob을 생성
        return jobBuilderFactory.get("helloJob")
                .start(helloStep1()) // job의 첫 번째 step으로 helloStep1을 등록
                .next(helloStep2())// helloStep1을 수행한 이후 다음 Step을 등록
                .build();
    }

    @Bean
    public Step helloStep1() {
        // helloStep1을 생성
        return stepBuilderFactory.get("helloStep1")
                // Step의 작업내용 Tasklet을 정의 -> 단일 메소드만 존재하므로 람다식으로도 가능
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        // Step은 기본적으로 Tasklet을 무한 반복시킨다.
                        // 따라서 null이나 RepeatStatus.FINISHED를 반환해주어야 1번만 Tasklet을 실행한다.
                        System.out.println("helloStep1");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step helloStep2() {
        return stepBuilderFactory.get("helloStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("helloStep2");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
}
