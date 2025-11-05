package com.deliverytech.delivery.control;


import com.deliverytech.delivery.dto.product.ProductDTO;
import com.deliverytech.delivery.dto.product.ProductResponseDTO;
import com.deliverytech.delivery.service.product.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
@Tag(name = "Produto", description = "Produto API")
public class ProductController {
    @Autowired
    private IProductService productService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/{restaurantId}")
    public ResponseEntity<List<ProductResponseDTO>> list(@PathVariable("restaurantId") Long restaurantId) {
        List<ProductResponseDTO> products = productService.getProductsByRestaurantId(restaurantId);
        return ResponseEntity.status(200).body(products);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping()
    @Operation(summary = "Cadastra um novo produto", description = "Cadastra um novo produto no restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    }
    )
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductDTO productDTO) {
        ProductResponseDTO product = productService.createProduct(productDTO);
        return ResponseEntity.status(201).body(product);
    }
}
