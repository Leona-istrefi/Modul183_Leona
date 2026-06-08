# Modul183_Leona

## Projektbeschreib
<p> Ich mache eine Website wo man Kleider verkaufen kann. Man kann sich registrieren und einloggen. Die Passwörter werden sicher gespeichert mit Salt & Pepper. Das Backend mache ich mit Java und das Frontend mit TypeScript. In der Datenbank werden der Benutzername und das gehashte Passwort gespeichert. Wenn man eingeloggt ist kann man Kleider hochladen mit Name, Zustand, Grösse, Preis, Beschreibung und einem Bild.Ich mache eine Website wo man Kleider verkaufen kann. Man kann sich registrieren und einloggen. Die Passwörter werden sicher gespeichert mit Salt & Pepper und bcrypt. Das Backend mache ich mit Java und das Frontend mit TypeScript. In der Datenbank werden der Benutzername und das gehashte Passwort gespeichert. Wenn man eingeloggt ist kann man Kleider hochladen mit Name, Zustand, Grösse, Preis, Beschreibung und einem Bild. </p>

## Zeitplan 
| Name | Beschreibung                                                                                                  | Zeit (h) |
|---|---------------------------------------------------------------------------------------------------------------|----------|
| Projektplanung | Architektur planen und Technologien festlegen. Datenbankstruktur skizzieren.                                  | 0.5      |
| Projekt aufsetzen | Spring Boot und React Projekt erstellen. Ordnerstruktur und Dependencies einrichten.                          | 1        |
| Datenbank aufsetzen | PostgreSQL installieren und konfigurieren. Tabellen für users, listings und images erstellen.                 | 1.5      |
| User Entity & Repository | Java Entity-Klassen für den User erstellen. JPA Repository einrichten.                                        | 1        |
| Registrierung Backend | Register-Endpunkt implementieren mit Salt generieren und bcrypt Hashing. Benutzername und Hash in DB speichern. | 2        |
| Login Backend | Login-Endpunkt implementieren und Passwort mit Hash vergleichen. JWT-Token zurückgeben.                       | 2        |
| Listing Entity & Repository | Java Entity für Inserate erstellen mit allen Feldern. JPA Repository und Service einrichten.                  | 1        |
| Listing CRUD Backend | Endpunkte für erstellen, abrufen und löschen von Inseraten. Nur eingeloggte User dürfen inserieren.           | 2        |
| Bild-Upload Backend | Multipart Endpunkt für Bild-Upload implementieren. Datei lokal speichern und Pfad in DB speichern.            | 2        |
| API testen | Alle Endpunkte mit Postman testen. Fehler beheben.                                                            | 1        |
| Login & Register Frontend | Loginseite und Registrierungsseite in React bauen. Formular mit API verbinden.                                | 2        |
| Navbar implementieren | Einfache Navigation mit Links erstellen. Eingeloggten User anzeigen.                                          | 1        |
| Cards im Frontend | Post-Cards auf der Startseite implementieren. Bild, Name und Preis anzeigen.                                  | 1.5      |
| Post erstellen Frontend | Formular für neue Poosts bauen. Alle Felder und Bild-Upload einbinden.                                        | 2        |
| Detailseite Frontend | Einzelne Detailseite pro Inserat erstellen. Alle Infos und das Bild anzeigen.                                 | 1        |
| Styling | Grundlegendes CSS für alle Seiten. Website übersichtlich und sauber gestalten.                                | 1.5      |
| Letzter Test & Bugfixing | Gesamte App durchklicken und Fehler beheben. Sicherstellen dass alles funktioniert.                           | 1        |
| Letzter Commit & Doku | Code aufräumen und kommentieren. README schreiben und auf GitHub pushen.                                      | 1        |
| **Total** |                | **25 h** |

## Arbeitsjournal 
| Datum               | Zeit (h)           | Was wurde erledigt                                    |
|---------------------|--------------------|-------------------------------------------------------|
| 08.06.2026          | 14:30-15:00 <br/>  | Ich habe meine Projektplanung im README.md festgelegt |

----------------------------------------------------------------------------------------------------------------
# Projektplanung

## Kleidungsverkaufsportal 
<p> Ich kreiere eine einfache Website wo man Kleider verkaufen kann. Man kann sich registrieren und einloggen. Das Backend ist mit Java (Sprint Boot) und das Frontend mit TypeScript (React). Passwörter werden sicher mit Salt & Pepper und bcrypt gespeichert.</p>

## Technologiestack 
- **Frontend:** TypeScript, React
- **Backend:** Java 17, Spring Boot 
- **Datenbank:** PosthreSQL
- **Sicherheit der Passwörter:** bcrpt, Salt & Pepper

## Architektur 
<p> Das Projekt is aufgeteilt in drei Teilen: </p>

- **Frontend** kommuniziert über REST-API mit dem Backend
- **Backend** verwaltet die Logik, Authentifizierung und Datenbankzugriffe
- **Datenbank** speichert Benutzer, Inserate und Bilder 

## Datenbankstruktur 
**users** 
- id, username, email, salt, password_hash

**listings** 
- id, user_id, name, zustand, grösse, preis, beschreibung

**images** 
- id, listening_id, filepath

## Funktionen 
- Registrierung und Login mit sicherer Passwortspeicherung
- Inserat erstellen mit Name, Zustand, Grösse, Preis, Beschreibng und Bild 
- Alle Inserate auf der Startseite anzeigen 
- Detailseite pro Inserat 

## Sicherheit 
<p> Passwörter werden mit einem zufälligen Salt und einem geheimen Pepper kombiniert und dann mit bcrypt gehasht. In der Datenbank wird nur der Hash und der Salt gespeichert, nie das Originalpasswort.</p>
