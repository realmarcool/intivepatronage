Wykorzystano następujące narzędzia:

- Java 11 jdk
- IntelliJ, 
- Gradle 5,
- Spring Boot 2.1.1


Jak zbudować i uruchomić aplikację:

1. Ściągamy repozytorium jako plik .zip

2. Rozpakowujemy

3. W katalogu aplikacji wpisujemy polecenie:

./gradlew bootJar (pod Linuxem)

gradlew bootJar (pod Windowsem)

4. Aplikacja zostanie zbudowana w katalogu build/libs

5. Uruchamiamy poleceniem java -jar intivepatronage-0.0.1-SNAPSHOT.jar (proszę pamiętać, że port 8080 na localhost musi być wolny)


Lista poleceń CURL do obsługi aplikacji:

CRUD – SALA KONFERENCYJNA:

ZAPISANIE SALI KONFERENCYJNEJ:

curl -X POST http://localhost:8080/conferenceroom -H 'Content-Type: application/json' -d '{"name":"conference room 1","id":"CR1","floor":10,"availability":true,"seating":34,"standingPlace":23,"lyingPlace":15,"hammock":24,"phone":true,"phoneInNumber":"91","phoneOutNumber":"+12 123456789","communicationInterface":"bluetooth"}'


ODCZYT WSZYSTKICH SAL KONFERENCYJNYCH:

curl -X GET http://localhost:8080/conferenceroom


ODCZYT SALI KONFERENCYJNEJ PO ID:

curl -X GET 'http://localhost:8080/conferenceroom/id?id=CR1'


UPDATE SALI KONFERENCYJNEJ PO ID:

curl -X PUT 'http://localhost:8080/conferenceroom/update?id=CR1' -H 'Content-Type: application/json' -d '{"name":"conference room 2","id":"CR2","floor":10,"availability":true,"seating":34,"standingPlace":23,"lyingPlace":15,"hammock":24,"phone":true,"phoneInNumber":"91","phoneOutNumber":"+12 123456789","communicationInterface":"bluetooth"}'


SKASOWANIE SALI KONFERENCYJNEJ PO ID:

curl -X DELETE 'http://localhost:8080/conferenceroom/delete/id?id=CR2'


SKASOWANIE WSZYSTKICH SAL KONFERENCYJNYCH:

curl -X DELETE http://localhost:8080/conferenceroom/delete/all

******************************************************************

CRUD – ORGANIZACJA

ZAPISANIE ORGANIZACJI:

curl -X POST  http://localhost:8080/organization -H 'Content-Type: application/json' -d '{"name":"Organizacja1"}'


ODCZYT WSZYSTKICH ORGANIZACJI:

curl -X GET http://localhost:8080/organization


ODCZYT ORGANIZACJI PO NAZWIE:

curl -X GET 'http://localhost:8080/organization/name?id=Organizacja1'


UPDATE ORGANIZACJI PO NAZWIE:

curl -X PUT 'http://localhost:8080/organization/update?id=Organizacja1' -H 'Content-Type: application/json' -d '{"name":"Organizacja2"}'


SKASOWANIE SALI KONFERENCYJNEJ PO NAZWIE:

curl -X DELETE 'http://localhost:8080/organization/delete/name?id=Organizacja2'


KASOWANIE WSZYSTKICH SAL KONFERENCYJNYCH:

curl -X DELETE http://localhost:8080/organization/delete/all

******************************************************************

CRUD – REZERWACJA

ZAPISANIE REZERWACJI:

curl -X POST http://localhost:8080/reservation -H 'Content-Type: application/json' -d '{ 
"id":"Rezerwacja1","start":"2019-01-01","end":"2019-01-10","organizationId":"Organizacja1","conferenceRoomId":"CR1"}'


ODCZYT WSZYSTKICH ORGANIZACJI:

curl -X GET http://localhost:8080/reservation


ODCZYT REZERWACJI PO ID:

curl -X GET 'http://localhost:8080/reservation/id?id=Rezerwacja1'


UPDATE REZERWACJI PO NAZWIE:

curl -X PUT 'http://localhost:8080/reservation/update?id=Rezerwacja1' -H 'Content-Type: application/json' -d '{ 
"id":"Rezerwacja2","start":"2019-01-01","end":"2019-01-10","organizationId":"Organizacja1","conferenceRoomId":"CR1"}'


SKASOWANIE REZERWACJI PO ID:

curl -X DELETE 'http://localhost:8080/reservation/delete/id?id=Rezerwacja2'


SKASOWANIE WSZYSTKICH REZERWACJI:

curl -X DELETE http://localhost:8080/reservation/delete/all

