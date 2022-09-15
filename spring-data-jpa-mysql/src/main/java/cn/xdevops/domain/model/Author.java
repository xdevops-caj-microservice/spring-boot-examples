package cn.xdevops.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private String id;
    private String firstName;
    private String lastName;
    private String nationality;
}
