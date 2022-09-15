package cn.xdevops.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {
    private Long id;
    private String publisherName;
    private String city;
    private LocalDate onboardDate;
}
