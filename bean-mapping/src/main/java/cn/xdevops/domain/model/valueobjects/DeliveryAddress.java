package cn.xdevops.domain.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAddress {
    private String name;
    private String phone;
    private String province;
    private String city;
    private String region;
    private String street;
    private String detailAddress;
}
