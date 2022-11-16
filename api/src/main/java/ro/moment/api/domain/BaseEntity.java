package ro.moment.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    @Column(name = "external_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID externalId;

    @PrePersist
    public void setExternalId() {
        if (externalId == null) {
            externalId = UUID.randomUUID();
        }
    }
}
