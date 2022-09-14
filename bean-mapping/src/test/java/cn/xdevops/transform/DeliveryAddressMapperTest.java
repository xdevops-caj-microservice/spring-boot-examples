package cn.xdevops.transform;

import cn.xdevops.domain.model.entities.Customer;
import cn.xdevops.domain.model.valueobjects.Address;
import cn.xdevops.domain.model.valueobjects.DeliveryAddress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Mapping Multiple
 */
class DeliveryAddressMapperTest {

    @Test
    @DisplayName("should transform delivery address from customer and address")
    void shouldTransformDeliveryAddressFromCustomerAndAddress() {
        Customer customer = Customer.builder()
                .name("William")
                .phone("12345678")
                .build();
        Address address = Address.builder()
                .province("Shanghai")
                .city("Shanghai")
                .region("Zhangjiang")
                .detailAddress("23-01")
                .build();

        DeliveryAddress deliveryAddress = DeliveryAddressMapper.MAPPER
                .toDeliveryAddress(customer, address);

        assertThat(deliveryAddress).isNotNull();
        assertThat(deliveryAddress.getName()).isEqualTo(customer.getName());
        assertThat(deliveryAddress.getPhone()).isEqualTo(customer.getPhone());
        assertThat(deliveryAddress.getProvince()).isEqualTo(address.getProvince());
        assertThat(deliveryAddress.getCity()).isEqualTo(address.getCity());
        assertThat(deliveryAddress.getRegion()).isEqualTo(address.getRegion());
        assertThat(deliveryAddress.getStreet()).isEqualTo(address.getStreet());
        assertThat(deliveryAddress.getDetailAddress()).isEqualTo(address.getDetailAddress());
    }

    @Test
    @DisplayName("should transform delivery address by default")
    void shouldTransformDeliveryAddressByDefault() {
        Customer customer = Customer.builder()
                .name("William")
                .phone("12345678")
                .build();
        Address address = Address.builder()
                .province("Shanghai")
                .city("Shanghai")
                .region("Zhangjiang")
                .detailAddress("23-01")
                .build();

        DeliveryAddress deliveryAddress = DeliveryAddressMapper.MAPPER
                .toDeliveryAddressByDefault(customer, address);

        assertThat(deliveryAddress).isNotNull();
        assertThat(deliveryAddress.getName()).isEqualTo(customer.getName());
        assertThat(deliveryAddress.getPhone()).isEqualTo(customer.getPhone());
        assertThat(deliveryAddress.getProvince()).isEqualTo(address.getProvince());
        assertThat(deliveryAddress.getCity()).isEqualTo(address.getCity());
        assertThat(deliveryAddress.getRegion()).isEqualTo(address.getRegion());
        assertThat(deliveryAddress.getStreet()).isEqualTo(address.getStreet());
        assertThat(deliveryAddress.getDetailAddress()).isEqualTo(address.getDetailAddress());
    }
}