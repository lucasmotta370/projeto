package com.projeto.projeto.resource;

import com.projeto.projeto.business.PremiacaoBusiness;
import com.projeto.projeto.model.dto.PremiacaoDTO;
import com.projeto.projeto.model.dto.RetornoDTO;
import com.projeto.projeto.model.entity.Premiacao;
import com.projeto.projeto.service.PremiacaoService;
import com.projeto.projeto.utils.CarregaArquivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/premiacao")
public class PremiacaoResource {

    @Autowired
    private PremiacaoService premiacaoService;

    @Autowired
    private CarregaArquivo carregaArquivo;

    @Autowired
    private PremiacaoBusiness premiacaoBusiness;


    @GetMapping(value = "/{id}")
    public ResponseEntity<PremiacaoDTO> findByID(@PathVariable Long id) {
        PremiacaoDTO premiacaoDTO = premiacaoService.findByID(id);
        return ResponseEntity.ok().body(premiacaoDTO);
    }

    @GetMapping(value = "/faixa-ganhadores")
    public ResponseEntity<RetornoDTO> getfaixas() {
        RetornoDTO retornoDTO = premiacaoBusiness.calculaFaixas();
        return ResponseEntity.ok().body(retornoDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PremiacaoDTO>> findAll(

        @RequestParam(value = "page", defaultValue = "0")Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy

        ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<PremiacaoDTO> list = premiacaoService.findAll(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping(value= "/{id}")
    public ResponseEntity<PremiacaoDTO> deleteByID(@PathVariable Long id){
        premiacaoService.deleteByID(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PremiacaoDTO> insert(@RequestBody PremiacaoDTO premiacaoDTO){
        premiacaoDTO = premiacaoService.insert(premiacaoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(premiacaoDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(premiacaoDTO);

    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<PremiacaoDTO> update(@PathVariable Long id, @RequestBody PremiacaoDTO premiacaoDTO){
        premiacaoDTO = premiacaoService.update(id, premiacaoDTO);

        return ResponseEntity.ok().body(premiacaoDTO);
    }
}
