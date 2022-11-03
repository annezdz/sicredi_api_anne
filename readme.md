# Prova técnica API

##  Tecnologias

 * Java 11
 * Maven
 * Rest Assured 4.1.2
 * JUnit 
 * Cucumber 4.8.0 
 * JSON Schema Validator 4.3.3
 * Javafaker 1.0.2


## Execução

Para execução do projeto é necessário cloná-lo e executar em sua IDE favorita.

# Pontos importantes

Relatórios das execução localizados aqui.

![](src/test/resources/img/img.png)

Runners para execução dos cenários

![](src/test/resources/img/img_1.png)

Arquivos Cucumber com os cenários


Tags para execução dos cenários de Restrição

![](src/test/resources/img/img_2.png)


Tags para execução dos cenários de Simulações


![](src/test/resources/img/img_3.png)

Informar a tag na Runner

![](src/test/resources/img/img_4.png)

Informar a base URL aqui 

![](src/test/resources/img/img_5.png)


# Execuções conflitantes com a documentação disponibilizada.

Diversos cenários tiveram retorno que não estão de acordo com a documentação 
disponibilizada para realização do desafio, tanto com relação ao status code retornado
quanto pela mensagem retornada.

Nesses casos segui com o que estava descrito na documentação e ao executar esses cenários
os mesmos falharão.

Seguem os cenários acima mencionados com a evidência da documentação x execução realizada.
(OBS: testes realizados no Postman tivemos o mesmo retorno)

# Execucoes com falha / defeito

## Cenarios de Restricao

### CN - @WithRestriction

 * Retorno divergente (mensagem)

* Criterio de aceite 
* 
![](src/test/resources/img/img_6.png)
![](src/test/resources/img/img_9.png)

* Execução : 

*  Retorno divergente com criterio de aceite.

![](src/test/resources/img/img_7.png)
![](src/test/resources/img/img_8.png)
![](src/test/resources/img/img_10.png)

## Cenarios de Simulacao

### CN - @AddSimulationExistent

* Retorno da mensagem e status code divergente com documentacao.

* Criterio de aceite

![](src/test/resources/img/img_11.png)
![](src/test/resources/img/img_12.png)

* Execução : 

![](src/test/resources/img/img_14.png)
![](src/test/resources/img/img_16.png)
![](src/test/resources/img/img_17.png)



### CN - "@PutWithoutSimulation

* Criterio de aceite divergente no PDF disponibilizado e na documentacao da API.

 * Divergencias : Mensagem de retorno divergente nas documentacoes.

![](src/test/resources/img/img_18.png)
![](src/test/resources/img/img_19.png)

* Execucao: 

* Como foram retornados o status code correto e a mensagem coerente, a priori considerei o teste como PASSED.


![](src/test/resources/img/img_20.png)


### CN - "@DeleteSimulationSuccessful"

* Criterio de aceite divergente no PDF disponibilizado e na documentacao da API.
Status Code de acordo com PDF deve ser 204 , mas na documentacao informa que deve ser 200.
* 
* Criterio de aceite:

![](src/test/resources/img/img_21.png)
![](src/test/resources/img/img_22.png)

* Execucao: 

* Retorno correto de acordo com documentacao da API. 
Nesse caso o CN esta com status PASSED.

![](src/test/resources/img/img_23.png)



### CN - @TryDeleteSimulation

* Status code incorreto e nao e exibida a mensagem de retorno.
#### Qualquer ID informado nao existente esta retornando 200 e a mensagem OK

* Criterios de aceite


* Na documentacao da API nao esta descrito esse comportamento, apenas no PDF.
* Nesse caso considero o CN FAILED.
* 
![](src/test/resources/img/img_27.png)
![](src/test/resources/img/img_28.png)


* Execucao : CN FAILED 

![](src/test/resources/img/img_24.png)
![](src/test/resources/img/img_25.png)
![](src/test/resources/img/img_26.png)


### CN - @TryGetAllSimulations

* Ao tentar buscar todas as simulacoes, quando nao existe simulacao cadastrada retorna status 200.

* Criterios de aceite

![](src/test/resources/img/img_29.png)

* Execucao : CN FAILED 
* Na documentacao da API nao esta previsto esse comportament, apenas no PDF disponibilizado.
Nesse caso considero o CN FAILED ao retornar um status code diferente do criterio de aceite.

![](src/test/resources/img/img_30.png)
![](src/test/resources/img/img_31.png)
![](src/test/resources/img/img_32.png)


# IMPORTANTE 

## Simulacoes

![](src/test/resources/img/img_33.png)

## Restricoes

![](src/test/resources/img/img_34.png)
