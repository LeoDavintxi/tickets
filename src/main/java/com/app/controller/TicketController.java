package com.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.entity.Ticket;
import com.app.service.TicketService;

@RestController
@RequestMapping("api/v1/ticket")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@GetMapping
	public ResponseEntity<Object> obtenerTickets(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Ticket> tickets;
			if(page.isPresent() && size.isPresent()) {
				tickets = ticketService.obtenerTicketsPaginados(page.get(), size.get()).getContent();				
			}else {
				tickets = ticketService.obtenerTodosTicket();
			}
			return ResponseEntity.ok(tickets);
		} catch (Exception e) {
			map.put("mensaje", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> obtenerTicketID(@PathVariable Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Optional<Ticket> ticket = ticketService.obtenerTicket(id);
			if(ticket.isPresent()) {
				return ResponseEntity.ok(ticket);							
			}else {
				map.put("mensaje", "ID no encontrado");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			map.put("mensaje", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	@PostMapping
	public ResponseEntity<Object> crearTicket(@RequestBody Ticket ticket) {
		Map<String , Object> map = new HashMap<String, Object>();
		try {
			Ticket nuevoTicket = ticketService.crearTicket(ticket);
			return ResponseEntity.ok(nuevoTicket);
		} catch (Exception e) {
			map.put("mensaje", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminarTicketID(@PathVariable Long id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(ticketService.borrarTicket(id)) {
				map.put("mensaje", "Se elimina el registro con ID: " + id);
				return ResponseEntity.ok(map);							
			}else {
				map.put("mensaje", "No se encontro el registro con ID: " + id);
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			map.put("mensaje", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Object> actualizarTicket(@PathVariable Long id, @RequestBody Ticket ticket){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Ticket ticketActualizado = ticketService.actualizarTicket(id, ticket);
			if(ticketActualizado != null) {
				return ResponseEntity.ok(ticketActualizado);				
			}else {
				map.put("mensaje", "ID no encontrado");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			map.put("mensaje", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
