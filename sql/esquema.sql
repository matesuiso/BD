drop database if exists colonia_verano;

create database colonia_verano;

use colonia_verano;

create table asociacion (
    nombre varchar(30),
    direccion varchar(30),
    telefono varchar(30),
    primary key (nombre)
);

create table colonia (
	cod_colonia int unsigned auto_increment,
    nombre_asoc varchar(30),
    ubicacion varchar(30),
    primary key (cod_colonia),
    foreign key (nombre_asoc) references asociacion(nombre) on delete cascade
);

create table persona (
	dni varchar(30),
    nombre varchar(30),
    apellido varchar(30),
    primary key (dni)
);

create table certificado (
	cod_certificado int unsigned auto_increment,
    fecha date,
    grado varchar(30),
    nombre_asoc varchar(30),
    primary key (cod_certificado),
    foreign key (nombre_asoc) references asociacion(nombre) on delete cascade
);

create table lider (
    dni varchar(30),
    cod_certificado int unsigned,
    foreign key (dni) references persona(dni) on delete cascade,
    foreign key (cod_certificado) references certificado(cod_certificado) on delete no action
);

create table mtel (
	telefono varchar(30),
    dni varchar(30),
    primary key (telefono, dni),
    foreign key (dni) references lider(dni) on delete cascade
);

create table nino (
	dni varchar(30),
    cod_nino int auto_increment,
    fecha_nacimiento date,
    telefono varchar(30),
    foreign key (dni) references persona(dni) on delete cascade,
    primary key (dni),
    unique (cod_nino)
);

create table asiste (
	dni varchar(30),
    cod_colonia int unsigned,
    primary key (dni, cod_colonia),
    foreign key (dni) references nino(dni) on delete cascade,
    foreign key (cod_colonia) references colonia(cod_colonia) on delete cascade
);

create table actividad (
	cod_actividad int unsigned auto_increment,
    descripcion varchar(255),
    cod_colonia int unsigned,
    dni_lider varchar(30),
    primary key (cod_actividad),
    foreign key (cod_colonia) references colonia(cod_colonia) on delete cascade,
    foreign key (dni_lider) references lider(dni) on delete restrict
);

create table ayuda (
	dni varchar(30),
    cod_actividad int unsigned,
    primary key (dni, cod_actividad),
    foreign key (cod_actividad) references actividad(cod_actividad) on delete cascade,
    foreign key (dni) references lider(dni) on delete cascade
);

create table realiza (
	dni varchar(30),
    cod_actividad int unsigned,
    fecha_inicio date,
    primary key (dni, cod_actividad),
    foreign key (dni) references nino(dni) on delete cascade,
    foreign key (cod_actividad) references actividad(cod_actividad) on delete cascade
);

create table campamento (
	cod_actividad int unsigned,
    ubicacion enum('campo', 'gimnasio', 'pileta', 'arenero'),
    duracion varchar(30),
    primary key (cod_actividad),
    foreign key (cod_actividad) references actividad(cod_actividad) on delete cascade
);

create table deporte (
	cod_actividad int unsigned,
    tipo varchar(30),
    horas int,
    primary key (cod_actividad),
    foreign key (cod_actividad) references actividad(cod_actividad) on delete cascade
);

create table maccesorio (
	cod_actividad int unsigned,
    accesorio varchar(30),
    primary key (cod_actividad, accesorio),
    foreign key (cod_actividad) references deporte(cod_actividad) on delete cascade
);


create table juego (
	cod_actividad int unsigned,
    tipo varchar(30),
    descripcion varchar(255),
    cant_part int,
    primary key (cod_actividad),
    foreign key (cod_actividad) references actividad(cod_actividad) on delete cascade
);

/* Auditoría */
create table historial_lider (
    cod_historial int unsigned auto_increment,
    cod_actividad int unsigned,
    dni_lider_anterior varchar(30),
    dni_lider_nuevo varchar(30),
    usuario varchar(30),
    primary key (cod_historial),
    foreign key (cod_actividad) references actividad(cod_actividad) on delete restrict,
    foreign key (dni_lider_anterior) references lider(dni) on delete restrict,
    foreign key (dni_lider_nuevo) references lider(dni) on delete restrict
);


/* Triggers */
delimiter $$
create trigger chk_edad_nino before insert on nino
  for each row
    begin
    if (new.fecha_nacimiento < '2010-01-01') then
      signal sqlstate '45000'
      set message_text = 'La fecha de nacimiento no puede ser anterior a 01/01/2010';
    end if;
  end;
$$

create trigger audit_cambio_lider after update on actividad
  for each row
    begin
      if (new.dni_lider != old.dni_lider) then
        insert into historial_lider values (null, new.cod_actividad, old.dni_lider, new.dni_lider, user());
      end if;
    end;
$$
