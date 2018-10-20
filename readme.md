# Compilação e Execução

Para compilar o código é necessario ter a JDK 1.8.0 ou maior e Maven. O código foi homologado no Sistema Operacional Windows não
avaliando erros nos demais sistemas operacionais. A compilação foi feita utilizando o Intellij IDEA gerando o arquivo
.jar e utilizando o Maven por linha de comando (mvn jfx:jar na pasta principal do projeto com a variavel JAVA_HOME
configurada para utilizar a JDK).

# Utilização

Para executar o programa, basta rodar o .jar criado após a compilação (Utilizando Maven o mesmo ficara em 
/target/jfx/app). Uma tela irá se abrir com um seletor de datas, um campo com nomes de funcionarios, um campo com nomes
de restaurantes, um botão utilizado para cadastrar os votos e na parte inferior um texto indicando o estado da votação
para o dia selecionado.
 
O funcionario deve selecionar seu nome na lista de profissionais, o restaurante desejado na lista de restaurantes e 
pressionar o botão votar. Uma mensagem deve aparecer alertando que o voto foi computado, após todos funcionarios votarem
ou o horario do sistema chegue ao meio-dia o resultado da votação sera informado na tela. Caso a votação não tenha
recebido votos validos (votos em restaurantes ainda não visitados aquela semana) é informado que nenhuma decisão foi
tomada nesta data.
 
 # Destaques
 
 Alguns itens que acho que devem ser destacados no código a seguir seriam:
 
 - O mesmo verifica o horario do sistema onde está sendo executado para validar se o calculo dos fotos deve ser 
 realizado e se ainda pode se votar.
 
 - O selecionador de datas bloqueia a seleção de datas anteriores a atual do sistema.
 
 - O código foi escrito em Java 8 utilizando a biblioteca Java FX 2 para fazer a interface visual
 
 - Para realizar a compilação do código gerando um artefato .jar é necessario utilizar o Maven, porem para rodar o projeto pode se utilizar IDEs que tenham suporte ao JavaFX 2 (Netbeans, Intellij IDEA e Eclipse)
 
 # A melhorar
 
 Para realizar a entrega o mais breve possivel acabei por não integrar o programa com uma base de dados, sendo assim o
 código conta com um numero limitado de dados "fakes" e não é persistente ao fechamento do programa. 
 
 A interface gráfica do programa ficou um tanto simples, a mesma poderia conter mais informações e ser mais apresentável.
 
 Alguns métodos que estão no Controller poderiam ser passados para camada lógica do código (LunchBO) para serem mais
  reutilizaveis e haver mais separação entre o Front e Back do projeto.
  
Poderiam ser criados testes automatizados para o Front, testando se a interface estária respondendo como desejado no caso
de haver futuras mudanças no código que alterem seu comportamento.

Atualmente ao passar o dia o mesmo é bloqueado, sendo assim não há a possiblidade de consultar o resultado desta data
 novamente. Poderia ser criada uma lista com os resultados passados para uma vizualização mais rapida.
 
 Mais testes automatizados para uma maior cobertura e evitar erros ao modificar o código.  
