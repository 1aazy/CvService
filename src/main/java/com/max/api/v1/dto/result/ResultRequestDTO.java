package com.max.api.v1.dto.result;

import com.max.api.v1.dto.test.TestNameDTO;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultRequestDTO {

    private int testMark;

    private TestNameDTO test;
}
