package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.DetalleFactura;
import com.vortexBird.moviePlatform.persistence.entity.InvoiceDetail;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",uses = {TiqueteMapper.class, FacturaMapper.class})
public interface DetalleFacturaMapper {
    @Mappings({
            @Mapping(source = "idInvoiceDetail", target = "detalleFacturaId"),
            @Mapping(source = "ticket", target = "tiquete"),
            @Mapping(source = "invoice", target = "factura"),
    })
    DetalleFactura toDetalleFactura(InvoiceDetail invoiceDetail);

    List<DetalleFactura> toDetallesFacturas(List<InvoiceDetail> invoiceDetailList);

    @InheritInverseConfiguration
    InvoiceDetail toInvoiceDetail(DetalleFactura detalleFactura);
}
