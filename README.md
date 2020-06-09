# Cade a Cerva [![Build Status](https://travis-ci.org/marcoasp/cade-a-cerva.svg?branch=master)](https://travis-ci.org/marcoasp/cade-a-cerva) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=marcoasp_cade-a-cerva&metric=alert_status)](https://sonarcloud.io/dashboard?id=marcoasp_cade-a-cerva)

Aplicação pra estudo e achar os goró (não necessariamente nessa ordem)

A aplicação  utiliza toda stack Spring Cloud e é composta por:

- Service Registry (Eureka)
- Config Server
- Gateway (Zuul e Oauth)
- Sales (Crud de promoções)
- Store (Crud de lojas)
- Users (Informações sobre usuário)
- Ui (Front end angular)

Para subir todo o ambiente localmente:

1. Criar um arquivo `.env` usando `cp .env-example .env`: 
    
   ```
    EUREKA_USER=eureka #Usuário para autenticar no Service Registry 
    EUREKA_PASSWORD=password #Senha para autenticar no Service Registry
    EUREKA_ADDRESS=http://eureka:password@registry:8080 #Url que aplicações usam para se registrar
    CONFIG_USER=config #Usuário para autenticar no Config Server
    CONFIG_PASSWORD=password #Senha para autenticar no Config Server
    ENCRYPT_KEY=DEAF197CA5487461FFA4D46A523C2 #Chave para criptografia dos valores expostos pelo config server
   ```
   
2. Subir os serviços via docker-compose: `docker-compose up -d`