package com.deliverytech.delivery;
import com.deliverytech.delivery.dto.restaurant.RestaurantDTO;
import com.deliverytech.delivery.entity.Restaurant;
import com.deliverytech.delivery.repository.IRestaurantRepository;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RestaurantControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IRestaurantRepository restauranteRepository;

    private RestaurantDTO restauranteDTO;
    private Restaurant restauranteSalvo;

    @BeforeEach
    void setUp() {
        restauranteRepository.deleteAll();

        restauranteDTO = new RestaurantDTO();
        restauranteDTO.setName("Pizza Express");
        restauranteDTO.setCuisine("Italiana");
        restauranteDTO.setAddress("Rua das Flores, 123");
        restauranteDTO.setPhone("11999999999");
        restauranteDTO.setCep("01310-000");
        //restauranteDTO.set(new BigDecimal("5.50"));
        //restauranteDTO.setTempoEntrega(45);
        //restauranteDTO.setHorarioFuncionamento("08:00-22:00");
        restauranteDTO.setCnpj("123322223432");

        // Criar restaurante para testes de busca
        Restaurant restaurante = new Restaurant();
        restaurante.setName("Burger King");
        restaurante.setCuisine("Americana");
        restaurante.setAddress("Av. Paulista, 1000");
        restaurante.setPhone("11888888888");
        restaurante.setCep("01310-000");
        restaurante.setLatitude(0.0);
        restaurante.setLongitude(0.0);
        restaurante.setCnpj("123322223432");
        //restaurante.setTaxaDeEntrega(null);
        //restaurante.setTempoEntrega(30);
        //restaurante.setHorarioFuncionamento("10:00-23:00");
        restaurante.setActive(true);
        restauranteSalvo = restauranteRepository.save(restaurante);
    }

    @Test
    void deveCadastrarRestauranteComSucesso() throws Exception {
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Pizza Express"))
                .andExpect(jsonPath("$.data.cuisine").value("Italiana"))
                .andExpect(jsonPath("$.data.active").value(true))
                .andExpect(jsonPath("$.message").value("Restaurante criado com sucesso"));
    }

    @Test
    void deveRejeitarRestauranteComDadosInvalidos() throws Exception {
        restauranteDTO.setName(""); // Nome inválido
        restauranteDTO.setPhone("123"); // Telefone inválido

        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.error.details.name").exists())
                .andExpect(jsonPath("$.error.details.phone").exists());
    }

    @Test
    void deveBuscarRestaurantePorId() throws Exception {
        mockMvc.perform(get("/api/v1/restaurants/{id}", restauranteSalvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(restauranteSalvo.getId()))
                .andExpect(jsonPath("$.data.nome").value("Burger King"))
                .andExpect(jsonPath("$.data.categoria").value("Americana"));
    }

    @Test
    void deveRetornar404ParaRestauranteInexistente() throws Exception {
        mockMvc.perform(get("/api/v1/restaurants/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("ENTITY_NOT_FOUND"));
    }

    @Test
    void deveListarRestaurantesComPaginacao() throws Exception {
        mockMvc.perform(get("/api/v1/restaurants")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.page.number").value(0))
                .andExpect(jsonPath("$.page.size").value(10))
                .andExpect(jsonPath("$.page.totalElements").value(1));
    }

    @Test
    void deveAtualizarRestauranteComSucesso() throws Exception {
        restauranteDTO.setName("Pizza Express Atualizada");

        mockMvc.perform(put("/api/v1/restaurants/{id}", restauranteSalvo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nome").value("Pizza Express Atualizada"))
                .andExpect(jsonPath("$.message").value("Restaurante atualizado com sucesso"));
    }

    @Test
    void deveAlterarStatusRestaurante() throws Exception {
        mockMvc.perform(patch("/api/v1/restaurants/{id}/status", restauranteSalvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.ativo").value(false))
                .andExpect(jsonPath("$.message").value("Status alterado com sucesso"));
    }

    @Test
    void deveBuscarRestaurantesPorCategoria() throws Exception {
        mockMvc.perform(get("/api/v1/restaurants/cuisine/{categoria}", "Americana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].categoria").value("Americana"));
    }
}