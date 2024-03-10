package com.max.api.v1.controller;

import com.max.api.v1.dto.CandidateDTO;
import com.max.api.v1.dto.test.TestNameDTO;
import com.max.api.v1.model.BinaryContent;
import com.max.api.v1.model.Candidate;
import com.max.api.v1.service.CandidateService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;
    private final ModelMapper modelMapper;


    @GetMapping("/getCandidateById/{id}")
    public ResponseEntity<CandidateDTO> getCandidateById(@PathVariable("id") int id) {
        Candidate candidate = candidateService.getCandidateById(id);

        CandidateDTO candidateDTO = modelMapper.map(candidate, CandidateDTO.class);
        return new ResponseEntity<>(candidateDTO, HttpStatus.OK);
    }


    @GetMapping("/getCandidates")
    public ResponseEntity<List<CandidateDTO>> getCandidates(@RequestParam(value = "page", required = false) Integer page,
                                                            @RequestParam(value = "candidate_per_page", required = false) Integer candidatePegPage,
                                                            @RequestParam(value = "sort_by_name", required = false) Boolean sortByName) {
        List<Candidate> candidates;

        if (page != null && candidatePegPage != null && sortByName != null) {
            candidates = candidateService.getSortedByNamePaginationTests(page, candidatePegPage);
        } else if (page != null && candidatePegPage != null) {
            candidates = candidateService.getPaginationCandidates(page, candidatePegPage);
        } else if (sortByName != null) {
            candidates = candidateService.getSortedByNameTests();
        } else {
            candidates = candidateService.getAllTest();
        }

        List<CandidateDTO> candidateDTOList = new ArrayList<>();

        for (Candidate candidate : candidates) {
            CandidateDTO responseDTO = modelMapper.map(candidate, CandidateDTO.class);
            candidateDTOList.add(responseDTO);
        }
        return new ResponseEntity<>(candidateDTOList, HttpStatus.OK);
    }


    @PostMapping("/createCandidate")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCandidate(@RequestParam String name,
                              @RequestParam String surname,
                              @RequestParam String middleName,
                              @RequestParam String description,
                              @RequestPart(name = "photo", required = false) MultipartFile photo,
                              @RequestPart(name = "cv", required = false) MultipartFile cv) {

        Candidate candidate = new Candidate();
        candidate.setName(name);
        candidate.setSurname(surname);
        candidate.setMiddleName(middleName);
        candidate.setDescription(description);


        if (photo != null &&
                (photo.getContentType().equals("image/jpeg") || photo.getContentType().equals("image/png"))) {
            try {
                candidate.setPhoto(new BinaryContent(photo.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException("An error occurred while setting the candidate photo", e);
            }
        }

        if (cv != null && cv.getContentType().equals("application/pdf")) {
            try {
                candidate.setCv(new BinaryContent(cv.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException("An error occurred while setting the candidate photo", e);
            }
        }

        candidateService.saveCandidate(candidate);
    }


    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/uploadPhotoToCandidate/{id}")
    public void uploadPhoto(@PathVariable("id") int candidateId,
                            @RequestBody byte[] photo) {

        candidateService.uploadPhoto(candidateId, photo);
    }


    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/uploadCvToCandidate/{id}")
    public void uploadCv(@PathVariable("id") int candidatId,
                         @RequestBody byte[] cv) {

        candidateService.uploadCv(candidatId, cv);
    }


    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/addTestToCandidate/{id}")
    public void addTestToCandidate(@PathVariable("id") int candidateId,
                                   @RequestBody TestNameDTO requestDTO) {
        candidateService.addTestToCandidate(candidateId, requestDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/deleteCandidateById/{id}")
    public void deleteCandidate(@PathVariable("id") int id) {
        candidateService.deleteCandidateById(id);
    }
}
