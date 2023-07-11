package example.batch_study.batch8_itemWriter.payPaging;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Pay {
    @Id
    @GeneratedValue
    private Long id;

    private boolean successStatus;

    public Pay() {
        this.successStatus = false;
    }

    public void success() {
        successStatus = true;
    }
}
