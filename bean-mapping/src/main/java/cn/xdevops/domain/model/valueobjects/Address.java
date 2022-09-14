package cn.xdevops.domain.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private String province;
    private String city;
    private String region;
    private String street;
    private String detailAddress;
}
