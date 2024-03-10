package com.max.api.v1.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "candidate", "test"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "candidate_test")
public class CandidateTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "result_id", referencedColumnName = "id")
    private Result result;
}
