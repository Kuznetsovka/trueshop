package com.kuznetsovka.trueshop.mapper;

import com.kuznetsovka.trueshop.domain.OrderDetails;
import com.kuznetsovka.trueshop.dto.OrderDetailsDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductMapper.class})
public interface OrderDetailsMapper {
    OrderDetailsMapper MAPPER = Mappers.getMapper(OrderDetailsMapper.class);

    @Mapping(source = "product", target = "product")
    OrderDetails toOrderDetails(OrderDetailsDto dto);

    @InheritInverseConfiguration
    OrderDetailsDto fromOrderDetails(OrderDetails orderDetails);
}
