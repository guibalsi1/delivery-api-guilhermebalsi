package com.deliverytech.delivery.service.cep;

import com.deliverytech.delivery.dto.cep.CepLocationDTO;
import com.deliverytech.delivery.dto.cep.CepResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService implements ICepService{
    @Override
    public CepLocationDTO getCepLocation(String cep) {
        String url = "https://www.cepaberto.com/api/v3/cep?cep=" + cep;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token token=c0abbdc7a5e6c064a244572a6cfbccfc");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CepResponseDTO> response = restTemplate.exchange(
                url, HttpMethod.GET,
                new HttpEntity<>(headers),
                CepResponseDTO.class);

        CepResponseDTO cepResponseDTO = response.getBody();
        if(cepResponseDTO == null || cepResponseDTO.latitude() == null || cepResponseDTO.longitude() == null) {
            throw new RuntimeException("Could not find location for CEP: " + cep);
        }
        return new CepLocationDTO(Double.parseDouble(cepResponseDTO.latitude()),Double.parseDouble(cepResponseDTO.longitude()));
    }
}
