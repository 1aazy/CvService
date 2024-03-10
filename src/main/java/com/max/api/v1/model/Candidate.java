package com.max.api.v1.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "candidateTests"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private String middleName;

    private String description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private BinaryContent cv;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private BinaryContent photo;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private Set<CandidateTest> candidateTests;
}
