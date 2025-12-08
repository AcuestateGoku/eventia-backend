-- Script para insertar usuario invitado precargado en eventia_db
-- Ejecuta esto en tu MySQL después de que Hibernate cree las tablas

INSERT IGNORE INTO usuarios (nombres, email, password, role, fecha_registro)
VALUES ('Invitado', 'guest@eventia.local', 'guest123', 'INVITADO', NOW());
