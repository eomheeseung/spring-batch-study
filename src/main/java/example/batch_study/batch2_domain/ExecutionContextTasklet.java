package example.batch_study.batch2_domain;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

public class ExecutionContextTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext jobExecutionContext = contribution
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        ExecutionContext stepExecutionContext = contribution
                .getStepExecution()
                .getExecutionContext();

        ExecutionContext jobExecutionContext2 = chunkContext
                .getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();

        ExecutionContext stepExecutionContext2 = chunkContext
                .getStepContext()
                .getStepExecution()
                .getExecutionContext();

        return RepeatStatus.FINISHED;
    }
}
