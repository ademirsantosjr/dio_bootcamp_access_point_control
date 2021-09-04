package one.dio.accesspointcontrol.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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
public class Transaction {
    
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public class TransactionId implements Serializable {
        private long transactionId;
        private long userId;
    }

    @EmbeddedId
    private TransactionId id;

    private LocalDateTime enterDate;

    private LocalDateTime exitDate;

    private BigDecimal period;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Calendar calendar;
}
