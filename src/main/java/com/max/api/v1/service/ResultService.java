package com.max.api.v1.service;

import com.max.api.v1.dto.result.ResultRequestDTO;
import com.max.api.v1.model.Candidate;
import com.max.api.v1.model.CandidateTest;
import com.max.api.v1.model.Result;
import com.max.api.v1.repositories.CandidateRepository;
import com.max.api.v1.repositories.ResultRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ResultService {

    private final ResultRepository resultRepository;
    private final CandidateRepository candidateRepository;

    @Transactional
    public void addResultToCandidateTest(int candidateId, ResultRequestDTO resultRequestDTO) {

        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() ->
                new EntityNotFoundException("Candidate with '" + candidateId + "' id not found."));

        Result result = new Result();
        result.setDate(new Date());
        result.setTestMark(resultRequestDTO.getTestMark());

        for (CandidateTest candidateTest : candidate.getCandidateTests()) {
            if (candidateTest.getTest().getTitle().equals(resultRequestDTO.getTest().getTitle())) {
                candidateTest.setResult(result);
                result.setCandidateTest(candidateTest);
            }
        }

        resultRepository.save(result);
    }

    @Transactional
    public void updateCandidateResultForTest(int candidateId, ResultRequestDTO resultRequestDTO) {
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() ->
                new EntityNotFoundException("Candidate with '" + candidateId + "' id not found."));


        for (CandidateTest candidateTest : candidate.getCandidateTests()) {
            if (candidateTest.getTest().getTitle().equals(resultRequestDTO.getTest().getTitle())) {

                Result result = candidateTest.getResult();
                result.setTestMark(resultRequestDTO.getTestMark());
                result.setDate(new Date());

                resultRepository.save(result);
            }
        }
    }
}
