# Sistema de Mediateca – Projecto Académico em Java (POO)

##  Descrição

Este projecto consiste no desenvolvimento de uma aplicação em Java (modo consola) no âmbito da disciplina de Programação Orientada a Objectos.

O sistema simula a gestão de uma Mediateca, permitindo:

- Gestão de Utentes
- Gestão de Obras (Livros e DVDs)
- Requisição e devolução de obras
- Aplicação rigorosa de regras de negócio
- Pesquisa de informação
- Persistência de dados através de serialização



##  Objectivo

Desenvolver uma aplicação funcional, organizada, compilável e demonstrável, aplicando correctamente os principais conceitos de POO:

- Abstração
- Herança
- Polimorfismo
- Encapsulamento
- Tratamento de Excepções
- Serialização de objectos



##  Estrutura do Projecto

###  Pacotes

poo.gamed  
poo.gamed.exception  
poo.gamed.app  


###  Principais Classes

####  Entidades do Domínio

- Obra (abstract)
- Livro
- DVD
- Categoria
- Utente
- Requisicao
- Mediateca (classe central de controlo)

####  Excepções Personalizadas

- NoSuchUserException
- NoSuchWorkException
- RuleFailedException
- UserRegistrationFailedException
- WorkRegistrationFailedException
- FileOpenFailedException
- WorkNotBorrowedByUserException

####  Interface (Menus)

- MainMenu
- MenuUtentes
- MenuObras
- MenuRequisicoes


## Funcionalidades Implementadas

✔ Registo de utentes com ID automático  
✔ Registo de obras com identificador no formato XXXX/AAAA  
✔ Controlo de exemplares disponíveis  
✔ Requisição com validação de regras  
✔ Devolução com actualização de estado  
✔ Ordenação de listagens  
✔ Persistência de dados em ficheiro  
✔ Tratamento adequado de excepções  

---

## Regras de Negócio Aplicadas

O sistema garante:

- Um utente não pode requisitar a mesma obra duas vezes
- Não é possível requisitar se não houver exemplares disponíveis
- Existe limite máximo de obras requisitadas por utente
- Obras de referência não podem ser requisitadas
- Obras acima do preço permitido não podem ser requisitadas
- Apenas o utente que requisitou pode devolver a obra

---

##  Persistência de Dados

O sistema utiliza Serializable para:

- Guardar o estado completo da aplicação
- Restaurar dados entre execuções
- Manter consistência dos identificadores automáticos

---

##  Como Compilar e Executar

### Compilar

```bash
javac poo/gamed/app/MainMenu.java<img width="1920" height="1008" alt="image" src="https://github.com/user-attachments/assets/21a5ff64-7d00-42bd-8514-2f6ad2913d91" />
