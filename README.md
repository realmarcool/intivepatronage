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

CRUD – Sala konferencyjna

Zapisanie sali konferencyjnej:

curl -X POST http://localhost:8080/conferenceroom -H 'Content-Type: application/json' -d '{"name":"conference room 1","id":"CR1","floor":10,"availability":true,"seating":34,"standingPlace":23,"lyingPlace":15,"hammock":24,"phone":true,"phoneInNumber":"91","phoneOutNumber":"+12 123456789","communicationInterface":"bluetooth"}'

Get /conferenceroom – odczyt wszystkich sal konferencyjnych
curl -X GET http://localhost:8080/conferenceroom

Get /conferenceroom/id – odczyt sali konferencyjnej po id
curl -X GET 'http://localhost:8080/conferenceroom/id?id=CR1'

Put /conferenceroom/update – update sali konferencyjnej po id
curl -X PUT 'http://localhost:8080/conferenceroom/update?id=CR1' -H 'Content-Type: application/json' -d '{"name":"conference room 2","id":"CR2","floor":10,"availability":true,"seating":34,"standingPlace":23,"lyingPlace":15,"hammock":24,"phone":true,"phoneInNumber":"91","phoneOutNumber":"+12 123456789","communicationInterface":"bluetooth"}'

Delete /conferenceroom/delete/id – skasowanie sali konferencyjnej po id
curl -X DELETE 'http://localhost:8080/conferenceroom/delete/id?id=CR2'

Delete /conferenceroom/delete/all – skasowanie wszystkich sal konferencyjnych
curl -X DELETE http://localhost:8080/conferenceroom/delete/all


CRUD – Organizacja

Post /organization – zapisanie sali organizacji
curl -X POST  http://localhost:8080/organization -H 'Content-Type: application/json' -d '{"name":"Organizacja1"}'

Get /organization – odczyt wszystkich organizacji
curl -X GET http://localhost:8080/organization

Get /organization/name – odczyt organizacji po nazwie
curl -X GET 'http://localhost:8080/organization/name?id=Organizacja1'

Put /organization/update – update organizacji po nazwie
curl -X PUT 'http://localhost:8080/organization/update?id=Organizacja1' -H 'Content-Type: application/json' -d '{"name":"Organizacja2"}'

Delete /organization/delete/name – skasowanie sali konferencyjnej po name
curl -X DELETE 'http://localhost:8080/organization/delete/name?id=Organizacja2'

Delete /organization/delete/all – skasowanie wszystkich sal konferencyjnych
curl -X DELETE http://localhost:8080/organization/delete/all


CRUD – Rezerwacja

Zapisanie rezerwacji:

curl -X POST http://localhost:8080/reservation -H 'Content-Type: application/json' -d '{ 
"id":"Rezerwacja1","start":"2019-01-01","end":"2019-01-10","organizationId":"Organizacja1","conferenceRoomId":"CR1"}'


Odczyt wszystkich organizacji:

curl -X GET http://localhost:8080/reservation


Odczyt rezerwacji po id:

curl -X GET 'http://localhost:8080/reservation/id?id=Rezerwacja1'


Update organizacji po nazwie
curl -X PUT 'http://localhost:8080/reservation/update?id=Rezerwacja1' -H 'Content-Type: application/json' -d '{ 
"id":"Rezerwacja2","start":"2019-01-01","end":"2019-01-10","organizationId":"Organizacja1","conferenceRoomId":"CR1"}'

Delete /reservation/delete/id – skasowanie rezerwacji po id
curl -X DELETE 'http://localhost:8080/reservation/delete/id?id=Rezerwacja2'

Delete /reservation/delete/all – skasowanie wszystkich rezerwacji
curl -X DELETE http://localhost:8080/reservation/delete/all
