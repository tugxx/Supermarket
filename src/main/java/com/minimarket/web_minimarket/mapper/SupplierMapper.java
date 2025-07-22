package com.minimarket.web_minimarket.mapper;

import com.minimarket.web_minimarket.dto.SupplierRequestDTO;
import com.minimarket.web_minimarket.dto.SupplierResponseDTO;
import com.minimarket.web_minimarket.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    @Mapping(target = "supplierId", source = "supplierId")
    @Mapping(target = "supplierName", source = "supplierName")
    @Mapping(target = "supplierContact", source = "supplierContact")
    SupplierResponseDTO supplierToSupplierResponseDTO(Supplier supplier);

    @Mapping(target = "supplierName", source = "supplierName")
    @Mapping(target = "supplierContact", source = "supplierContact")
    Supplier supplierRequestDTOToSupplier(SupplierRequestDTO supplierDTO);
}
