package com.max.api.v1.controller;

import com.max.api.v1.dto.domain.DomainRequestDTO;
import com.max.api.v1.dto.domain.DomainResponseDTO;
import com.max.api.v1.model.Domain;
import com.max.api.v1.service.DomainService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class DomainController {

    private final ModelMapper modelMapper;
    private final DomainService domainService;


    @GetMapping("/getDomainByTitle/{title}")
    public ResponseEntity<DomainResponseDTO> getDomainByTitle(@PathVariable("title") String title) {

        Domain domain = domainService.getDomainByTitle(title).orElseThrow(() ->
                new EntityNotFoundException("Domain with title '" + title + "' not found."));

        DomainResponseDTO responseDTO = modelMapper.map(domain, DomainResponseDTO.class);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping("/getDomains")
    public ResponseEntity<List<DomainResponseDTO>> getDomains(@RequestParam(value = "page", required = false) Integer page,
                                                              @RequestParam(value = "domain_per_page", required = false) Integer domainPegPage,
                                                              @RequestParam(value = "sort_by_title", required = false) Boolean sortByTitle) {
        List<Domain> domains;

        if (page != null && domainPegPage != null && sortByTitle != null) {
            domains = domainService.getSortedPaginationTests(page, domainPegPage);
        } else if (page != null && domainPegPage != null) {
            domains = domainService.getPaginationDomains(page, domainPegPage);
        } else if (sortByTitle != null) {
            domains = domainService.getSortedDomains();
        } else {
            domains = domainService.getAllDomains();
        }

        List<DomainResponseDTO> responseDTOList = new ArrayList<>();

        for (Domain domain : domains) {
            DomainResponseDTO responseDTO = modelMapper.map(domain, DomainResponseDTO.class);
            responseDTOList.add(responseDTO);
        }

        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/createDomain")
    public void createDomain(@RequestBody DomainRequestDTO domainDTO) {

        Domain domain = modelMapper.map(domainDTO, Domain.class);

        domainService.saveDomain(domain);
    }


    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/updateDomain/{title}")
    public void updateDomain(@PathVariable("title") String title,
                             @RequestBody DomainRequestDTO requestDTO) {

        Domain domainToUpdate = domainService.getDomainByTitle(title).orElseThrow(() ->
                new EntityNotFoundException("Domain with title '" + title + "' not found."));

        Domain updatedDomain = modelMapper.map(requestDTO, Domain.class);

        domainService.updateDomain(domainToUpdate, updatedDomain);
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/deleteDomainByTitle/{title}")
    public void deleteDomainByTitle(@PathVariable("title") String title) {
        domainService.deleteDomainByTitle(title);
    }
}
