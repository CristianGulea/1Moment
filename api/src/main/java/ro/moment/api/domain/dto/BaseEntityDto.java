package ro.moment.api.domain.dto;

import lombok.Getter;
import lombok.Setter;
import ro.moment.api.domain.BaseEntity;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class BaseEntityDto {
    private Long id;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private UUID externalId;

    public BaseEntityDto(Long id, LocalDate createdDate, LocalDate updatedDate, UUID externalId) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.externalId = externalId;
    }

    public BaseEntityDto(BaseEntity entity) {
        this.id = entity.getId();
        this.createdDate = entity.getCreatedDate();
        this.updatedDate = entity.getUpdatedDate();
        this.externalId = entity.getExternalId();
    }

    public BaseEntityDto() {
    }
}
