package com.max.api.v1.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "testSet"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "domain")
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String title;

    private String description;

    @ManyToMany(mappedBy = "domainSet", cascade = CascadeType.ALL)
    private Set<Test> testSet;
}
