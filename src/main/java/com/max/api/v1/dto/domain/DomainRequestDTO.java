package com.max.api.v1.dto.domain;

import com.max.api.v1.dto.test.TestRequestDTO;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainRequestDTO {

    private String title;

    private String description;

    private Set<TestRequestDTO> testSet = new HashSet<>();
}
