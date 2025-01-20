package com.rexdev08.forohub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rexdev08.forohub.model.Topico;
import com.rexdev08.forohub.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping
    public List<Topico> listarTodos() {
        return topicoRepository.findAll();
    }

    @PostMapping
    public Topico crear(@RequestBody Topico topico) {
        topico.setFechaCreacion(java.time.LocalDateTime.now());
        return topicoRepository.save(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizar(@PathVariable Long id, @RequestBody Topico topicoDetalles) {
        return topicoRepository.findById(id)
                .map(topico -> {
                    topico.setTitulo(topicoDetalles.getTitulo());
                    topico.setMensaje(topicoDetalles.getMensaje());
                    topico.setStatus(topicoDetalles.getStatus());
                    topico.setAutor(topicoDetalles.getAutor());
                    topico.setCurso(topicoDetalles.getCurso());
                    topicoRepository.save(topico);
                    return ResponseEntity.ok(topico);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(topico -> {
                    topicoRepository.delete(topico);
                    return ResponseEntity.<Void>noContent().build(); // Especificar explícitamente el tipo
                })
                .orElse(ResponseEntity.notFound().build());
    }
}