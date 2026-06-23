package it.marconi.rubrica.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class ContactDTO {
    private UUID id;
    private String name;
    private String surname;
    private String phone;
    private String email;
}