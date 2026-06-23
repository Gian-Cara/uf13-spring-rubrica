package it.marconi.rubrica.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import it.marconi.rubrica.domain.Contact;
import it.marconi.rubrica.domain.ContactForm;
import it.marconi.rubrica.dto.APIResponse;
import it.marconi.rubrica.dto.ContactDTO;
import it.marconi.rubrica.services.ContactService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contacts") // Endpoint base per i contatti
public class ContactController {
    
    @Autowired
    private ContactService contactService;

    // 1. Prendi tutti i contatti 
    @GetMapping
    public APIResponse<List<ContactDTO>> showContactList() {
        List<ContactDTO> dtos = contactService.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
            
        return new APIResponse<>("success", dtos, null);
    }

    // 2. Salva un nuovo contatto 
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<ContactDTO> handleNewContact(@RequestBody @Valid ContactForm contactForm) {
        // Abbiamo tolto BindingResult: se ci sono errori ci pensa il GlobalExceptionHandler (400 Bad Request)
        Contact savedContact = contactService.save(contactForm);
        return new APIResponse<>("success", convertToDTO(savedContact), null);
    }

    // 3. Prendi un singolo contatto tramite ID nell'URL 
    @GetMapping("/{id}")
    public APIResponse<ContactDTO> showContact(@PathVariable("id") UUID contactId) {
        Contact contact = contactService.get(contactId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contatto non trovato"));
            
        return new APIResponse<>("success", convertToDTO(contact), null);
    }

    // 4. Elimina un contatto
    @DeleteMapping("/{id}")
    public APIResponse<Void> deleteContact(@PathVariable("id") UUID contactId) {
        contactService.deleteById(contactId);
        return new APIResponse<>("success", null, null);
    }

    // Metodo di comodo per trasformare l'Entità in DTO 
    private ContactDTO convertToDTO(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setName(contact.getName());
        dto.setSurname(contact.getSurname());
        dto.setPhone(contact.getPhone());
        dto.setEmail(contact.getEmail());
        return dto;
    }
}