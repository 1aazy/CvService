package com.max.api.v1.service;

import com.max.api.v1.exception.TestTitleDuplicationException;
import com.max.api.v1.model.Candidate;
import com.max.api.v1.model.CandidateTest;
import com.max.api.v1.model.Domain;
import com.max.api.v1.model.Test;
import com.max.api.v1.repositories.CandidateRepository;
import com.max.api.v1.repositories.DomainRepository;
import com.max.api.v1.repositories.TestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TestService {

    private final TestRepository testRepository;
    private final DomainRepository domainRepository;
    private final CandidateRepository candidateRepository;

    @Transactional
    public void saveTest(Test test) {
        enrichTest(test);
        try {
            testRepository.save(test);
        } catch (ConstraintViolationException e) {
            throw new TestTitleDuplicationException("Test with title '" + test.getTitle() + "' already exist.");
        }
    }

    @Transactional
    public void deleteTestByTitle(String title) {
        testRepository.deleteByTitle(title);
    }

    public Optional<Test> getTestByTitle(String title) {
        return testRepository.findByTitle(title);
    }


    public List<Test> getPaginationTests(int page, int testPerPage) {
        return testRepository.findAll(PageRequest.of(page, testPerPage)).getContent();
    }

    public List<Test> getSortedByTitlePaginationTests(int page, int testPerPage) {
        return testRepository.findAll(PageRequest.of(page, testPerPage, Sort.by("title"))).getContent();
    }

    public List<Test> getSortedByTitleTests() {
        return testRepository.findAll(Sort.by("title"));
    }

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    @Transactional
    public Test updateTest(Test testToUpdate, Test updatedTest) {
        testToUpdate.setDomainSet(updatedTest.getDomainSet());

        if (!testToUpdate.getDomainSet().isEmpty()) {
            enrichTest(testToUpdate);
        }

        return testRepository.save(testToUpdate);
    }

    @Transactional
    public Test addCandidateToTest(String title, int candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() ->
                new EntityNotFoundException("Candidate with id '" + candidateId + "' not found."));

        Test test = testRepository.findByTitle(title).orElseThrow(() ->
                new EntityNotFoundException("Test with title '" + title + "' not found."));

        CandidateTest candidateTest = new CandidateTest();

        candidateTest.setCandidate(candidate);
        candidateTest.setTest(test);

        test.getCandidateTests().add(candidateTest);

        return testRepository.save(test);
    }


    private void enrichTest(Test test) {
        Set<Domain> domains = test.getDomainSet();

        if (!domains.isEmpty()) {

            Set<Domain> populatedSet = new HashSet<>();
            for (Domain domain : domains) {

                Domain domainToSave = domainRepository.findByTitle(domain.getTitle()).orElseThrow(() ->
                        new EntityNotFoundException("Domain with title '" + domain.getTitle() + "' not found."));

                domainToSave.getTestSet().add(test);

                populatedSet.add(domainToSave);
            }
            test.setDomainSet(populatedSet);
        }
    }
}
