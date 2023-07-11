package example.batch_study.batch8_itemWriter.payPaging;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class PayApplicationRunner implements ApplicationRunner {
    private final PayRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 50; i++) {
            Pay pay = new Pay();
            repository.save(pay);
        }
    }
}
