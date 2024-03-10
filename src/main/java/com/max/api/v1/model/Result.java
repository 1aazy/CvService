package com.max.api.v1.model;//package com.max.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "candidateTest"})
@AllArgsConstructor
@Entity
@Table(name = "result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    private Integer testMark;

    @OneToOne(mappedBy = "result", cascade = CascadeType.REFRESH)
    private CandidateTest candidateTest;

    public Result() {
        date = new Date();
    }
}
