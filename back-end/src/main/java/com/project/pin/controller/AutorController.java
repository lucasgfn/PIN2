package com.project.pin.controller;


import com.project.pin.dto.AutorRequestDTO;
import com.project.pin.dto.AutorResponseDTO;
import com.project.pin.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {
    @Autowired
    private AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorResponseDTO> save(@RequestBody AutorRequestDTO autorRequestDTO){
        AutorResponseDTO autorResponseDTO = autorService.cadastrarAutor(autorRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(autorResponseDTO);
    }

    @GetMapping
    public List<AutorResponseDTO> getAll(){
        return autorService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> getById(@PathVariable Long id){
        return autorService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return autorService.deletar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> update(@PathVariable Long id, @RequestBody AutorRequestDTO autorRequestDTO){
        return autorService.updateAutor(id, autorRequestDTO);
    }


}
