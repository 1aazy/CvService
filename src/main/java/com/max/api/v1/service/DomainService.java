package com.max.api.v1.service;

import com.max.api.v1.exception.DomainTitleDuplicationException;
import com.max.api.v1.model.Domain;
import com.max.api.v1.model.Test;
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
public class DomainService {

    private final DomainRepository domainRepository;
    private final TestRepository testRepository;

    @Transactional
    public void saveDomain(Domain domain) {

        enrichDomain(domain);

        try {
             domainRepository.save(domain);
        } catch (ConstraintViolationException e) {
            throw new DomainTitleDuplicationException("Domain with title '" + domain.getTitle() + "' already exist.");
        }
    }

    @Transactional
    public void deleteDomainByTitle(String title) {

        Domain domainToDelete = domainRepository.findByTitle(title).orElseThrow(() ->
                new EntityNotFoundException("Domain with title '" + title + "' not found"));

        Set<Test> testToDelete = new HashSet<>();

        Set<Test> tests = domainToDelete.getTestSet();

        for (Test test : tests) {

            Set<Domain> domains = test.getDomainSet();

            if (domains.size() > 1) {
                domains.remove(domainToDelete);
                testToDelete.add(test);
            }
        }

        domainToDelete.getTestSet().removeAll(testToDelete);

        domainRepository.delete(domainToDelete);
    }

    public Optional<Domain> getDomainByTitle(String title) {
        return domainRepository.findByTitle(title);
    }

    public List<Domain> getPaginationDomains(int page, int domainPerPage) {
        return domainRepository.findAll(PageRequest.of(page, domainPerPage)).getContent();
    }

    public List<Domain> getSortedPaginationTests(Integer page, Integer domainPerPage) {
        return domainRepository.findAll(PageRequest.of(page, domainPerPage, Sort.by("title"))).getContent();
    }

    public List<Domain> getSortedDomains() {
        return domainRepository.findAll(Sort.by("title"));
    }

    public List<Domain> getAllDomains() {
        return domainRepository.findAll();
    }

    @Transactional
    public void updateDomain(Domain domainToUpdate, Domain updatedDomain) {

        clearTestDomainSet(domainToUpdate);

        domainToUpdate.setTestSet(updatedDomain.getTestSet());

        if (!domainToUpdate.getTestSet().isEmpty()) {
            enrichDomain(domainToUpdate);
        }

        domainRepository.save(domainToUpdate);
    }


    private void enrichDomain(Domain domain) {

        Set<Test> tests = domain.getTestSet();

        if (!tests.isEmpty()) {

            Set<Test> populatedSet = new HashSet<>();

            for (Test test : tests) {

                Test testToSave = testRepository.findByTitle(test.getTitle()).orElseThrow(() ->
                        new EntityNotFoundException("Test with title '" + test.getTitle() + "' not found."));

                testToSave.getDomainSet().add(domain);

                populatedSet.add(testToSave);
            }
            domain.setTestSet(populatedSet);
        }
    }

    private void clearTestDomainSet(Domain domain) {

        Set<Test> tests = domain.getTestSet();

        if (!tests.isEmpty()) {

            for (Test test : tests) {

                test.setDomainSet(new HashSet<>());
            }
        }
    }
}
