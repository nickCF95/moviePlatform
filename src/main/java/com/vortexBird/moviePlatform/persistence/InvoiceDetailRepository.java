package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.DetalleFactura;
import com.vortexBird.moviePlatform.domain.repository.DetalleFacturaRepository;
import com.vortexBird.moviePlatform.persistence.crud.InvoiceDetailCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.InvoiceDetail;
import com.vortexBird.moviePlatform.persistence.mapper.DetalleFacturaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InvoiceDetailRepository implements DetalleFacturaRepository {

    @Autowired
    private InvoiceDetailCrudRepository invoiceDetailCrudRepository;

    @Autowired
    private DetalleFacturaMapper detalleFacturaMapper;

    @Override
    public Optional<List<DetalleFactura>> obtenerTodos() {
        return Optional.of(detalleFacturaMapper.toDetallesFacturas((List<InvoiceDetail>) invoiceDetailCrudRepository.findAll()));
    }

    @Override
    public Optional<List<DetalleFactura>> obtenerPorFactura(long facturaId) {
        return invoiceDetailCrudRepository.findByInvoice_idInvoice(facturaId).map(invoiceDetailList -> detalleFacturaMapper.toDetallesFacturas(invoiceDetailList));
    }

    @Override
    public Optional<List<DetalleFactura>> obtenerPorTiquete(long tiqueteId) {
        return invoiceDetailCrudRepository.findByTicket_idTicket(tiqueteId).map(invoiceDetailList -> detalleFacturaMapper.toDetallesFacturas(invoiceDetailList));
    }

    @Override
    public Optional<DetalleFactura> obtenerDetalleFactura(long detalleFacturaId) {
        return invoiceDetailCrudRepository.findById(detalleFacturaId).map(invoiceDetail -> detalleFacturaMapper.toDetalleFactura(invoiceDetail));
    }

    @Override
    public DetalleFactura save(DetalleFactura detalleFactura) {
        return detalleFacturaMapper.toDetalleFactura(invoiceDetailCrudRepository.save(detalleFacturaMapper.toInvoiceDetail(detalleFactura)));
    }

    @Override
    public List<DetalleFactura> saveAll(List<DetalleFactura> detallesFactura) {
        List<InvoiceDetail> invoiceDetailList = (List<InvoiceDetail>) invoiceDetailCrudRepository.saveAll(detallesFactura.stream().map(detalleFactura -> detalleFacturaMapper.toInvoiceDetail(detalleFactura)).toList());
        return detalleFacturaMapper.toDetallesFacturas(invoiceDetailList);
    }

    @Override
    public void delete(long detalleFacturaId) {
        invoiceDetailCrudRepository.deleteById(detalleFacturaId);
    }
}
