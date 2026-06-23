# Relazione Tecnica - Evoluzione Applicazione Rubrica
**Studente:** Gian-Cara (Luca-Cristi)  
**Repository di partenza:** Progetto Personale / Fork base  

---

## 📝 Scelte Progettuali ed Evoluzione Architetturale

### Task 1: Standardizzazione delle Risposte e Centralizzazione degli Errori
Per evitare la frammentazione dei formati di risposta e impedire la fuga di stack trace interni verso il client, è stato implementato un contratto unico standardizzato basato sullo standard **JSend**:

* **`APIResponse<T>`**: Classe DTO generica creata nel pacchetto `it.marconi.rubrica.dto` per avvolgere uniformemente i payload di successo (`success`) e le mappe di fallimento (`fail`).
* **`ContactDTO`**: introdotto per isolare lo strato di persistenza (JPA) dall'esterno, evitando l'esposizione diretta delle entità del database sul controller.
* **`GlobalExceptionHandler`**: Realizzato tramite l'annotazione `@ControllerAdvice`. Centralizza la gestione eccezioni intercettando in modo mirato:
    * `MethodArgumentNotValidException` (Errore 400 Bad Request) restituendo una mappa dettagliata dei campi non validi.
    * `ResponseStatusException` (Errore 404 Not Found) per risorse non censite.

### Task 2: Containerizzazione Multi-Stage e Isolamento dei Profili
* **Dockerfile Multi-Stage**: Strutturato in due stadi distinti (`AS build` con Maven e JDK per la compilazione automatizzata e uno stadio di esecuzione basato su immagine minimale `Alpine JRE`) riducendo l'impronta di memoria dell'immagine finale.
* **Gestione Profili**: Configurato il file `application.properties` per supportare dinamicamente i profili `dev` e `prod`. In modalità `dev` il logging è impostato su `TRACE` con formattazione SQL di Hibernate. In modalità `prod` il logging passa a `INFO`.
* **Log Rotanti (`logback-spring.xml`)**: In produzione, i log vengono reindirizzati su un file rotante delegato a gestire la policy basata su tempo (giornaliero) e dimensione (max 10MB per singolo file).

### Task 3: Monitoraggio Proattivo e Sistemi di Alerting
* **Spring Boot Actuator & Micrometer**: Integrate le dipendenze nel `pom.xml` esponendo in modo controllato l'endpoint `/actuator/prometheus`.
* **Infrastruttura Docker Compose**: Esteso il file `docker-compose.yml` per orchestrare in background i container di **Prometheus** (per lo scraping regolare delle metriche della JVM) e **Grafana** per la visualizzazione.
* **Criteri di Alerting (PromQL)**: Configurata una Alert Rule su Grafana che monitora l'incremento temporale delle risposte HTTP 500, attivando lo stato di allarme (`Firing`) al superamento della soglia critica di 10 chiamate d'errore.