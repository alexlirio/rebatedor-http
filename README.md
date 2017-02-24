## Projeto Rebatedor HTTP (JSON, XML, TEXT e etc.)

O projeto Rebatedor HTTP recebe e responde mensagens no formato HTTP (JSON, XML, TEXT e etc.). Para verificar o conteúdo das mensagens, o mesmo é logado. 


## Installation

1. Baixe o projeto do repositório Git: https://github.com/alexlirio/rebatedor-http.git
2. Para criar o pacote war do projeto, execute o seguinte comando maven na pasta raiz do projeto: "mvn clean package".
3. O pacote é criado na pasta do projeto como: "target/rebatedor-http*.war".


## Configuração Necessária

Para o funcionamento é necessário configurar: os seguintes arquivos dentro de "cfg/":

1. ** config.properties **, arquivo dentro da pasta "cfg/", com os valores necessários. Em cima de cada propriedade tem um comentário explicando a sua finalidade. 
2. ** No Tomcat 8**, colocar o arquivo gerado, "target/rebatedor-http*.war", na pasta "webapps" e levantar o servidor Tomcat.


## Utilização

O rebatedor HTTP e retorna o conteúdo do arquivo indicado na propriedade "http\_body\_file\_response" com o HTTP Status Code da propriedade "http\_status\_code\_response" do arquivo "cfg/config.properties". Para testes pode-se usar qualquer ferramenta que envia mensagens HTTP REST (Ex.: POSTMAN - http://www.getpostman.com/):


Exemplos de Utilização:

1. Para o HOST(URL), utilize os dados de exemplo abaixo, substituindo o "[METHOD]" pelo tipo de requisição desejada ("get" ou "post").

		http://localhost:8080/rebatedor-http/rebatedor-rest-service/[METHOD]
		
2. Para o Body, quando necessário, utilize os dados de exemplo abaixo como JSON, XML, TEXT ou etc.

		{"id":"123456"}
