package com.max.api.v1.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDTO {

    private String name;

    private String surname;

    private String middleName;

    private String description;
}
