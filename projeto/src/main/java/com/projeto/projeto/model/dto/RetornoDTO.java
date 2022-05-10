package com.projeto.projeto.model.dto;

import java.io.Serializable;
import java.util.List;

public class RetornoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<RetornoPremiacaoDTO> min;

    private List<RetornoPremiacaoDTO> max;

    public RetornoDTO(List<RetornoPremiacaoDTO> min, List<RetornoPremiacaoDTO> max) {
        this.min = min;
        this.max = max;
    }

    public RetornoDTO() {
    }

    public List<RetornoPremiacaoDTO> getMin() {
        return min;
    }

    public void setMin(List<RetornoPremiacaoDTO> min) {
        this.min = min;
    }

    public List<RetornoPremiacaoDTO> getMax() {
        return max;
    }

    public void setMax(List<RetornoPremiacaoDTO> max) {
        this.max = max;
    }
}
