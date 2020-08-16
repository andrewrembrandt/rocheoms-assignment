package ch.andrewrembrandt.rocheoms.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class Order {
  private long id;
  private String buyerEmail;
  private ZonedDateTime placedTime;
}
