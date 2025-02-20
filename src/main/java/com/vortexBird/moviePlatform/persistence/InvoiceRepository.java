package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.Factura;
import com.vortexBird.moviePlatform.domain.repository.FacturaRepository;
import com.vortexBird.moviePlatform.persistence.crud.InvoiceCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.Invoice;
import com.vortexBird.moviePlatform.persistence.mapper.FacturaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class InvoiceRepository implements FacturaRepository {

    @Autowired
    private InvoiceCrudRepository invoiceCrudRepository;

    @Autowired
    private FacturaMapper facturaMapper;

    @Override
    public Optional<List<Factura>> obtenerTodas() {
        return Optional.of(facturaMapper.toFacturas((List<Invoice>) invoiceCrudRepository.findAll()));
    }

    @Override
    public Optional<List<Factura>> obtenerPorMetodoPagoUsuario(long metodoPagoUsuarioId) {
        return invoiceCrudRepository.findByUserPaymentMethod_idUserPaymentMethod(metodoPagoUsuarioId).map(invoices -> facturaMapper.toFacturas(invoices));
    }

    @Override
    public Optional<List<Factura>> obtenerPorFecha(Timestamp fecha) {
        return invoiceCrudRepository.findByDate(fecha).map(invoices -> facturaMapper.toFacturas(invoices));
    }

    @Override
    public Optional<List<Factura>> obtenerPorFechaAntesDe(Timestamp fecha) {
        return invoiceCrudRepository.findByDateBefore(fecha).map(invoices -> facturaMapper.toFacturas(invoices));
    }

    @Override
    public Optional<List<Factura>> obtenerPorFechaDespuesDe(Timestamp fecha) {
        return invoiceCrudRepository.findByDateAfter(fecha).map(invoices -> facturaMapper.toFacturas(invoices));
    }

    @Override
    public Optional<List<Factura>> obtenerPorRangoDeFechas(Timestamp fechaI, Timestamp fechaF) {
        return invoiceCrudRepository.findByDateBetween(fechaI, fechaF).map(invoices -> facturaMapper.toFacturas(invoices));
    }

    @Override
    public Optional<List<Factura>> obtenerPorNumeroTiquetes(int noTiquetes) {
        return invoiceCrudRepository.findByNumberOfTickets(noTiquetes).map(invoices -> facturaMapper.toFacturas(invoices));
    }

    @Override
    public Optional<List<Factura>> obtenerPorNumeroTiquetesMayorA(int noTiquetes) {
        return invoiceCrudRepository.findByNumberOfTicketsGreaterThanEqual(noTiquetes).map(invoices -> facturaMapper.toFacturas(invoices));

    }

    @Override
    public Optional<List<Factura>> obtenerPorNumeroTiquetesMenorA(int noTiquetes) {
        return invoiceCrudRepository.findByNumberOfTicketsLessThanEqual(noTiquetes).map(invoices -> facturaMapper.toFacturas(invoices));
    }

    @Override
    public Optional<List<Factura>> obtenerPorRangoDeNumeroTiquetes(int noTiquetesI, int noTiquetesF) {
        return invoiceCrudRepository.findByNumberOfTicketsRange(noTiquetesI, noTiquetesF).map(invoices -> facturaMapper.toFacturas(invoices));
    }

    @Override
    public Optional<Factura> obtenerFactura(long facturaId) {
        return invoiceCrudRepository.findById(facturaId).map(invoice -> facturaMapper.toFactura(invoice));
    }

    @Override
    public Factura save(Factura factura) {
        return facturaMapper.toFactura(invoiceCrudRepository.save(facturaMapper.toInvoice(factura)));
    }

    @Override
    public void delete(long facturaId) {
        invoiceCrudRepository.deleteById(facturaId);
    }
}
