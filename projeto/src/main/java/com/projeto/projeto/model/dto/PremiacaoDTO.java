package com.projeto.projeto.model.dto;

import com.projeto.projeto.model.entity.Premiacao;
import java.io.Serializable;

public class PremiacaoDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;

    private Long year;

    private String title;

    private String studios;

    private String producers;

    private String winner;


    public PremiacaoDTO() {
    }

    public PremiacaoDTO(Long id, Long year, String title, String studios, String producers, String winner) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public PremiacaoDTO(Premiacao premiacao){
        this.id = premiacao.getId();
        this.producers = premiacao.getProducers();
        this.studios = premiacao.getStudios();
        this.winner = premiacao.getWinner();
        this.title = premiacao.getTitle();
        this.year = premiacao.getYear();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public String getProducers() {
        return producers;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "PremiacaoDTO{" +
                "id=" + id +
                ", year=" + year +
                ", title='" + title + '\'' +
                ", studios='" + studios + '\'' +
                ", producers='" + producers + '\'' +
                ", winner='" + winner + '\'' +
                '}';
    }
}
