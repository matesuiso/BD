use colonia_verano;

set names 'utf8';

set foreign_key_checks = 0;

insert into persona (dni, nombre, apellido) values
	('465498987', 'Juan', 'Farías'),
    ('446579878', 'Brian', 'Terroba'),
    ('456897879', 'Eric', 'Rodriguez'),
    ('459822249', 'Pedro', 'Dellamea'),
    ('286549879', 'Martín', 'Martinez'),
    ('334688846', 'Roberto', 'Mantegazza'),
    ('318799987', 'Gonzalo', 'López'),
    ('329549855', 'Imanol', 'Nichio');

insert into lider (dni, cod_certificado) values
	('286549879', 1),
    ('334688846', 2),
    ('318799987', 3),
    ('329549855', 4);

insert into nino (dni, cod_nino, fecha_nacimiento, telefono) values
	('465498987', 1, '2012-12-15', '3585987632'),
    ('446579878', 2, '2013-01-16', '3584887923'),
    ('456897879', 3, '2012-01-06', '3516654987'),
    ('459822249', 4, '2012-08-22', '3585006600');

insert into asociacion (nombre, direccion, telefono) values
	('T&S', 'Bolivar 529', '3584666899'),
    ('Delfín', 'Constitucion 988', '3584622333'),
    ('Banco Galicia S.A.', 'Hipólito Yrigoyen 3254', '3584666222'),
    ('Municipalidad de Río Cuarto', 'Pje. de la Concepción 650', '35846898765');

insert into certificado (cod_certificado, fecha, grado, nombre_asoc) values
	(1, '2018-05-11', 'Avanzado', 'T&S'),
    (2, '2018-04-22', 'Avanzado', 'Delfín'),
    (3, '2018-04-02', 'Intermedio', 'Banco Galicia S.A.'),
    (4, '2018-02-07', 'Básico', 'Municipalidad de Río Cuarto');

insert into colonia (cod_colonia, nombre_asoc, ubicacion) values
	(1, 'T&S', 'Estados Unidos 100'),
    (2, 'Delfín', 'Caseros 801'),
    (3, 'Banco Galicia S.A.', 'RN A005 Km 604'),
    (4, 'Municipalidad de Río Cuarto', 'Centro Municipal N 1');

insert into asiste (dni, cod_colonia) values
	('465498987', 1),
    ('465498987', 3),
    ('456897879', 2),
    ('459822249', 4);

insert into actividad (cod_actividad, descripcion, cod_colonia, dni_lider) values
	(1, 'Escondidas', 2, '286549879'),
    (2, 'Futbol', 1, '286549879'),
    (3, 'Natación', 2, '334688846'),
    (4, 'Campamento', 2, '318799987'),
    (5, 'Volley', 3, '334688846'),
    (6, 'Handball', 3, '329549855'),
    (7, 'Mancha', 4, '329549855'),
    (8, 'Fornite', 4, '318799987');

insert into juego (cod_actividad, tipo, descripcion, cant_part) values
	(1, 'Recreativo', 'Los jugadores se esconden y uno debe encontrarlos.', 10),
	(7, 'Recreativo', 'Un jugador está "manchado" y debe manchar al resto, que deben huir.', 10),
    (8, 'Juego electrónico', '100 jugadores caen en una isla y deben escapar de la tormenta y matarse entre si.', 100);

insert into deporte (cod_actividad, tipo, accesorios, horas) values
	(2, 'Deporte de equipo', 'Botines, canilleras y pelota', 5),
	(3, 'Deporte individual', 'Antiparras y gorro', 3),
    (5, 'Deporte de equipo', 'Pelota', 5),
    (6, 'Deporte de equipo', 'Pelota', 5);

insert into campamento (cod_actividad, ubicacion, duracion) values
	(4, 'campo', 5);

insert into ayuda (dni, cod_actividad) values
	('286549879', 2),
    ('318799987', 4);

insert into realiza (dni, cod_actividad, fecha_inicio) values
	('465498987', 1, '2018-04-22'),
    ('465498987', 3, '2017-12-26'),
    ('446579878', 4, '2018-02-01'),
    ('459822249', 7, '2018-01-02');

insert into mtel (telefono, dni) values
	('3585321654', '286549879'),
    ('3585667788', '286549879'),
	('3585321987', '334688846'),
	('3584987357', '318799987'),
	('3584951369', '329549855');

set foreign_key_checks = 1;
