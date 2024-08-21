package System.App.entities;

import java.time.LocalDateTime;

public record Purchase(
        Integer id,
        Integer userId,
        LocalDateTime dateCreated,
        Boolean paid,
        Boolean expired
){}
