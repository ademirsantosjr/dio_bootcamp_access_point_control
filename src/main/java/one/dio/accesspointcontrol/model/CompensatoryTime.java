package one.dio.accesspointcontrol.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class CompensatoryTime {
    
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public class CompensatoryTimeId implements Serializable {
        private long compensatoryTimeId;
        private long transactionId;
        private long userId;
    }

    @EmbeddedId
    private CompensatoryTimeId id;

    private LocalDateTime dateWorked;
    
    private BigDecimal hoursWorked;

    private BigDecimal balance;
}
