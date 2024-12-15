package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.app.entity.Ticket;

public interface ITicketService {
	public Optional<Ticket> obtenerTicket(Long id);
	public Ticket actualizarTicket (Long id, Ticket ticket);
	public List<Ticket> obtenerTodosTicket();
	public Page<Ticket> obtenerTicketsPaginados(int page, int size);
	public boolean borrarTicket (Long id);
	public Ticket crearTicket(Ticket ticket);
}