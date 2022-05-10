package com.projeto.projeto.service;

import com.projeto.projeto.exceptions.DatabaseException;
import com.projeto.projeto.exceptions.ResourceNotFoundException;
import com.projeto.projeto.model.dto.PremiacaoDTO;
import com.projeto.projeto.model.entity.Premiacao;
import com.projeto.projeto.repository.PremiacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PremiacaoService {

    @Autowired
    private PremiacaoRepository premiacaoRepository;

    @Transactional(readOnly = true)
    public PremiacaoDTO findByID(Long id) {

        try {
            Optional<Premiacao> obj = premiacaoRepository.findById(id);
            Premiacao premiacao = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
            return new PremiacaoDTO(premiacao);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("id not found " + id);
        }


    }


    @Transactional(readOnly = true)
    public Page<PremiacaoDTO> findAll(PageRequest pageRequest) {
        Page<Premiacao> list = premiacaoRepository.findAll(pageRequest);

        return list.map(PremiacaoDTO::new);
    }

    @Transactional(readOnly = true)
    public List<Premiacao> findAll() {
        List<Premiacao> list = premiacaoRepository.findAll();

        return list;
    }

    @Transactional(readOnly = false)
    public void deleteByID(Long id) {
        try {
            premiacaoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Error deleting id " + id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity  violation trying to delete Premiacao" + id);
        }

    }

    @Transactional(readOnly = false)
    public PremiacaoDTO insert(PremiacaoDTO premiacaoDTO) {
        Premiacao premiacao = new Premiacao();
        copyDTOtoEntity(premiacaoDTO, premiacao);

        premiacao = premiacaoRepository.save(premiacao);
        return new PremiacaoDTO(premiacao);
    }

    @Transactional(readOnly = false)
    public PremiacaoDTO update(Long id, PremiacaoDTO premiacaoDTO) {
        try {
            Premiacao premiacao = premiacaoRepository.getOne(id);

            copyDTOtoEntity(premiacaoDTO, premiacao);

            return new PremiacaoDTO(premiacao);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Not found id " + premiacaoDTO.getId());
        }

    }
    private void copyDTOtoEntity(PremiacaoDTO premiacaoDTO, Premiacao premiacao){
        premiacao.setTitle(premiacaoDTO.getTitle());
        premiacao.setProducers(premiacaoDTO.getProducers());
        premiacao.setStudios(premiacaoDTO.getStudios());
        premiacao.setWinner(premiacaoDTO.getWinner());
        premiacao.setYear(premiacaoDTO.getYear());
    }
}
