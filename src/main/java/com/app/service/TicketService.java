package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.entity.Ticket;
import com.app.repository.TicketRepository;

@Service
public class TicketService implements ITicketService {

	@Autowired
	private TicketRepository ticketRepository;

	public Page<Ticket> obtenerTicketsPaginados(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return ticketRepository.findAll(pageable);
	}

	public List<Ticket> obtenerTodosTicket() {
		return ticketRepository.findAll();
	}

	public Optional<Ticket> obtenerTicket(Long id) {
		return ticketRepository.findById(id);
	}

	public boolean borrarTicket(Long id) {
		if(ticketRepository.findById(id).isPresent()) {
			ticketRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public Ticket actualizarTicket(Long id, Ticket ticket) {
		Optional<Ticket> ticketActualizado = ticketRepository.findById(id);
		if(ticketActualizado.isPresent()) {
			ticketActualizado.get().setEstado(ticket.getEstado().toString());
			ticketActualizado.get().setDetalle(ticket.getDetalle());
		}else {
			return null;
		}
		return ticketRepository.save(ticketActualizado.get());
	}

	public Ticket crearTicket(Ticket ticket) {
		Ticket nuevoTicket = ticketRepository.save(ticket);
		return nuevoTicket;
	}

}
