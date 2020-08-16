package ch.andrewrembrandt.rocheoms.repository;

import ch.andrewrembrandt.rocheoms.model.ProductDTO;
import ch.andrewrembrandt.rocheoms.model.ProductDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Criteria.where;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
  @Autowired private DatabaseClient client;

  @Override
  public Flux<ProductDTO> findAllActiveProductDTO() {
    return client
        .select()
        .from("product")
        .matching(where("deleted").isFalse())
        .as(ProductDTO.class)
        .fetch()
        .all();
  }

  @Override
  public Mono<Integer> addProduct(String sku, ProductDataDTO dto) {
    return client
        .execute(
            "insert into product(sku, name, price, creation_date, deleted) "
                + "values(:sku, :name, :price, :creation_date, false) on conflict do nothing")
        .bind("sku", sku)
        .bind("name", dto.getName())
        .bind("price", dto.getPrice())
        .bind("creation_date", dto.getCreationDate())
        .fetch()
        .rowsUpdated();
  }

  @Override
  public Mono<Integer> updateBySku(String sku, ProductDataDTO dto) {
    return client
        .update()
        .table("product")
        .using(
            Update.update("name", dto.getName())
                .set("price", dto.getPrice())
                .set("creation_date", dto.getCreationDate()))
        .matching(where("sku").is(sku))
        .fetch()
        .rowsUpdated();
  }

  @Override
  public Mono<Integer> softDeleteBySku(String sku) {
    return client
        .update()
        .table("product")
        .using(Update.update("deleted", true))
        .matching(where("sku").is(sku).and("deleted").is(false))
        .fetch()
        .rowsUpdated();
  }
}
