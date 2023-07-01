package example.batch_study.batch2_domain;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Date;

//@Component
@RequiredArgsConstructor
public class JobParameterTest implements ApplicationRunner {
    private final Job job;
    private final JobLauncher jobLauncher;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "heeseung")
                .addLong("seq", 1L)
                .addDate("date", new Date())
                .addDouble("age", 25.5)
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
