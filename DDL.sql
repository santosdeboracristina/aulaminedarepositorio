create schema aulamineda;

use aulamineda;

create user 'user'@'localhost' identified by 'pass123';

grant select, insert, delete, update on aulamineda.* to user@'localhost';

create table usr_usuario (
  usr_id bigint unsigned not null auto_increment,
  usr_nome varchar(20) not null,
  usr_email varchar(100) not null,
  usr_senha varchar(50) not null,
  primary key (usr_id),
  unique key uni_usuario_nome (usr_nome),
  unique key uni_usuario_email (usr_email)
);

create table aut_autorizacao (
  aut_id bigint unsigned not null auto_increment,
  aut_nome varchar(20) not null,
  primary key (aut_id),
  unique key uni_aut_nome (aut_nome)
);

create table uau_usuario_autorizacao (
  usr_id bigint unsigned not null,
  aut_id bigint unsigned not null,
  primary key (usr_id, aut_id),
  foreign key uau_usr_fk (usr_id) references usr_usuario (usr_id) on delete restrict on update cascade,
  foreign key uau_aut_fk (aut_id) references aut_autorizacao (aut_id) on delete restrict on update cascade
);

create table liv_livro (
  liv_id bigint unsigned not null auto_increment,
  liv_titulo varchar(40) not null,
  usr_autor_id bigint unsigned not null,
  primary key (liv_id),
  foreign key liv_usr_fk (usr_autor_id) references usr_usuario (usr_id) on delete restrict on update cascade,
  unique key uni_liv_titulo (liv_titulo)
);