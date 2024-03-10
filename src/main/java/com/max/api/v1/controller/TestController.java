package com.max.api.v1.controller;

import com.max.api.v1.dto.test.TestRequestDTO;
import com.max.api.v1.dto.test.TestResponseDTO;
import com.max.api.v1.model.Test;
import com.max.api.v1.service.TestService;
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
public class TestController {

    private final ModelMapper modelMapper;
    private final TestService testService;


    @GetMapping("/getTestByTitle/{title}")
    public ResponseEntity<TestResponseDTO> getTestByTitle(@PathVariable("title") String title) {

        Test test = testService.getTestByTitle(title).orElseThrow(() ->
                new EntityNotFoundException("Test with title '" + title + "' not found."));

        TestResponseDTO responseDTO = modelMapper.map(test, TestResponseDTO.class);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping("/getTests")
    public ResponseEntity<List<TestResponseDTO>> getTests(@RequestParam(value = "page", required = false) Integer page,
                                                          @RequestParam(value = "test_per_page", required = false) Integer testPegPage,
                                                          @RequestParam(value = "sort_by_title", required = false) Boolean sortByTitle) {
        List<Test> tests;

        if (page != null && testPegPage != null && sortByTitle != null) {
            tests = testService.getSortedByTitlePaginationTests(page, testPegPage);
        } else if (page != null && testPegPage != null) {
            tests = testService.getPaginationTests(page, testPegPage);
        } else if (sortByTitle != null) {
            tests = testService.getSortedByTitleTests();
        } else {
            tests = testService.getAllTests();
        }

        List<TestResponseDTO> responseDTOList = new ArrayList<>();

        for (Test test : tests) {
            TestResponseDTO responseDTO = modelMapper.map(test, TestResponseDTO.class);
            responseDTOList.add(responseDTO);
        }
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }


    @PostMapping("/createTest")
    public void createTest(@RequestBody TestRequestDTO testDTO) {

        Test test = modelMapper.map(testDTO, Test.class);

        testService.saveTest(test);
    }


    @PatchMapping("/updateTest/{title}")
    public ResponseEntity<TestResponseDTO> updateTest(@PathVariable("title") String title,
                                                      @RequestBody TestRequestDTO requestDTO) {

        Test testToUpdate = testService.getTestByTitle(title).orElseThrow(() ->
                new EntityNotFoundException("Test with title '" + title + "' not found."));

        Test updatedTest = modelMapper.map(requestDTO, Test.class);

        updatedTest = testService.updateTest(testToUpdate, updatedTest);
        TestResponseDTO response = modelMapper.map(updatedTest, TestResponseDTO.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PatchMapping("/addCandidateToTest/{title}")
    public ResponseEntity<TestResponseDTO> addCandidateToTest(@PathVariable("title") String title,
                                                              @RequestParam("candidateId") int candidate) {

        Test test = testService.addCandidateToTest(title, candidate);
        TestResponseDTO testResponseDTO = modelMapper.map(test, TestResponseDTO.class);

        return new ResponseEntity<>(testResponseDTO, HttpStatus.OK);
    }


    @DeleteMapping("/deleteTestByTitle/{title}")
    public void deleteTestByDomain(@PathVariable("title") String title) {
        testService.deleteTestByTitle(title);
    }
}
