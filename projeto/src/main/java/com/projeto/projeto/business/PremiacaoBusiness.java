package com.projeto.projeto.business;

import com.projeto.projeto.model.dto.RetornoDTO;
import com.projeto.projeto.model.dto.RetornoPremiacaoDTO;
import com.projeto.projeto.model.entity.Premiacao;
import com.projeto.projeto.service.PremiacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PremiacaoBusiness {

    @Autowired
    private PremiacaoService premiacaoService;
    public RetornoDTO calculaFaixas() {
        List<Premiacao> premiacao = premiacaoService.findAll();

        List<RetornoPremiacaoDTO> min = getFaixa(premiacao, false);

        List<RetornoPremiacaoDTO> max = getFaixa(premiacao, true);

        return new RetornoDTO(min, max);
    }

    private List<RetornoPremiacaoDTO> getFaixa(List<Premiacao> premiacao, Boolean ehMaior) {
        List<RetornoPremiacaoDTO> lista = new ArrayList<>();
        Comparator<Premiacao> comparator = Comparator
                .<Premiacao, String>comparing(premiacao1 -> premiacao1.getProducers())
                .thenComparingLong(premiacao1 -> premiacao1.getYear());
        List<Premiacao> ordenada = premiacao.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        Integer qtdAnos = 0;
        Integer ultAno = 0;
        String ultimoProducer = "";
        System.out.println("1");
        for (Premiacao p : ordenada){
            System.out.println(ordenada.toString());
            if(ultimoProducer.equals(p.getProducers())){
                qtdAnos = Math.toIntExact(p.getYear()) - ultAno;
                Boolean cond1 = true;
                Boolean cond2 = true;
                if(ehMaior){
                    cond1 = Math.toIntExact(p.getYear()) - ultAno >= qtdAnos;
                    cond2 = Math.toIntExact(p.getYear()) - ultAno > qtdAnos;
                }else{
                    cond1 = Math.toIntExact(p.getYear()) - ultAno <= qtdAnos;
                    cond2 = Math.toIntExact(p.getYear()) - ultAno < qtdAnos;
                }
                if(cond1){
                    if(cond2){
                        lista.removeAll(lista);
                    }
                    lista.add(new RetornoPremiacaoDTO(p.getProducers(),qtdAnos.longValue(), ultAno.longValue(), p.getYear()));
                }
            }else {
                ultAno = Math.toIntExact(p.getYear());
            }
            ultimoProducer = p.getProducers();

        };

        return lista;
    }


}
