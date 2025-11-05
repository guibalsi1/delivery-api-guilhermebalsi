package com.deliverytech.delivery.service.cep;

import com.deliverytech.delivery.dto.cep.CepLocationDTO;
import org.springframework.stereotype.Service;

@Service
public interface ICepService {
    public CepLocationDTO getCepLocation(String cep);
}
