package com.max.api.v1.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "domainSet", "candidateTests"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String title;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "test_domain",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "domain_id"))

    private Set<Domain> domainSet;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private Set<CandidateTest> candidateTests;
}
