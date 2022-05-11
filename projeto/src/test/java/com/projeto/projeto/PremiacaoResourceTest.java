package com.projeto.projeto;

import com.projeto.projeto.business.PremiacaoBusiness;
import com.projeto.projeto.model.dto.RetornoDTO;
import com.projeto.projeto.model.dto.RetornoPremiacaoDTO;
import com.projeto.projeto.model.entity.Premiacao;
import com.projeto.projeto.repository.PremiacaoRepository;
import com.projeto.projeto.resource.PremiacaoResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PremiacaoResourceTest {

    private static final String API_URL_PATH = "/premiacao/faixa-ganhadores";

    private static final String PRODUCER_NAME_1 = "producer1";

    private static final String PRODUCER_NAME_2 = "producer2";

    private MockMvc mockMvc;

    @InjectMocks
    private PremiacaoResource premiacaoResource;

    @Mock
    private PremiacaoBusiness premiacaoBusiness;

    @Mock
    private PremiacaoRepository premiacaoRepository ;

    @InjectMocks
    private PremiacaoBusiness premiacaoBusiness2;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(premiacaoResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();

        premiacaoBusiness2 = new PremiacaoBusiness();

        MockitoAnnotations.initMocks(this);

    }

    @Test
    void getFaixaGanhadoresTest() throws Exception {

        //when
        when(premiacaoBusiness.calculaFaixas()).thenReturn(buildRetornoDto());

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min[0].interval", is(1)))
                .andExpect(jsonPath("$.max[0].interval", is(4)))
                .andExpect(jsonPath("$.min[0].producer", is(PRODUCER_NAME_1)))
                .andExpect(jsonPath("$.max[0].producer", is(PRODUCER_NAME_2)))
                .andExpect(jsonPath("$.min[0].previousWin", is(1980)))
                .andExpect(jsonPath("$.max[0].previousWin", is(1980)))
                .andExpect(jsonPath("$.min[0].folowingWin", is(1981)))
                .andExpect(jsonPath("$.max[0].folowingWin", is(1984)));
    }

    @Test
    void calculaFaixasTest()  {

        List<Premiacao> premiacaoList = buildPremiacaoList();

        when(premiacaoRepository.findAll()).thenReturn(premiacaoList);

        RetornoDTO retornoDTO = premiacaoBusiness2.calculaFaixas();

        Assertions.assertEquals(retornoDTO.getMax().get(0).getInterval(), 4L);
        Assertions.assertEquals(retornoDTO.getMax().get(0).getProducer(), PRODUCER_NAME_2);
        Assertions.assertEquals(retornoDTO.getMax().get(0).getFolowingWin(), 1984L);
        Assertions.assertEquals(retornoDTO.getMax().get(0).getPreviousWin(), 1980L);

        Assertions.assertEquals(retornoDTO.getMin().get(0).getInterval(), 1L);
        Assertions.assertEquals(retornoDTO.getMin().get(0).getProducer(), PRODUCER_NAME_1);
        Assertions.assertEquals(retornoDTO.getMin().get(0).getFolowingWin(), 1981L);
        Assertions.assertEquals(retornoDTO.getMin().get(0).getPreviousWin(), 1980L);


    }

    private List<Premiacao> buildPremiacaoList() {
        List<Premiacao> premiacaoList = new ArrayList<>();
        Premiacao premiacao1 = new Premiacao(1L, 1980L, "title1", "studios1", PRODUCER_NAME_1, "winner1");
        Premiacao premiacao2 = new Premiacao(2L, 1981L, "title2", "studios2", PRODUCER_NAME_1, "winner2");
        Premiacao premiacao3 = new Premiacao(3L, 1980L, "title3", "studios3", PRODUCER_NAME_2, "winner3");
        Premiacao premiacao4 = new Premiacao(4L, 1984L, "title4", "studios4", PRODUCER_NAME_2, "winner4");

        premiacaoList.add(premiacao1);
        premiacaoList.add(premiacao2);
        premiacaoList.add(premiacao3);
        premiacaoList.add(premiacao4);
        return premiacaoList;
    }

    private RetornoDTO buildRetornoDto() {
        RetornoDTO retornoDTO = new RetornoDTO();
        RetornoPremiacaoDTO retornoPremiacaoDTOMin = new RetornoPremiacaoDTO(PRODUCER_NAME_1, 1L, 1980L, 1981L);
        RetornoPremiacaoDTO retornoPremiacaoDTOMax = new RetornoPremiacaoDTO(PRODUCER_NAME_2, 4L, 1980L, 1984L);
        List<RetornoPremiacaoDTO> retornoPremiacaoDTOListMax = new ArrayList<>();
        List<RetornoPremiacaoDTO> retornoPremiacaoDTOListMin = new ArrayList<>();
        retornoPremiacaoDTOListMax.add(retornoPremiacaoDTOMax);
        retornoPremiacaoDTOListMin.add(retornoPremiacaoDTOMin);
        retornoDTO.setMax(retornoPremiacaoDTOListMax);
        retornoDTO.setMin(retornoPremiacaoDTOListMin);
        return retornoDTO;
    }

}
