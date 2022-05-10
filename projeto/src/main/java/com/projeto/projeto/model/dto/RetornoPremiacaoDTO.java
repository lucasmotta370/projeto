package com.projeto.projeto.model.dto;

import com.projeto.projeto.model.entity.Premiacao;

import java.io.Serializable;
import java.util.List;

public class RetornoPremiacaoDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    private String producer;

    private Long interval;

    private Long previousWin;

    private Long folowingWin;

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public Long getPreviousWin() {
        return previousWin;
    }

    public void setPreviousWin(Long previousWin) {
        this.previousWin = previousWin;
    }

    public Long getFolowingWin() {
        return folowingWin;
    }

    public void setFolowingWin(Long folowingWin) {
        this.folowingWin = folowingWin;
    }

    public RetornoPremiacaoDTO(String producer, Long interval, Long previousWin, Long folowingWin) {
        this.producer = producer;
        this.interval = interval;
        this.previousWin = previousWin;
        this.folowingWin = folowingWin;
    }

    public RetornoPremiacaoDTO() {
    }

    @Override
    public String toString() {
        return "RetornoPremiacaoDTO{" +
                "producer='" + producer + '\'' +
                ", interval=" + interval +
                ", previousWin=" + previousWin +
                ", folowingWin=" + folowingWin +
                '}';
    }
}
