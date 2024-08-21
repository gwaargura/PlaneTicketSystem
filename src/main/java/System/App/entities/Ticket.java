package System.App.entities;

import java.time.LocalDateTime;

public record Ticket(
    Integer id,
    Integer purchaseId,
    Integer seat,
    String origin,
    String destination,
    LocalDateTime departureTime,
    Integer planeId,
    String gate,
    Integer flightId
){}
