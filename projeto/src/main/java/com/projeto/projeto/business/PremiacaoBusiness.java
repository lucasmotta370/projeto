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

    private List<RetornoPremiacaoDTO> getFaixa(List<Premiacao> premiacao, Boolean buscaMaior) {
        List<RetornoPremiacaoDTO> listaFaixas = new ArrayList<>();

        //Ordena a lista de premiações por ganhador de ano
        premiacao = ordena(premiacao);

        //Monta as faixas de anos de premiação por ganhador
        listaFaixas = montaFaixas(premiacao);

        //Ordena os intervalos por qtd de faixa premiada
        listaFaixas = ordenaIntervalos(listaFaixas);


        //Aqui percorre a lista verificando as maiores e menores faixas
        List<RetornoPremiacaoDTO> faixa = new ArrayList<>();
        Integer ultFaixa = 0;
        for (RetornoPremiacaoDTO ret : listaFaixas){
            if(ultFaixa == 0){
                ultFaixa = Math.toIntExact(ret.getInterval());
            }
            if(Math.toIntExact(ret.getInterval()) > ultFaixa){
                //Se eu estiver buscando pelas faixas menores, e a faixa já incrementou, então já posso retornar os valores
                if(!buscaMaior){
                    return faixa;
                }
                //Se estou buscando por faixas maiores e a faixa foi incrementada, então devo limpar minha lista para adicioar a faixa maior
                faixa.removeAll(faixa);
            }
            faixa.add(ret);
            ultFaixa = Math.toIntExact(ret.getInterval());
        }

        return faixa;
    }




    private List<RetornoPremiacaoDTO> montaFaixas(List<Premiacao> premiacao) {
        List<RetornoPremiacaoDTO> lista = new ArrayList<>();
        Integer qtdAnos = 0;
        Integer ultAno = 0;
        String ultimoProducer = "";
        for (Premiacao p : premiacao){
            if(ultimoProducer.equals(p.getProducers())){
                qtdAnos = Math.toIntExact(p.getYear()) - ultAno;
                lista.add(new RetornoPremiacaoDTO(p.getProducers(),qtdAnos.longValue(), ultAno.longValue(), p.getYear()));
            }else {
                ultAno = Math.toIntExact(p.getYear());
            }
            ultimoProducer = p.getProducers();
        };
        return lista;
    }

    private List<Premiacao> ordena(List<Premiacao> premiacao) {
        Comparator<Premiacao> comparator = Comparator
                .<Premiacao, String>comparing(premiacao1 -> premiacao1.getProducers())
                .thenComparingLong(premiacao1 -> premiacao1.getYear());
        List<Premiacao> ordenada = premiacao.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        return ordenada;
    }

    private List<RetornoPremiacaoDTO> ordenaIntervalos(List<RetornoPremiacaoDTO> retornoPremiacaoDTOList) {
        Comparator<RetornoPremiacaoDTO> comparator = Comparator
                .<RetornoPremiacaoDTO, Long>comparing(retorno -> retorno.getInterval());
        List<RetornoPremiacaoDTO> ordenada = retornoPremiacaoDTOList.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        return ordenada;
    }


}
