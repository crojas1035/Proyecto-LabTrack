/*
  Script de creación de base de datos para LabTrack.
  Crea las tablas y agrega datos de prueba.
*/

drop database if exists labtrack;

create database labtrack
  default character set utf8mb4
  default collate utf8mb4_unicode_ci;

use labtrack;

-- Tabla de usuarios
create table usuario (
  id_usuario int not null auto_increment,
  nombre varchar(60) not null,
  correo varchar(100) not null,
  password varchar(255) not null,
  tipo_usuario enum(
    'ADMIN',
    'ENCARGADO',
    'DOCENTE',
    'ESTUDIANTE',
    'TECNICO'
  ) not null,
  activo boolean not null default true,
  fecha_creacion timestamp default current_timestamp,
  fecha_modificacion timestamp default current_timestamp
    on update current_timestamp,
  primary key (id_usuario),
  unique (correo),
  index ndx_usuario_correo (correo)
) engine = InnoDB;

-- Tabla de laboratorios
create table laboratorio (
  id_laboratorio int not null auto_increment,
  codigo varchar(20) not null,
  nombre varchar(60) not null,
  ubicacion varchar(100) not null,
  estado enum(
    'Disponible',
    'Mantenimiento',
    'Cerrado'
  ) not null default 'Disponible',
  activo boolean not null default true,
  fecha_creacion timestamp default current_timestamp,
  fecha_modificacion timestamp default current_timestamp
    on update current_timestamp,
  primary key (id_laboratorio),
  unique (codigo),
  index ndx_laboratorio_nombre (nombre)
) engine = InnoDB;

-- Tabla de categoria de equipos
create table categoria_equipo (
  id_categoria int not null auto_increment,
  nombre varchar(60) not null,
  activo boolean not null default true,
  primary key (id_categoria)
) engine = InnoDB;

-- Tabla de equipos
create table equipo (
  id_equipo int not null auto_increment,
  id_laboratorio int not null,
  id_categoria int not null,
  codigo varchar(20) not null,
  nombre varchar(60) not null,
  descripcion text,
  estado enum(
    'Disponible',
    'En uso',
    'Mantenimiento',
    'Fuera de servicio'
  ) not null default 'Disponible',
  activo boolean not null default true,
  fecha_creacion timestamp default current_timestamp,
  fecha_modificacion timestamp default current_timestamp
    on update current_timestamp,
  primary key (id_equipo),
  unique (codigo),
  index ndx_equipo_laboratorio (id_laboratorio),
  index ndx_equipo_categoria (id_categoria),
  index ndx_equipo_nombre (nombre),
  foreign key fk_equipo_laboratorio (id_laboratorio)
    references laboratorio (id_laboratorio),
  foreign key fk_equipo_categoria (id_categoria)
    references categoria_equipo (id_categoria)
) engine = InnoDB;

-- Tabla de solicitudes de uso
create table solicitud (
  id_solicitud int not null auto_increment,
  id_usuario int not null,
  id_laboratorio int not null,
  fecha_solicitud timestamp default current_timestamp,
  fecha_uso date not null,
  motivo varchar(255) not null,
  estado enum(
    'Pendiente',
    'Aprobada',
    'Rechazada'
  ) not null default 'Pendiente',
  fecha_modificacion timestamp default current_timestamp
    on update current_timestamp,
  primary key (id_solicitud),
  index ndx_solicitud_usuario (id_usuario),
  index ndx_solicitud_laboratorio (id_laboratorio),
  foreign key fk_solicitud_usuario (id_usuario)
    references usuario (id_usuario),
  foreign key fk_solicitud_laboratorio (id_laboratorio)
    references laboratorio (id_laboratorio)
) engine = InnoDB;

-- Tabla de reportes de fallas
create table reporte_falla (
  id_reporte int not null auto_increment,
  id_equipo int not null,
  id_usuario_reporta int not null,
  descripcion_problema text not null,
  fecha_reporte timestamp default current_timestamp,
  estado enum(
    'Pendiente',
    'En proceso',
    'Resuelta'
  ) not null default 'Pendiente',
  fecha_modificacion timestamp default current_timestamp
    on update current_timestamp,
  primary key (id_reporte),
  index ndx_reporte_equipo (id_equipo),
  index ndx_reporte_usuario (id_usuario_reporta),
  foreign key fk_reporte_equipo (id_equipo)
    references equipo (id_equipo),
  foreign key fk_reporte_usuario (id_usuario_reporta)
    references usuario (id_usuario)
) engine = InnoDB;

-- Tabla de prestamos
create table prestamo (
  id_prestamo int not null auto_increment,
  id_usuario int not null,
  fecha_prestamo timestamp default current_timestamp,
  fecha_devolucion_esperada date not null,
  estado enum(
    'Vigente',
    'Devuelto',
    'Atrasado'
  ) not null default 'Vigente',
  activo boolean not null default true,
  fecha_modificacion timestamp default current_timestamp on update current_timestamp,
  primary key (id_prestamo),
  foreign key fk_prestamo_usuario (id_usuario) references usuario (id_usuario)
) engine = InnoDB;

-- Tabla de detalle de prestamos
create table detalle_prestamo (
  id_detalle int not null auto_increment,
  id_prestamo int not null,
  id_equipo int not null,
  devuelto boolean not null default false,
  primary key (id_detalle),
  foreign key fk_detalle_prestamo (id_prestamo) references prestamo (id_prestamo),
  foreign key fk_detalle_equipo (id_equipo) references equipo (id_equipo)
) engine = InnoDB;

-- Datos de prueba para usuarios
insert into usuario
(nombre, correo, password, tipo_usuario, activo)
values
('Ana Solís', 'ana.solis@labtrack.com', 'clave123', 'ADMIN', true),
('Marco Vega', 'marco.vega@labtrack.com', 'clave123', 'ENCARGADO', true),
('Laura Ruiz', 'laura.ruiz@labtrack.com', 'clave123', 'DOCENTE', true),
('Diego Mora', 'diego.mora@labtrack.com', 'clave123', 'ESTUDIANTE', true),
('Sofía Campos', 'sofia.campos@labtrack.com', 'clave123', 'TECNICO', true);

-- Datos de prueba para laboratorios
insert into laboratorio
(codigo, nombre, ubicacion, estado, activo)
values
('LAB-RED-01', 'Laboratorio de Redes', 'Edificio A - Piso 2', 'Disponible', true),
('LAB-PROG-01', 'Laboratorio de Programación', 'Edificio B - Piso 1', 'Disponible', true),
('LAB-ELEC-01', 'Laboratorio de Electrónica', 'Edificio C - Piso 1', 'Mantenimiento', true);

-- Datos de prueba para categoria
insert into categoria_equipo (nombre) values ('Computación'), ('Redes'), ('Electrónica');

-- Datos de prueba para equipos
insert into equipo
(id_laboratorio, id_categoria, codigo, nombre, descripcion, estado, activo)
values
(1, 1, 'EQ-PC-01', 'Computadora Dell 01', 'Equipo para prácticas de redes', 'Disponible', true),
(1, 2, 'EQ-RTR-01', 'Router Cisco 01', 'Router para configuración de redes', 'En uso', true),
(2, 1, 'EQ-PC-02', 'Computadora HP 01', 'Equipo para desarrollo de software', 'Disponible', true),
(2, 1, 'EQ-PC-03', 'Computadora HP 02', 'Equipo con falla en el disco', 'Fuera de servicio', true),
(3, 3, 'EQ-OSC-01', 'Osciloscopio 01', 'Equipo de medición electrónica', 'Mantenimiento', true);

-- Datos de prueba para solicitudes
insert into solicitud
(id_usuario, id_laboratorio, fecha_uso, motivo, estado)
values
(3, 1, '2026-07-15', 'Clase práctica de redes', 'Pendiente'),
(4, 2, '2026-07-16', 'Práctica de programación', 'Aprobada');

-- Datos de prueba para reportes
insert into reporte_falla
(id_equipo, id_usuario_reporta, descripcion_problema, estado)
values
(4, 4, 'La computadora no reconoce el disco de almacenamiento', 'Pendiente'),
(5, 3, 'El equipo presenta mediciones inestables', 'En proceso');

-- Datos de prueba para prestamos
insert into prestamo (id_usuario, fecha_devolucion_esperada, estado) values (4, '2026-08-10', 'Vigente');
insert into detalle_prestamo (id_prestamo, id_equipo, devuelto) values (1, 1, false);
