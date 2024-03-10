package com.max.api.v1.dto.result;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponseDTO {
    private int testMark;

    private String testTitle;
}
