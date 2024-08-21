package System.App.entities;

public record Pilot(
    Integer id,
    String name,
    Integer age,
    Double totalHourFlew,
    Boolean active
){}
