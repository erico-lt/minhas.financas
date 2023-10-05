package com.erico.minhasfinancas.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.erico.minhasfinancas.dto.UsuarioDTO;
import com.erico.minhasfinancas.entites.Usuario;
import com.erico.minhasfinancas.resources.UsuarioResource;
import com.erico.minhasfinancas.services.LancamentoServices;
import com.erico.minhasfinancas.services.UsuarioServices;
import com.erico.minhasfinancas.services.impl.exceptions.RecursoNaoEncontradoException;
import com.erico.minhasfinancas.services.impl.exceptions.RegraNegocioException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioResource.class)
@AutoConfigureMockMvc
@SpringJUnitConfig
public class UsuarioResourceTest {

    private static final String API = "/api/usuarios";
    private static final MediaType JSON = MediaType.APPLICATION_JSON;

    @Autowired
    MockMvc mvc;

    @MockBean
    UsuarioServices usuarioServices;

    @MockBean
    LancamentoServices lancamentoServices;

    UsuarioDTO usuarioDTO;    
    Usuario usuarioSalvo;
    @BeforeEach
    void criarDtoUsuario() {
        usuarioDTO = UsuarioDTO.builder().nome("Erico").senha("123").email("email@email.com").build();        
        usuarioSalvo = Usuario.builder().id(1L).nome("Erico").senha("123").email("email@email.com").build();
    }

    @Test
    void deveAutenticarRetornarUmUsuario() throws Exception{   
        String email = "email@email.com";
        String senha = "123";        

        Mockito.when(usuarioServices.autenticar(email, senha)).thenReturn(usuarioSalvo);

        String json = new ObjectMapper().writeValueAsString(usuarioDTO);     
         
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/autenticar")).accept(JSON).contentType(JSON).content(json);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(usuarioSalvo.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuarioSalvo.getNome()))
        .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuarioSalvo.getEmail()));             
    }

    @Test
    void deveRetornarUmNOT_FOUNDAoAutenticarUmUsuario() throws Exception{   
        String email = "email@email.com";
        String senha = "123";       

        Mockito.when(usuarioServices.autenticar(email, senha)).thenThrow(RecursoNaoEncontradoException.class);

        String json = new ObjectMapper().writeValueAsString(usuarioDTO);     
         
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/autenticar")).accept(JSON).contentType(JSON).content(json);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound());                 
    }
    
    @Test
    void deveCriarUmUsuarioNoBancoDeDados() throws Exception{             

        Mockito.when(usuarioServices.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuarioSalvo);

        String json = new ObjectMapper().writeValueAsString(usuarioDTO);     
         
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(JSON).contentType(JSON).content(json);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(usuarioSalvo.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuarioSalvo.getNome()))
        .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuarioSalvo.getEmail()));             
    }

    @Test
    void deveLancarUmaExcecaoAoTentarCriarUmUsuario() throws Exception{
       
        Mockito.when(usuarioServices.salvarUsuario(Mockito.any(Usuario.class))).thenThrow(RegraNegocioException.class);

        String json = new ObjectMapper().writeValueAsString(usuarioDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(JSON).contentType(JSON).content(json);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
        
    }
}
