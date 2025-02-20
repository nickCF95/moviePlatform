package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.Factura;
import com.vortexBird.moviePlatform.persistence.entity.Invoice;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import javax.print.DocFlavor;
import java.util.List;

@Mapper(componentModel = "spring",uses = {MetodoPagoUsuarioMapper.class})
public interface FacturaMapper {

    @Mappings({
            @Mapping(source = "idInvoice", target = "facturaId"),
            @Mapping(source = "date", target = "fecha"),
            @Mapping(source = "numberOfTickets", target = "numeroTiquetes"),
            @Mapping(source = "price", target = "precio"),
            @Mapping(source = "userPaymentMethod", target = "metodoPagoUsuario"),
            @Mapping(target = "detallesFactura", ignore = true),
    })
    Factura toFactura(Invoice invoice);

    List<Factura> toFacturas(List<Invoice> invoices);

    @InheritInverseConfiguration
    Invoice toInvoice(Factura factura);
}
