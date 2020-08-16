package ch.andrewrembrandt.rocheoms.repository;

import ch.andrewrembrandt.rocheoms.model.ProductDTO;
import ch.andrewrembrandt.rocheoms.model.ProductDataDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
  Flux<ProductDTO> findAllActiveProductDTO();

  Mono<Integer> addProduct(String sku, ProductDataDTO dto);

  Mono<Integer> updateBySku(String sku, ProductDataDTO dto);

  Mono<Integer> softDeleteBySku(String sku);
}
