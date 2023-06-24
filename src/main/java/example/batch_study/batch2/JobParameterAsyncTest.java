package example.batch_study.batch2;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.Date;

//@Component
@RequiredArgsConstructor
public class JobParameterAsyncTest implements ApplicationRunner {
    private final BasicBatchConfigurer basicBatchConfigurer;
    private final Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SimpleJobLauncher simpleJobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "heeseung")
                .addDate("date", new Date())
                .addLong("seq", 1L)
                .addDouble("age", 25.5)
                .toJobParameters();

        simpleJobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        simpleJobLauncher.run(job, jobParameters);
    }
}
