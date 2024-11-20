<h1 align="center">
  Encurtador de URL Java Serverless
</h1>


<p align="center">
  <a href="#page_with_curl-sobre">Sobre</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#hammer-tecnologias">Tecnologias</a>
  &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
    <a href="#books-requisitos">Requisitos</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#rotating_light-importante">Importante</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#rocket-iniciando">Iniciando</a>
    <a href="#golf-rotas">Rotas</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
</p>

## :page_with_curl: Sobre
Esse repositório é um projeto construido através do curso gratuito de Java da Rockeseat. O intutido dele é criar uma aplicação java para rodar através de uma função Lambda na AWS. Fora o serviço já citado, também utilizamos o S3 e a API Gateway.

Sobre a aplicação em si, é um encurtador de URL, onde o usuário pode enviar uma URL e receber uma URL encurtada. A aplicação também conta com um serviço de redirecionamento, onde o usuário pode acessar a URL encurtada e ser redirecionado para a URL original.
## :hammer: Tecnologias

- Java 17
- Maven
- AWS
- Lambda
- S3
- API Gateway
- Lombok

## :books: Requisitos

As tecnologias a seguir são necessárias para conseguir rodar o projeto em sua máquina.

- Ter [**Git**](https://git-scm.com/) para clonar o projeto.
- Ter Java 17 instalado.
- Ter uma conta na [**AWS**](https://aws.amazon.com/) para executar o projeto.

## :rotating_light: Importante

Para o projeto funcionar é imporante:

- Criar e configurar um bucket no S3
- Criar e configura uma função Lambda
- Criar e configurar uma API Gateway

Caso o curso desse projeto ainda esteja disponível no youtube da Rockeseat, recomendo fortemente que assista.

## :golf: Rotas
### Criar uma url encurtada
`POST - /`
Exemplo de corpo da requisição:
```bash
{
    "originalUrl": "https://google.com",
    "expirationTime": "1732190780"
}
```

Em "expirationTime" é imporante colocar um tempo válido, para isso utilize o site [Epoch Converter](https://www.epochconverter.com/), e converta a data desejada em segundos.

Exemplo de resposta:
```bash
{
    "urlId": "b1b2b3b4"
}
```

### Pegar tempo de determinada localização
`GET - /{urlId}`

Ele vai redirecionar para a URL original caso ainda esteja válida, caso contrário irá retornar um erro.
