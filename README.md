# Modul183_Leona
(Falls sie es testen möchten und einen Docker installieren möchten, können sie das Setup dafür weiter unten finden. Es gibt bereits einen Admin user, falls Sie diesen ebenfalls testen möchten können Sie es hiermit machen: 
username: admin
email: admin@test.com
passwort: admin.

Alle anderen User müssen/sollen sie selbst erstellen für den ultimativen test. Danke!)
## Projektbeschreib
<p> Ich mache eine Website wo man Kleider verkaufen kann. Man kann sich registrieren und einloggen. Die Passwörter werden sicher gespeichert mit Salt & Pepper. Das Backend mache ich mit Java und das Frontend mit TypeScript. In der Datenbank werden der Benutzername und das gehashte Passwort gespeichert. Wenn man eingeloggt ist kann man Kleider hochladen mit Name, Zustand, Grösse, Preis, Beschreibung und einem Bild.Ich mache eine Website wo man Kleider verkaufen kann. Man kann sich registrieren und einloggen. Die Passwörter werden sicher gespeichert mit Salt & Pepper und bcrypt. Das Backend mache ich mit Java und das Frontend mit TypeScript. In der Datenbank werden der Benutzername und das gehashte Passwort gespeichert. Wenn man eingeloggt ist kann man Kleider hochladen mit Name, Zustand, Grösse, Preis, Beschreibung und einem Bild. </p>

## Zeitplan 
| Name                             | Beschreibung                                                                                                                     | Zeit (h)   |
|----------------------------------|----------------------------------------------------------------------------------------------------------------------------------|------------|
| ~~Projektplanung~~               | ~~Architektur planen und Technologien festlegen. Datenbankstruktur skizzieren.~~                                                 | ~~0.5~~    |
| ~~Projekt aufsetzen~~            | ~~Spring Boot und React Projekt erstellen. Ordnerstruktur und Dependencies einrichten.~~                                         | ~~1~~      |
| ~~Datenbank aufsetzen~~          | ~~PostgreSQL installieren und konfigurieren. Tabellen für users, listings und images erstellen.~~                                | ~~1.5~~    |
| ~~User Entity & Repository~~     | ~~Java Entity-Klassen für den User erstellen. JDBC Repository einrichten.~~                                                      | ~~1~~      |
| ~~Registrierung Backend~~        | ~~Register-Endpunkt implementieren mit Salt generieren und bcrypt Hashing. Benutzername und Hash in DB speichern.~~              | ~~2~~      |
| ~~Login Backend~~                | ~~Login-Endpunkt implementieren und Passwort mit Hash vergleichen. JWT-Token zurückgeben.~~                                      | ~~2~~      |
| ~~Listing Entity & Repository~~  | ~~Java Entity für Inserate erstellen mit allen Feldern. JPA Repository und Service einrichten.~~                                 | ~~1~~      |
| ~~Listing CRUD Backend~~         | ~~Endpunkte für erstellen, abrufen und löschen von Inseraten. Nur eingeloggte User dürfen inserieren.~~                          | ~~2~~      |
| ~~Bild-Upload Backend~~          | ~~Multipart Endpunkt für Bild-Upload implementieren. Datei lokal speichern und Pfad in DB speichern.~~                           | ~~2~~      |
| ~~API testen~~                   | ~~Alle Endpunkte mit Postman testen. Fehler beheben.~~                                                                           | ~~1~~      |
| ~~Login & Register Frontend~~    | ~~Loginseite und Registrierungsseite in React bauen. Formular mit API verbinden.~~                                               | ~~2~~      |
| ~~Navbar implementieren~~        | ~~Einfache Navigation mit Links erstellen. Eingeloggten User anzeigen.~~                                                         | ~~1~~      |
| ~~Cards im Frontend~~            | ~~Post-Cards auf der Startseite implementieren. Bild, Name und Preis anzeigen.~~                                                 | ~~1.5~~    |
| ~~Post erstellen Frontend~~      | ~~Formular für neue Poosts bauen. Alle Felder und Bild-Upload einbinden.~~                                                       | ~~2~~      |
| ~~Detailseite Frontend~~         | ~~Einzelne Detailseite pro Inserat erstellen. Alle Infos und das Bild anzeigen.~~                                                | ~~1~~      |
| Das wird im **BLOCK 6** gemacht: |
| ~~Navigation verbessern~~        | ~~Die Navigation bei der Registrierung sollte verbessert werden, nach dem Registrieren direkt eingeloggt sein~~                  | ~~0.5~~    |
| ~~Verbesserungen~~               | ~~Verbesserungen von herr van essen implementieren~~                                                                             | ~~1~~      |
| ~~User Profile hinzufügen~~      | ~~Der User sollte seine eigene user Profile Page haben wo er username/passwort ändern kann und ein PB hinzufügen kann.~~         | ~~1.5~~    |
| ~~Anmeldung mit Email~~          | ~~Man sollte sich auch mit seiner E-mail einloggen, statt nur mit dem username.~~                                                | ~~0.5~~    |
| Das wird im **BLOCK 7** gemacht: |
| ~~Warenkorb~~                        | ~~Falls man ein Inserat von andern kaufen will, kann man es in den Warenkorb tun. Wenn man es gekauft hat verschwindet das Inserat~~ | ~~2~~          |
| ~~Styling~~                          | ~~Grundlegendes CSS für alle Seiten. Website übersichtlich und sauber gestalten.~~                                                 | ~~1.5~~        |
| ~~Profilbild~~                       | ~~Man sollte, in der Navbar, sein Profilbild sehen.~~                                                                              | ~~0.5~~        |
| ~~Validierung~~                      | ~~beim registrieren sollte alles pflichtangaben sein. Beim ändern von passwort oder benutzername, darf es nicht derselbe username oder dasselbe passwort sein~~ | ~~0.5~~        |
| ~~Letzter Test & Bugfixing~~         | ~~Gesamte App durchklicken und Fehler beheben. Sicherstellen dass alles funktioniert.~~                                              | ~~1~~          |
| ~~Letzter Commit & Doku~~            | ~~Code aufräumen und kommentieren. README schreiben und auf GitHub pushen.~~                                                         | ~~1~~          |
| **Total**                        |                                                                                                                                  | **30.5 h** |

## Arbeitsjournal 
| Datum      | Zeit (h)                                                                                      | Was wurde erledigt                                                                                                                                                                                                                                                                                                                   |
|------------|-----------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 08.06.2026 | 13:55-14:30<br/>14:30-15:00<br/> 15:00-15:30<br/>15:30-16:30<br/> 16:30-17:00<br/>17:00-17:35 | Einführung in den Unterricht und Videos schauen.<br/>Ich habe meine Projektplanung im README.md festgelegt<br/> Pause <br/>Ich habe mein Projekt aufgesetzt<br/> ich habe meine datenbank aufsetzen.<br/> Angefangen meine Java user entity klassen zu erstellen.                                                                    |
| 10.06.2026 | 19:00-21:00<br/>21:00-23:00                                                                   | Angefangen meine Register Points zu implementieren<br/>Ich habe den Login Endpunkt implementiert                                                                                                                                                                                                                                     |
| 11.06.2026 | 19:00-21:10<br/>21:10-22:30                                                                   | Ich habe angefangen an den Inseraten zu arbeiten und die Felder zu erstellen.<br/> Ich habe anefangen alle Endpunke für die CRUD Operatoren zu schreiben.                                                                                                                                                                            |
| 12.06.2026 | 17:00-19:15<br/>19:20-21:45                                                                   | Ich habe angefangen an dem Bild-upload zu arbeiten.<br/>Ich habe angefangen meine API's zu testen und alle fehler zu beheben                                                                                                                                                                                                         |
| 13.06.2026 | 17:00-18:30                                                                                   | Ich habe angefangen die Registrierung und das Login in meinem Frontend zu implementieren.                                                                                                                                                                                                                                            |
| 15.06.2026 | 13:55-14:55<br/>14:55-15:30<br/>15:30-17:00<br/>17:00-17:35                                   | Kleine Einführung zum heutigen Tag gehabt, 2 Videos geschaut und meine Navabr implementiert.<br/> Ich habe nun die Implementation gebaut, dass all meien Posts auf der /listings seite sehen kann.<br/> Daran gearbeitet, dass man Posts erstellen kann, favorisieren kann und auf öffentlich/privat setzen kann<br/>Videos geschaut |
| 22.06.2026 | 13:55-14:55<br/>14:55-15:55<br/>15:55-16:25<br/>16:25-17:35                                   | Navigation verbessern, sodass ich nach dem Registrieren direkt eingeloggt werde. <br/> Verbesserung's Vorschläge von Herr van essen implementiert (docker und env.exmple für besseres Testing)<br/>Meinen code so umgeschriebeen, dass man sich jetzt auch mit der email anmelden kann.<br/>Eine "Profile User" page erstellt.       |
| 29.06.2026 | 13:55-14:55<br/>15:15-15:40<br/>15:40-16:05<br/>16:05-16:35<br/>16:35-16:45<br/>16:45-17:15   | Videos geschaut und an meinem Warenkorb gearbeitet<br/>Profilbild wird nun in der navbar angezeigt<br/>Validierung hinzugefügt<br/>Alle px zu rem geändert im styling<br/>Durchgeklickt und nach grossen bugs gesucht<br/>ReadMe noch beabreitet, letzten Commit gemacht und letztes Video geschaut                                  |

----------------------------------------------------------------------------------------------------------------
# Projektplanung

## Kleidungsverkaufsportal
<p> Ich kreiere eine einfache Website wo man Kleider verkaufen kann. Man kann sich registrieren und einloggen. Das Backend ist mit Java (Maven) und das Frontend mit TypeScript (React). Passwörter werden sicher mit Salt & Pepper und bcrypt gespeichert.</p>

## Technologiestack
- **Frontend:** TypeScript, React
- **Backend:** Java 26, Maven
- **Datenbank:** PostgreSQL
- **Sicherheit der Passwörter:** bcrypt, Salt & Pepper

## Architektur
<p> Das Projekt ist aufgeteilt in drei Teile: </p>

- **Frontend** kommuniziert über REST-API mit dem Backend
- **Backend** verwaltet die Logik, Authentifizierung und Datenbankzugriffe
- **Datenbank** speichert Benutzer, Inserate und Bilder

## Datenbankstruktur
**users**
- id, username, email, salt, password_hash

**listings**
- id, user_id, name, zustand, grösse, preis, beschreibung

**images**
- id, listing_id, filepath

## Funktionen
- Registrierung und Login mit sicherer Passwortspeicherung
- Inserat erstellen mit Name, Zustand, Grösse, Preis, Beschreibung und Bild
- Alle Inserate auf der Startseite anzeigen
- Detailseite pro Inserat

## Sicherheit
<p> Passwörter werden mit einem zufälligen Salt und einem geheimen Pepper kombiniert und dann mit bcrypt gehasht. In der Datenbank wird nur der Hash und der Salt gespeichert, nie das Originalpasswort.</p>

----------------------------------------------------------------------------------------------------------------

# Projekt aufsetzen

## Ordnerstruktur
VerkaufsPage/

- .idea/
- .mvn/

- frontend/
    - src/
        - components/
        - pages/
        - App.tsx
    - public/
    - package.json

- src/
    - main/
        - java/
            - com/
                - kleidung/
                    - Main.java
                    - DatabaseConnection.java

- pom.xml
- README.md

## Backend (Plain Java mit Maven)
1. In dem IntelliJ, was mit dem Repo verbunden ist, gehen wir auf File -> New -> Module
2. Dort fülle ich folgendes aus:
    - Name: VerkaufsPage
    - JDK 26
3. Danach klicke ich auf finish.

<p> In meinem pom.xml file, das nun erstellt wurde, füge ich diese Dependencies dazu: </p>
<dependencies>
 <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.7.3</version>
    </dependency>

 <dependency>
        <groupId>at.favre.lib</groupId>
        <artifactId>bcrypt</artifactId>
        <version>0.10.2</version>
    </dependency>

</dependencies>

## Frontend (React + TypeScript)
1. Auf https://nodejs.org/ sich node.js herunterladen und danach installieren
2. IntelliJ neu starten
3. Im Terminal auf cd VerkaufsPage/ wechseln und dort diese Commands eingeben:
    - npx create-react-app frontend --template typescript
    - cd frontend
    - npm install axios react-router-dom

## Branches erstellen
- git branch develop
- git branch feature/auth
- git branch feature/listings
- git branch feature/image-uploads
- git branch feature/frontend
- git branch feature/verbesserungen

## Branch Strategie:
| Name                  | Beschreibung                                                                                                  
|-----------------------|---------------------------------------------------------------------------------------------------------------
| main                  | Nur fertiger, funktionierender Code                                  
| develop               | Hauptentwicklungsbranch                         
| feature/auth          | Registrierung und Login                
| feature/listings      | Inserate CRUD Operatoren                                        
| feature/image-uploads | Bild Upload
| feature/frontend      | Alle React Seiten 
| feature/verbesserungen| Verbesserungen welche von Herr van Essen erwähnt worden

----------------------------------------------------------------------------------------------------------------

# Datenbank aufsetzen
## PostgreSQL installieren
1. Ich ging auf https://www.postgresql.org/download/ und wählte Windows aus
2. Ich habe den Installer heruntergeladen und gestartet
3. Ich habe alles auf Standard gelassen und mir mein **Passwort** gemerkt
4. Der Port bleibt auf 5432

## Datenbank erstellen
<p> Nach der Installation öffne ich **SQL Shell (psql)** und gebe folgendes ein. (Ich drücke bei allem Enter ausser beim Passwort):</p>

- Server [localhost]:
- Database [postgres]:
- Port [5432]:
- Username [postgres]:
- Password: meinPasswort

<p> Danach erstelle ich meine Datenbank namens kleidungsshop mit diesem Command: </p>

```sql
CREATE DATABASE kleidungsshop;
\c kleidungsshop
```

<p> Nun erstelle ich eine Tabelle für die users, listings und images, welche ich später brauchen werde. </p>

```sql
-- Tabelle für die users --
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    salt VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabelle für die listings -- 
CREATE TABLE listings (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    name VARCHAR(100) NOT NULL,
    zustand VARCHAR(50) NOT NULL,
    groesse VARCHAR(20) NOT NULL,
    preis DECIMAL(10,2) NOT NULL,
    beschreibung TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabelle für die images --
CREATE TABLE images (
    id SERIAL PRIMARY KEY,
    listing_id INT REFERENCES listings(id),
    filepath VARCHAR(255) NOT NULL
);
```

----------------------------------------------------------------------------------------------------------------

# User Entity Klassen erstellen
## Datenbankverbindung

Die Datenbankverbindung wird in der Klasse `DatabaseConnection.java` verwaltet.
Sie stellt eine Verbindung zur PostgreSQL Datenbank her und gibt ein `Connection` Objekt zurück.

### Verbindungsparameter
- **Host:** localhost
- **Port:** 5432
- **Datenbank:** kleidungsshop
- **User:** postgres

### Nutzung
```java
Connection conn = DatabaseConnection.getConnection();
```

> **Wichtig:** Das Datenbankpasswort wird in einer `.env` Datei gespeichert und nie direkt im Code geschrieben. Die `.env` Datei ist in der `.gitignore` eingetragen und wird nicht auf GitHub gepusht.

----------------------------------------------------------------------------------------------------------------

# Registrierung im Backend
<p> Wenn sich ein User registriert, wird sein Passwort sicher gespeichert. Dafür wird ein zufälliger Salt generiert und zusammen mit einem geheimen Pepper mit bcrypt gehasht.</p>

## Ablauf
1. User gibt Username, Email und Passwort ein
2. Ein zufälliger Salt wird generiert
3. Passwort wird mit Salt und Pepper kombiniert und mit bcrypt gehasht
4. Username, Salt und Hash werden in der Datenbank gespeichert
5. Das Originalpasswort wird nie gespeichert

## Sicherheit
- **Salt** wird zufällig pro User generiert und in der DB gespeichert
- **Pepper** ist ein geheimer Schlüssel der nur in der `.env` Datei steht
- **bcrypt** ist der Hashing-Algorithmus

----------------------------------------------------------------------------------------------------------------

# Login Endpunkt
<p> Wenn sich ein User einloggt, wird das eingegebene Passwort mit dem gespeicherten Hash verglichen.
Wenn das gelingt, wird ein JWT Token zurückgegeben, dieser wird für weitere Anfragen verwendet.</p>

## Ablauf
1. User gibt Username und Passwort ein
2. User wird anhand vom Username aus der Datenbank geholt
3. Passwort wird mit Salt und Pepper kombiniert und mit bcrypt verglichen
4. Wenn das geht, wird ein JWT Token generiert und zurückgegeben
5. Wenn das nicht funktioniert, wird ein 401 Fehler zurückgegeben

## Sicherheit
- **JWT-Token** ist 24h gültig
- **JWT-Secret** steht nur in der `.env` Datei
- Das Originalpasswort wird nie gespeichert oder verglichen

----------------------------------------------------------------------------------------------------------------

# Listing Entity und Repository
<p> Ein Inserat enthält alle Informationen zu einem Kleidungsstück, welches zum Verkauf bereit ist. Alle Daten der Inserate werden in der Datenbank gespeichert.</p>

## Felder
- **Name:** Name des Kleidungsstücks
- **Zustand:** Zustand vom Kleidungsstück (Gut, Schlecht, leicht beschädigt etc.)
- **Grösse:** Grösse vom Kleidungsstück (S, M, L)
- **Preis:** Preis in CHF
- **Beschreibung:** Zusätzliche Beschreibung z.B. es ist von den 2000ern

## Repository Methoden
- `save()` - Inserat in der Datenbank speichern
- `findAll()` - Alle Inserate anzeigen lassen
- `findById()` - Spezifisches Inserat suchen
- `delete()` - Ein Inserat löschen

----------------------------------------------------------------------------------------------------------------

# Endpunkte für die Inserate
<p> Alle meine Endpunkte für die Inserate sind unter `/listings` zu finden. Das Erstellen und Löschen von Inseraten ist nur für eingeloggte User erlaubt.</p>

## Vorherige Fehler beheben
<p> Mir ist aufgefallen, dass ich in der letzten Aufgabe keine "put/update" Methode implementiert hatte, weshalb ich dies nachgeholt habe.
Was ich auch bemerkt hatte, war, dass ich in meiner Datenbank Tabelle keine Zeile für die verschiedenen Rollen hatte, dies wurde mit diesem Command behoben: </p>

```sql
ALTER TABLE users ADD COLUMN role VARCHAR(20) DEFAULT 'user';
```

## Endpunkte
- `GET /listings` - Alle Inserate abrufen (man muss nicht eingeloggt sein)
- `POST /listings` - Neues Inserat erstellen (man muss eingeloggt sein)
- `PUT /listings/{id}` - Inserat bearbeiten (man muss User oder Admin sein)
- `DELETE /listings/{id}` - Inserat löschen (man muss User oder Admin sein)

## Berechtigungen
- **User:** kann nur seine eigenen Inserate löschen oder bearbeiten
- **Admin:** kann alle Inserate löschen oder bearbeiten

## Service Methoden
- `create()` - Neues Inserat erstellen
- `getAll()` - Alle Inserate aufrufen
- `getById()` - Ein spezifisches Inserat aufrufen
- `update()` - Ein Inserat bearbeiten
- `delete()` - Ein Inserat löschen

## Authentifizierung
<p> Für POST, PUT und DELETE muss ein JWT-Token im Header mitgeschickt werden.</p>

## Fehlercodes
- `401` - Nicht eingeloggt oder ungültiger Token
- `403` - Keine Berechtigung
- `404` - Das Inserat wurde nicht gefunden
- `500` - Fehler in der Datenbank/beim Speichern

----------------------------------------------------------------------------------------------------------------
# Bild Upload
<p> Bilder können pro Inserat hochgeladen werden. Die Datei wird lokal auf dem Server gespeichert.</p>

## Endpunkt
- `POST /listings/{id}/image` - Bild für ein Inserat hochladen

## Ablauf
- Bild wird als Datei im Request Body mitgeschickt
- Ein Dateiname wird generiert
- Datei wird lokal im `uploads/` Ordner gespeichert
- Dateipfad wird in der Datenbank gespeichert

----------------------------------------------------------------------------------------------------------------

# APIs testen
<p> Alle Endpunkte wurden mit Postman getestet und alle Fehler wurden behoben.</p>

## Getestete Endpunkte
- `POST /register` - Registrierung
- `POST /login` - Eingeloggt und den JWT Token erhalten
- `GET /listings` - Alle Inserate abgerufen
- `POST /listings` - Inserat erstellt
- `PUT /listings/{id}` - Inserat bearbeitet
- `POST /image/{id}` - Bild hochladen
- `DELETE /listings/{id}` - Inserat löschen

## Authentifizierung in Postman
<p> Der JWT Token wurde vom Login kopiert und bei den geschützten Endpunkten im Header mitgeschickt.</p>

## Gefundene Fehler und die Fixes
**1. .env Datei nicht gefunden**
<p> Der Server hat meine `.env` Datei nicht gefunden, weil das Working Directory falsch war. </p>

**Fix**
<p> Ich ging auf -> Run -> Edit Configuration -> Add new Configuration -> Application, Main und danach dort alle Informationen eingegeben. </p>

**2. URL Encoded Parameter mit Leerzeichen**
<p> Postman hat die Keys mit `%20` am Ende geschickt (bsp: `username%20=testuser`), weshalb die Parameter nicht gelesen wurden.</p>

**Fix**
<p> In der `extractParam` Methode `URLDecoder.decode()` und `.trim()` auf den Key angewendet.</p>

**3. JWT Token hängt den Server auf**
<p> Die alte `jjwt` Version war nicht kompatibel mit Java 26 und hat den Server zum Absturz gebracht.</p>

**Fix**
<p> Die Dependency gewechselt und die API entsprechend angepasst.</p>

**4. Inserat löschen schlug fehl wegen dem Fremdschlüssel**
<p> Ein Inserat konnte nicht gelöscht werden, weil noch ein Bild in der `images` Tabelle darauf war.</p>

**Fix**
<p> In der `ListingRepository.delete()` werden alle Bilder vom Inserat gelöscht, danach das Inserat selbst.</p>

**5. Image Handler wurde nicht aufgerufen**
<p> Der Context `/listings` hat alle Anfragen abgefangen, auch `/listings/{id}/image`.</p>

**Fix**
<p> Den Image Endpunkt auf `/image/{id}` geändert und vor `/listings` registriert.</p>

**6. Too many bytes to write to stream**
<p> Die Response Länge wurde mit `message.length()` berechnet, was bei Sonderzeichen falsch war.</p>

**Fix**
<p> Überall `message.getBytes("UTF-8")` verwendet und `bytes.length` für die Header-Länge gesetzt.</p>

## Postman Collection
<p> Die Postman Collection mit allen Tests ist im Repository zu finden und kann direkt in Postman importiert werden.</p>

----------------------------------------------------------------------------------------------------------------

# Login und Registrierung im Frontend
<p> Ich habe das Login und die Registrierung in React mit TypeScript gebaut. Alle Formulare sind mit dem Backend verbunden und auch responsiv gestylt.</p>

## Atomic Design
<p> Ich habe mein Frontend mit dem Atomic Design Prinzip gebaut:</p>

- **Atoms** - kleinste UI Elemente wie `Button`, `Input` und `ErrorMessage`
- **Molecules** - Kombination von Atoms wie `Login- /RegisterForm`
- **Pages** - die Seiten welche alle Molecules zusammensetzen

## Styling
- `global.css` - Globales Styling für den ganzen Body
- `atoms.css` - Styling für alle Atoms
- `molecules.css` - Styling für alle Molecules

## Responsiv
<p> Ich habe die Seiten so gestylt, dass sie sich automatisch an die verschiedenen Bildschirmgrössen anpassen.</p>

## Seiten
- `/login` - Loginseite mit dem Username und Passwort
- `/register` - Registrierungsseite wo man sich registrieren kann
- `/listings` - Hier werden später alle Inserate angezeigt, im Moment steht dort nur "Willkommen!" als Platzhalter.

## Ablauf Login
1. User gibt Username und Passwort ein
2. Anfrage wird an `POST /login` geschickt
3. JWT-Token wird im `localStorage` gespeichert
4. User wird auf `/listings` weitergeleitet

## Ablauf Registrierung
1. User gibt Username, Email und Passwort ein
2. Anfrage wird an `POST /register` geschickt
3. Bei Erfolg wird eine Erfolgsmeldung angezeigt
4. User kann sich dann einloggen

## Gefundene Fehler
**CORS Fehler:** Der Browser hat Anfragen von `localhost:3000` zu `localhost:8080` blockiert.
**Fix:** `CorsUtil.java` erstellt und in allen Handlers eingebaut.

----------------------------------------------------------------------------------------------------------------

# Navbar
<p> Simple Navbar über der Page.</p>

## Funktionen
- Link zu den Inseraten
- Zeigt an welcher User eingeloggt ist
- Logout Button
- Herz Icon, um die favorisierten Inserate anzuzeigen

## Responsiveness
<p> Das Design wurde so implementiert, dass es responsiv ist.</p>

----------------------------------------------------------------------------------------------------------------
# Inserat Cards

<p> Auf der Startseite werden alle Inserate als Cards angezeigt mit Bild, Name und Preis. Ein Klick auf eine Card öffnet eine Detailansicht.</p>

## Berechtigung
- Alle User sehen die anderen Inserate
- Ein normaler User kann nur seine eigenen Inserate bearbeiten oder löschen
- Ein Admin kann alle Inserate bearbeiten oder löschen

## Bearbeiten
<p> Im Detailmodus kann ein berechtigter User auf "Bearbeiten" klicken, die Felder ändern und dann speichern.</p>

## Löschen
<p> Mit "Löschen" wird das Inserat gelöscht.</p>

----------------------------------------------------------------------------------------------------------------

# Inserat erstellen über + Button und Detaillistenansicht

<p> Auf der `/listings` Seite gibt es unten rechts einen `+` Button. Ein Klick öffnet eine Karte mit allen Feldern, welche man ausfüllen kann.</p>

## Felder
- Name
- Zustand
- Grösse
- Preis
- Beschreibung
- Bild
- Sichtbarkeit (Öffentlich/Privat)

## Ablauf
1. User füllt das Formular in der Karte aus
2. Daten werden per `POST /listings` an das Backend geschickt
3. Falls ein Bild ausgewählt wurde, wird es per `POST /image/{id}` zum neu erstellten Inserat hochgeladen
4. Das neue Inserat erscheint sofort in der Übersicht

## Öffentlich / Privat
<p> Jedes Inserat hat eine Sichtbarkeit, die beim Erstellen oder Bearbeiten gesetzt werden kann.</p>

## Sichtbarkeit
- **Öffentlich:** Das Inserat ist für alle User sichtbar
- **Privat:** Das Inserat ist nur für den Ersteller sichtbar

## Detailseite / Detail-Modal
<p>Ein Klick auf eine Inserat Card öffnet eine Detailkarte mit allen Informationen zum Inserat.</p>

## Angezeigte Informationen
- Bild
- Name
- Preis

## Berechtigungen
- Alle User können die Details eines öffentlichen Inserats sehen
- Bearbeiten- und Löschen Buttons werden nur angezeigt wenn der eingeloggte User der Besitzer ist oder Admin-Rechte hat

## Bearbeiten
<p>In der Karte kann ein berechtigter User auf "Bearbeiten" klicken. Die Felder werden zu Eingabefeldern und können mit "Speichern" per `PUT /listings/{id}` aktualisiert werden.</p>

## Löschen
<p> Mit "Löschen" wird das Inserat per `DELETE /listings/{id}` entfernt und die Karte schliesst sich automatisch.</p>

## Favoriten
<p>Eingeloggte User können öffentliche Inserate anderer User favorisieren.</p>

## Funktionsweise
- Auf jeder Card eines öffentlichen Inserats (das nicht dem eigenen User gehört) wird ein Herz-Icon angezeigt
- Ein Klick auf das Herz fügt das Inserat zu den Favoriten hinzu oder entfernt es wieder
- Unter `/favorites` (Herz Icon in der Navbar) sieht man alle favorisierten Inserate

## Datenbank
- Eine neue Tabelle `favorites` speichert die Verknüpfung zwischen `user_id` und `listing_id`.
- Eine `UNIQUE` Constraint verhindert doppelte Favoriten.

## Endpunkte
- `GET /favorites` – Alle favorisierten Inserate des eingeloggten Users abrufen
- `POST /favorites/{listingId}` – Favorit hinzufügen oder entfernen

## Berechtigungen
- Nur eingeloggte User können favorisieren
- Eigene Inserate können nicht favorisiert werden (kein Herz Icon)

## Gefundene Fehler und Fixes
**CORS Fehler bei /favorites**
Der `FavoriteHandler` hatte keine CORS-Header gesetzt, wodurch der Browser alle Anfragen an `/favorites` blockierte.
**Fix:** `CorsUtil.addCorsHeaders()` und `CorsUtil.handleOptions()` in `FavoriteHandler.java` eingebunden und den Context `/favorites` in `Main.java` registriert.

**CORS Fehler beim Erstellen von Inseraten (Access-Control-Allow-Origin enthält mehrere Werte)**
`addCorsHeaders()` wurde durch `.add()` zweimal aufgerufen, wodurch der Header `Access-Control-Allow-Origin: *, *` ungültig wurde.
**Fix:** In `CorsUtil.java` `.add()` durch `.set()` ersetzt, damit der Header nur einmal gesetzt wird.

----------------------------------------------------------------------------------------------------------------

# Navigation – Registrierung

<p> Nach der Registrierung wird der User automatisch eingeloggt und direkt auf `/listings` weitergeleitet.</p>

## Ablauf
1. User füllt das Registrierungsformular aus
2. Account wird per `POST /register` erstellt
3. Automatisch wird per `POST /login` eingeloggt
4. JWT-Token, Username, Rolle und UserId werden im `localStorage` gespeichert
5. User wird direkt auf `/listings` weitergeleitet

----------------------------------------------------------------------------------------------------------------

# Setup

## Voraussetzungen
- Java 17+
- Node.js 18+
- Docker Desktop

## Docker Desktop installieren
1. Gehe auf [docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop/)
2. Lade die Windows Version herunter
3. Installiere es mit den Standardeinstellungen
4. PC neu starten
5. Docker Desktop öffnen und warten bis der grüne Punkt unten links erscheint

## Datenbank starten
<p> Die Datenbank läuft in einem Docker Container. Starte sie mit: </p>

```bash
docker-compose up -d
```

<p>Alle Tabellen werden automatisch beim ersten Start erstellt.</p>

## Umgebungsvariablen
<p> Kopiere `.env.example` zu `.env` und fülle die Werte aus: </p>

```bash
cp .env.example .env
```

<p> Fülle folgende Werte in der `.env` Datei aus: </p>

```bash
DB_URL=jdbc:postgresql://localhost:5432/kleidungsshop
DB_USER=postgres
DB_PASSWORD=your_password_here
PEPPER=your_pepper_here
JWT_SECRET=your_jwt_secret_here_min_32_characters
```

## Backend starten
<p> Öffne das Projekt in IntelliJ und starte `Main.java`. </p>

## Frontend starten
```bash
cd frontend
npm install
npm start
```

----------------------------------------------------------------------------------------------------------------

# Anmeldung mit Email oder Username

<p> User können sich mit ihrem Username als auch mit ihrer Email einloggen. </p>

## Ablauf
1. User gibt Username oder Email und Passwort ein
2. Backend sucht zuerst per Username in der Datenbank
3. Falls kein User gefunden, wird per Email gesucht
4. Bei Erfolg wird ein JWT-Token zurückgegeben

----------------------------------------------------------------------------------------------------------------

## User Profil
<p> Jeder eingeloggte User hat eine eigene Profilseite.</p>

## Angezeigte Informationen
- Profilbild (oder Initiale als Platzhalter)
- Aktueller Username
- Aktuelles Passwort

## Endpunkte
- `GET /profile` – Profildaten abrufen (Username und Profilbild)
- `PUT /profile` – Username oder Passwort ändern
- `POST /profile` – Profilbild hochladen

## Datenbank
<p> Die Tabelle `users` hat eine Spalte `profile_picture` die den Dateipfad des Profilbilds speichert. </p>

----------------------------------------------------------------------------------------------------------------

# Warenkorb
<p>Eingeloggte User können Inserate anderer User in den Warenkorb legen und kaufen.
Nach dem Kauf verschwindet das Inserat aus der App.</p>

## Funktionsweise
- Im Detail-Modal eines fremden Inserats erscheint ein "In den Warenkorb" Button
- Über das Warenkorb-Icon in der Navbar gelangt man zur Warenkorb-Seite
- Dort kann man einzelne Inserate kaufen oder aus dem Warenkorb entfernen
- Nach dem Kauf wird das Inserat aus der Datenbank gelöscht und verschwindet für alle

## Datenbank
<p>Eine neue Tabelle `cart` speichert die Verknüpfung zwischen `user_id` und `listing_id`.
Eine `UNIQUE` Constraint verhindert doppelte Einträge.</p>

## Endpunkte
- `GET /cart` – Alle Inserate im Warenkorb abrufen
- `POST /cart/{id}` – Inserat zum Warenkorb hinzufügen
- `DELETE /cart/{id}` – Inserat aus Warenkorb entfernen
- `POST /cart/{id}/buy` – Inserat kaufen und löschen

## Berechtigungen
- Nur eingeloggte User können den Warenkorb nutzen
- Eigene Inserate können nicht in den Warenkorb gelegt werden

----------------------------------------------------------------------------------------------------------------

# Profilbild in der Navbar

<p> Statt "Eingeloggt als [username]" wird in der Navbar das Profilbild des Users angezeigt.
Falls noch kein Profilbild hochgeladen wurde, wird die erste Initiale des Usernamens als Platzhalter angezeigt.
Ein Klick auf das Bild führt zur Profilseite. </p>

----------------------------------------------------------------------------------------------------------------

# Validierung

## Registrierung
<p>Alle Felder (Username, Email, Passwort) sind Pflichtfelder.</p>

- Email muss ein `@` enthalten
- Passwort muss mindestens 4 Zeichen haben
- Username und Email müssen einzigartig sein

## Profil ändern
- Der neue Username darf nicht gleich wie der aktuelle sein
- Das neue Passwort darf nicht gleich wie das aktuelle sein
- Mindestens ein Feld muss ausgefüllt sein

----------------------------------------------------------------------------------------------------------------

# Styling
<p> Das Frontend verwendet ausschliesslich `rem` Einheiten für alle Grössenangaben, um eine konsistente und skalierbare Darstellung zu gewährleisten. Die Basis-Schriftgrösse des Browsers (standardmässig 16px) dient als Referenz. </p>

## Dateien
- `global.css` – Globale Styles für Body, Links und Box-Sizing
- `atoms.css` – Styles für Button, Input und Fehlermeldungen
- `molecules.css` – Styles für alle zusammengesetzten Komponenten

## Umrechnungsreferenz
- `4px` → `0.25rem`
- `8px` → `0.5rem`
- `16px` → `1rem`
- `32px` → `2rem`

----------------------------------------------------------------------------------------------------------------

# Letzter Test & Bugfixing
<p>Die gesamte App wurde manuell durchgetestet. Alle Hauptfunktionen wurden überprüft und funktionieren korrekt. </p>

## Getestete Funktionen
- Registrierung mit Pflichtfeld-Validierung
- Automatischer Login nach Registrierung
- Login mit Username oder Email
- Inserate anzeigen, erstellen, bearbeiten und löschen
- Öffentliche und private Inserate
- Bild-Upload bei Inseraten
- Favoriten hinzufügen und entfernen
- Warenkorb – Inserate hinzufügen, entfernen und kaufen
- Profilseite – Username, Email, Passwort und Profilbild ändern
- Profilbild in der Navbar
- Admin kann alle Inserate bearbeiten und löschen
- User kann nur eigene Inserate bearbeiten und löschen
- Navbar – Links zu Inseraten, Favoriten, Warenkorb und Profil
- Logout

## Ergebnis
Keine kritischen Fehler gefunden. Die App funktioniert stabil und alle Features verhalten sich wie erwartet.

----------------------------------------------------------------------------------------------------------------
# ReadMe bearbeiten 
Ich habe versucht mein Readme so gut es geht in der kurzen zet zu bearbeiten und zu perfektionierne.
