package it.marconi.rubrica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<T> {
    private String status;  // "success", "fail", "error"
    private T data;         // i dati veri e propri o la mappa degli errori
    private String message; // usato principalmente per gli errori generici
}