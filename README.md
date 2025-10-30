# Programiranje-Distribuiranih-Sistema
Projekat iz predmeta Programiranje Distribuiranih Sistema

**Mini E-commerce Microservices Project**
**Opis projekta**

Ovo je mala mikroservisna aplikacija koja demonstrira principe distribuiranih sistema u Javi sa Spring Boot-om.

Tema: Mini e-commerce
Entiteti: User, Order

Ciljevi projekta:

- Razdvajanje funkcionalnosti u dva mikroservisa (users-service, orders-service)
- Service Discovery preko Eureka Server
- Centralna ulazna tačka preko API Gateway
- Komunikacija servis–servis koristeći OpenFeign
- Osnovna otpornost sistema koristeći Resilience4j (Circuit Breaker + Retry)
- Persistencija podataka u H2 in-memory bazi
- Agregacioni endpoint /orders/{id}/details koji spaja podatke iz oba servisa
- Bonus: Jednostavna autentikacija na Gateway-u (BasicAuth)

**Struktura projekta**

Root folder: Projekat
Projekat /
- eureka-server/
- users-service/
- orders-service/
- api-gateway/

**Redosled pokretanja servisa**

- Eureka Server: http://localhost:8761/
- Users Service: http://localhost:8082/
- Orders Service: http://localhost:8083/
- API Gateway: http://localhost:8084/

Napomena: API Gateway koristi BasicAuth:
- Username: gateway
- Password: gateway123

**Endpoints**

Users Service
1) /users/test - GET - Test endpoint
2) /users/create - POST - Kreira novog korisnika
3) /users/all - GET - Vraća sve korisnike
4) /users/{id} - GET - Vraća korisnika po ID-u

Orders Service
1) /orders/test - GET - Test endpoint
2) /orders - POST - Kreira novu narudžbinu
3) /orders - GET - Vraća sve narudžbine
4) /orders/user/{userId} - GET - Vraća sve narudžbine za korisnika
5) /orders/{id}/details - GET - Agregacioni endpoint: vraća order + user podatke (koristi Feign + Resilience4j)

API Gateway
1) /api/users/** → routes ka users-service
2) /api/orders/** → routes ka orders-service
Pristup zaštićen BasicAuth-om

**Resilience4j (Circuit Breaker + Retry)**

Feign poziv iz Orders Service → Users Service koristi:
- Circuit Breaker
- Retry

Fallback vrednost vraća User sa username="Unknown user" ukoliko je users-service nedostupan

Testiranje:
- Ugasiti users-service
- Poslati GET zahtev /orders/{id}/details preko gateway-a
- Rezultat: Order podaci sa fallback User informacijom

**Servisi**

1) Eureka Server - 8761 - / - Service Discovery
2) Users Service - 8082 - /users/** - CRUD nad korisnicima
3) Orders Service	8083 - /orders/** - CRUD nad narudžbinama + business logika + agregacioni endpoint
4) API Gateway	8084 - /api/users/** , /api/orders/** - Centralna ulazna tačka, BasicAuth zaštita

**Tehnički zahtevi**

- Java 17+
- Spring Boot 3+
- H2 baza za oba servisa
- OpenFeign za komunikaciju servisa
- Resilience4j za otpornost sistema
- Validacija podataka i korektni HTTP statusi
- Jasan logging (Feign poziv, CB/Retry događaji)
- Bonus: BasicAuth na Gateway-u

**Pokretanje i testiranje**
- (mvn spring-boot:run) - komanda za pokretanje preko terminala

- Pokrenuti Eureka Server  (port 8761)
- Pokrenuti Users Service (port 8082)
- Pokrenuti Orders Service (port 8083)
- Pokrenuti API Gateway (port 8084)
- Testirati sve rute preko Postman-a ili browser-a
- Proveriti Eureka Dashboard: svi servisi i gateway bi trebalo da budu registrovani

**Dijagram komponenti**

Komponente:

- EurekaServer - centralna tačka registra servisa
- UsersService - čuva korisnike u H2 bazi, pruža REST API
- OrdersService - čuva porudžbine, koristi Feign da dobije korisničke podatke
- ApiGateway - prima zahteve i prosleđuje ih registrovanim servisima
- BasicAuthFilter - jednostavna sigurnosna kontrola na ulazu

**Zaključak**

Ovaj projekat demonstrira osnovne principe mikroservisne arhitekture:

- nezavisno razvijanje i pokretanje servisa,
- centralizovanu registraciju (Eureka),
- rutiranje kroz gateway,
- jednostavnu međuservisnu komunikaciju.
