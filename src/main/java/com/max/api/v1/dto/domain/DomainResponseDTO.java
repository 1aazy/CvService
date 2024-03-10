package com.max.api.v1.dto.domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainResponseDTO {

    private String title;

    private String description;
}
