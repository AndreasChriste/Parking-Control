# Comece por aqui
### Principais ferramentas:

* Java JDK 17
* Spring WEB
* PostgreSQL
* OpenAPI
* Insomnia / Postman

# Resumo

Este projeto é uma API REST que visa realizar o controle de vagas de estacionamento de um condomínio. Tal api possui os as principais operações para manipulação das informações "CRUD", ou seja, é possivel fazer operações de criação, leitura, atualização e deleção neste projeto. Para o controle dessas vagas atende-se alguns critérios essências para a alocação de um novo veículo a uma vaga, esses criterios são listados a seguir:

* Só é permitido cadastrar 1 veículo por vaga de estacionamento.
* Cada vaga de estacionamento está atrelada a um apartamento e bloco.
* O cadastro de um veículo em uma vaga só é permitido se todas as informações, como parkingSpotNumber,licensePlateCar,brandCar, modelCar, colorCar, responsableName, apartment e block, estiverem devidamente preenchidas.


# Documentação
Para se ter um controle efetivo das funcionalidades da api foi utilizado o OpenAPI para documentar os detalhes da API. Os endpoints estão devidamente documentados e suas funcionalidades são descritas utilizando essa ferramenta, basta executar a aplicação e abrir a seguinte URL: 
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) .Feito isso, chegará numa página semelhante a imagem a seguir:

![imagem OpenAPI](image.png)


