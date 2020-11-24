create schema aulamineda;

use aulamineda;

create user 'user'@'localhost' identified by 'pass123';

grant select, insert, delete, update on aulamineda.* to user@'localhost';

create table usr_usuario (
  usr_id bigint unsigned not null auto_increment,
  usr_nome varchar(20) not null,
  usr_senha varchar(50) not null,
  primary key (usr_id),
  unique key uni_usuario_nome (usr_nome)
);

create table aut_autorizacao (
  aut_id bigint unsigned not null auto_increment,
  aut_nome varchar(20) not null,
  primary key (aut_id),
  unique key uni_aut_nome (aut_nome)
);

create table liv_livros (
  liv_id bigint unsigned not null auto_increment,
  liv_titulo varchar(20) not null,
  primary key (liv_id),
  unique key uni_liv_titulo (liv_titulo)
);

create table uau_usuario_autorizacao (
  usr_id bigint unsigned not null,
  aut_id bigint unsigned not null,
  primary key (usr_id, aut_id),
  foreign key aut_usuario_fk (usr_id) references usr_usuario (usr_id) on delete restrict on update cascade,
  foreign key aut_autorizacao_fk (aut_id) references aut_autorizacao (aut_id) on delete restrict on update cascade
);

create table uau_usuario_livro (
  usr_id bigint unsigned not null,
  liv_id bigint unsigned not null,
  primary key (usr_id, liv_id),
  foreign key aut_usuario_fk (usr_id) references usr_usuario (usr_id) on delete restrict on update cascade,
  foreign key liv_livrod_fk (aut_id) references liv_livros (liv_id) on delete restrict on update cascade
);

insert into usr_usuario (usr_nome, usr_senha)
    values ('Debora', 'SenhaFr4ca');
insert into aut_autorizacao (aut_nome)
    values ('ROLE_ADMIN');
insert into liv_livros (liv_titulo)
    values ('Anne_of_Green_Gables');
insert into uau_usuario_autorizacao values (1,1);
insert into uau_usuario_livro values (1,1);
