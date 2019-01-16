Wykorzystano następujące narzędzia:

- Java 11 jdk,
- IntelliJ, 
- Gradle 5,
- Spring Boot 2.1.1,
- Swagger


Jak zbudować i uruchomić aplikację:

1. Ściągamy repozytorium jako plik .zip

2. Rozpakowujemy

3. W katalogu aplikacji wpisujemy polecenie:

./gradlew bootJar    (pod Linuxem)

gradlew bootJar    (pod Windowsem)

4. Aplikacja zostanie zbudowana w katalogu build/libs

5. Uruchamiamy poleceniem:

java -jar intivepatronage-0.0.1-SNAPSHOT.jar    (proszę pamiętać, że port 8080 na localhost musi być wolny)

******************************************************************
Api Documentation Swagger - http://localhost:8080/swagger-ui.html
******************************************************************

Lista poleceń CURL do obsługi aplikacji:

CRUD – SALA KONFERENCYJNA:

ZAPISANIE SALI KONFERENCYJNEJ:

curl -X POST http://localhost:8080/conferencerooms -H 'Content-Type: application/json' -d @payload.json

zawartość payload.json
{
"name" : "conference room 1",
"id" : "CR1",
"floor" : 10,
"availability" : true,
"seating" : 34,
"standingPlace" : 23,
"lyingPlace" : 15,
"hammock" : 24,
"phone" : true,
"phoneInNumber" : "91",
"phoneOutNumber" : "+12 123456789",
"communicationInterface" : "bluetooth"
}


ODCZYT WSZYSTKICH SAL KONFERENCYJNYCH:

curl -X GET http://localhost:8080/conferencerooms


ODCZYT SALI KONFERENCYJNEJ PO ID:

curl -X GET 'http://localhost:8080/conferencerooms/id?id=CR1'


UPDATE SALI KONFERENCYJNEJ PO ID:

curl -X PUT 'http://localhost:8080/conferencerooms/update?id=CR1' -H 'Content-Type: application/json' -d @payload.json


SKASOWANIE SALI KONFERENCYJNEJ PO ID:

curl -X DELETE 'http://localhost:8080/conferencerooms/delete/id?id=CR2'


SKASOWANIE WSZYSTKICH SAL KONFERENCYJNYCH:

curl -X DELETE http://localhost:8080/conferencerooms/delete/all

******************************************************************

CRUD – ORGANIZACJA

ZAPISANIE ORGANIZACJI:

curl -X POST  http://localhost:8080/organizations -H 'Content-Type: application/json' -d '{"name":"Organizacja1"}'


ODCZYT WSZYSTKICH ORGANIZACJI:

curl -X GET http://localhost:8080/organizations


ODCZYT ORGANIZACJI PO NAZWIE:

curl -X GET 'http://localhost:8080/organizations/id?id=Organizacja1'


UPDATE ORGANIZACJI PO NAZWIE:

curl -X PUT 'http://localhost:8080/organizations/update?id=Organizacja1' -H 'Content-Type: application/json' -d '{"name":"Organizacja2"}'


SKASOWANIE SALI KONFERENCYJNEJ PO NAZWIE:

curl -X DELETE 'http://localhost:8080/organizations/delete/id?id=Organizacja2'


KASOWANIE WSZYSTKICH SAL KONFERENCYJNYCH:

curl -X DELETE http://localhost:8080/organizations/delete/all

******************************************************************

CRUD – REZERWACJA

ZAPISANIE REZERWACJI:

curl -X POST http://localhost:8080/reservations -H 'Content-Type: application/json' -d@payload.json

zawartość payload.json
{"id" : "Rezerwacja1",
"beginDate" : "2019-01-01T13:00",
"endDate" : "2019-01-01T14:00",
"organizationId" : "Organizacja1",
"conferenceRoomId" : "CR1"
}


ODCZYT WSZYSTKICH REZERWACJI:

curl -X GET http://localhost:8080/reservations


ODCZYT REZERWACJI PO ID:

curl -X GET 'http://localhost:8080/reservations/id?id=Rezerwacja1'


UPDATE REZERWACJI PO ID:

curl -X PUT 'http://localhost:8080/reservations/update?id=Rezerwacja1' -H 'Content-Type: application/json' -d@payload.json


SKASOWANIE REZERWACJI PO ID:

curl -X DELETE 'http://localhost:8080/reservations/delete/id?id=Rezerwacja2'


SKASOWANIE WSZYSTKICH REZERWACJI:

curl -X DELETE http://localhost:8080/reservations/delete/all

