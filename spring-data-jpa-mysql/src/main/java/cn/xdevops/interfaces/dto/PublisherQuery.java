package cn.xdevops.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherQuery {
    private String publisherName;
    private String city;
    private LocalDate onboardDate;
}
