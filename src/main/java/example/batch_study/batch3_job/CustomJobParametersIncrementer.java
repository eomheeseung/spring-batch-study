package example.batch_study.batch3_job;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomJobParametersIncrementer implements JobParametersIncrementer {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMdd-hh:mm:ss");

    @Override
    public JobParameters getNext(JobParameters parameters) {
        String id = FORMAT.format(new Date());

        return new JobParametersBuilder()
                .addString("custom", id)
                .toJobParameters();
    }
}
