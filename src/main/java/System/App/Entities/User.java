package System.App.Entities;

public record User(
        Integer id,
        String name,
        String password,
        String email,
        String phone,
        Boolean active

){}
