package com.max.api.v1.dto.test;

import com.max.api.v1.dto.domain.DomainRequestDTO;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = "domainSet")
@NoArgsConstructor
@AllArgsConstructor
public class TestRequestDTO {

    private String title;

    private String description;

    private Set<DomainRequestDTO> domainSet = new HashSet<>();
}
