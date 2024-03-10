package com.max.api.v1.controller;//package com.max.controller;

import com.max.api.v1.dto.result.ResultRequestDTO;
import com.max.api.v1.service.ResultService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class ResultController {

    private final ResultService resultService;


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addResultToCandidate/{id}")
    public void addResultToCandidateTest(@PathVariable("id") int candidateId,
                                         @RequestBody ResultRequestDTO resultRequestDTO) {

        resultService.addResultToCandidateTest(candidateId, resultRequestDTO);
    }


    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/updateCandidateResultForTest/{id}")
    public void updateCandidateResultForTest(@PathVariable("id") int candidateId,
                                             @RequestBody ResultRequestDTO resultRequestDTO) {

        resultService.updateCandidateResultForTest(candidateId, resultRequestDTO);
    }
}
