package System.App.entities;

import java.time.LocalDateTime;

public record Flight(
    Integer id,
    Integer planeId,
    Integer pilotId,
    Integer copilotId,
    String origin,
    String destination,
    LocalDateTime departureTime,
    Double duration
){}
