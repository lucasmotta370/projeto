package com.projeto.projeto.utils;

import com.projeto.projeto.model.dto.PremiacaoDTO;
import com.projeto.projeto.model.entity.Premiacao;
import com.projeto.projeto.service.PremiacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

@Component
public class CarregaArquivo {

    @Autowired
    private PremiacaoService premiacaoService;

    Scanner in;
    public void carrega(){
        try {
            in = new Scanner(new FileReader("src/main/resources/movielist.txt"));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] colunas = line.split(";",5);
                if(!colunas[1].equals("title")){
                    PremiacaoDTO premiacao = new PremiacaoDTO(null,Long.parseLong(colunas[0]) , colunas[1], colunas[2], colunas[3], colunas[4] );
                    premiacaoService.insert(premiacao);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
