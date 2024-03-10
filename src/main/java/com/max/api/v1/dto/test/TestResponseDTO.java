package com.max.api.v1.dto.test;

import com.max.api.v1.dto.domain.DomainResponseDTO;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = "domainSet")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResponseDTO {
    private String title;

    private String description;

    private Set<DomainResponseDTO> domainSet;
}
