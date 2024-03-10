package com.max.api.v1.service;

import com.max.api.v1.dto.test.TestNameDTO;
import com.max.api.v1.model.BinaryContent;
import com.max.api.v1.model.Candidate;
import com.max.api.v1.model.CandidateTest;
import com.max.api.v1.model.Test;
import com.max.api.v1.repositories.CandidateRepository;
import com.max.api.v1.repositories.TestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final TestRepository testRepository;

    @Transactional
    public void saveCandidate(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    public Candidate getCandidateById(int candidateId) {

        return candidateRepository.findById(candidateId).orElseThrow(() ->
                new EntityNotFoundException("Candidate with id '" + candidateId + "' not found."));
    }

    @Transactional
    public void deleteCandidateById(int candidateId) {
        candidateRepository.deleteById(candidateId);
    }

    @Transactional
    public void uploadPhoto(int candidateId, byte[] photo) {

        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() ->
                new EntityNotFoundException("Candidate with id '" + candidateId + "' not found."));

        candidate.setPhoto(new BinaryContent(photo));

        candidateRepository.save(candidate);
    }

    @Transactional
    public void uploadCv(int candidateId, byte[] cv) {

        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() ->
                new EntityNotFoundException("Candidate with id '" + candidateId + "' not found."));

        candidate.setCv(new BinaryContent(cv));

        candidateRepository.save(candidate);
    }

    public List<Candidate> getPaginationCandidates(int page, int candidatePerPage) {
        return candidateRepository.findAll(PageRequest.of(page, candidatePerPage)).getContent();
    }

    public List<Candidate> getSortedByNamePaginationTests(int page, int testPerPage) {
        return candidateRepository.findAll(PageRequest.of(page, testPerPage, Sort.by("name"))).getContent();
    }

    public List<Candidate> getSortedByNameTests() {
        return candidateRepository.findAll(Sort.by("name"));
    }

    public List<Candidate> getAllTest() {
        return candidateRepository.findAll();
    }

    @Transactional
    public void addTestToCandidate(int candidateId, TestNameDTO requestDTO) {

        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() ->
                new EntityNotFoundException("Candidate with id '" + candidateId + "' not found."));

        Test test = testRepository.findByTitle(requestDTO.getTitle()).orElseThrow(() ->
                new EntityNotFoundException("Test with test '" + candidateId + "' not found."));

        CandidateTest candidateTest = new CandidateTest();

        candidateTest.setCandidate(candidate);
        candidateTest.setTest(test);

        candidate.getCandidateTests().add(candidateTest);

        candidateRepository.save(candidate);
    }
}
