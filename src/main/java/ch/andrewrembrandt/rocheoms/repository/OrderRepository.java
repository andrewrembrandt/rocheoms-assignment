package ch.andrewrembrandt.rocheoms.repository;

import ch.andrewrembrandt.rocheoms.model.NewOrderDTO;
import ch.andrewrembrandt.rocheoms.model.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

public interface OrderRepository {
  Mono<Long> addOrder(NewOrderDTO dto, ZonedDateTime placedTime);

  Flux<Order> getOrdersBetween(ZonedDateTime from, ZonedDateTime to);
}
