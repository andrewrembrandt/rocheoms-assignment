package ch.andrewrembrandt.rocheoms.repository;

import ch.andrewrembrandt.rocheoms.model.NewOrderDTO;
import ch.andrewrembrandt.rocheoms.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

import static org.springframework.data.relational.core.query.Criteria.where;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
  @Autowired private DatabaseClient client;

  @Override
  public Mono<Long> addOrder(NewOrderDTO dto, ZonedDateTime placedTime) {
    return client
        .insert()
        .into("customer_order")
        .value("buyer_email", dto.getBuyerEmail())
        .value("placed_time", placedTime)
        .map(r -> r.get(0, Long.class))
        .one();
  }

  @Override
  public Flux<Order> getOrdersBetween(ZonedDateTime from, ZonedDateTime to) {
    return client
        .select()
        .from("customer_order")
        .matching(
            where("placed_time").greaterThanOrEquals(from).and("placed_time").lessThanOrEquals(to))
        .as(Order.class)
        .fetch()
        .all();
  }
}
