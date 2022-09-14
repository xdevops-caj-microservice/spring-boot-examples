package cn.xdevops.transform;

import cn.xdevops.domain.model.entities.Customer;
import cn.xdevops.domain.model.valueobjects.Address;
import cn.xdevops.domain.model.valueobjects.DeliveryAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeliveryAddressMapper {
    DeliveryAddressMapper MAPPER = Mappers.getMapper(DeliveryAddressMapper.class);

    @Mapping(source = "customer.name", target = "name")
    @Mapping(source = "customer.phone", target = "phone")
    @Mapping(source = "address.province", target = "province")
    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "address.region", target = "region")
    @Mapping(source = "address.street", target = "street")
    @Mapping(source = "address.detailAddress", target = "detailAddress")
    DeliveryAddress toDeliveryAddress(Customer customer, Address address);
}
