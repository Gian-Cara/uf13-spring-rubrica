# Spring Rubrica

Applicazione Spring per la gestione di una rubrica di contatti tramite database MySQL su container Docker

## Dependencies
- Spring Web (sviluppo webapp -> pagine HTML)
- Spring DevTools (opzionale, gestisce il riavvio rapido dell'applicazione)
- Thymeleaf (templating HTML per pagine dinamiche)
- Spring Data JPA (tecnologia di gestione dei database)
- Driver MySQL (connettore al DBMS) 
- Validator (validazione dei form)
- Lombok (boilerplate code)
- Webjar Locator (gestione pacchetti)
- Bootstrap

Per aggiungere nuove dependencies usare l'ozione `Spring Initializr: Add starters...`

## Avvio applicazione

1. Creazione del container per il database specificato in `docker-compose.yaml`:

```bash
docker compose up mysql-db [-d]
```

2. Verificare il corretto avvio del container:

```bash
docker compose ps
```

3. Terminare l'esecuzione del container:

```bash
docker compose down
```

## Variabili d'ambiente

Si possono aggiungere variabili d'ambiente Docker in 2 modi (visibili solo dai container):
- via `docker-compose.yaml`
- via `.env`

Per aggiungere variabili d'ambiente in "locale" nel Codespace:
- GitHub Repository -> Settings -> Secrets and variables -> Codespaces

Per verificare le variabili d'ambiente note a un container:

```bash
docker exec -it <container-id> env
```

Per verificare le variabili d'ambiente note a Codespace:

```bash
echo $NOME_VARIABILE
```

## Accesso al database

1. Accesso al container e inserimento credenziali (verrà chiesto di inserire la password):

```bash
docker exec -it mysql_container mysql -u root -p
```

2. Selezionare il database:

```sql
use rubrica_db;
```

3. Mostrare le tabelle:

```sql
show tables;
```

4. Mostrare gli attributi di una tabella:

```sql
describe contacts;
```

5. Interrogare il database via query:

```sql
SELECT * FROM contacts;
```
```sql
INSERT INTO tabella (...) VALUES (...);
```

6. Chiudere il terminale del container:

```bash
exit
```
---
## 📑 Documentazione d'Esame
La relazione tecnica ufficiale che descrive le scelte progettuali, i criteri di accettazione e l'architettura delle Task sviluppate è disponibile al seguente link:
* [Leggi la Relazione Tecnica (docs/RELAZIONE.md)](docs/RELAZIONE.md)