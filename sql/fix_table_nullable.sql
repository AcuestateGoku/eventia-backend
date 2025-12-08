-- Script para ajustar la tabla usuarios y permitir campos opcionales (nulls)
-- Ejecuta esto en MySQL después de haber actualizado el código Java

ALTER TABLE usuarios MODIFY COLUMN apellidos VARCHAR(255) NULL;
ALTER TABLE usuarios MODIFY COLUMN rut VARCHAR(255) NULL;
ALTER TABLE usuarios MODIFY COLUMN fecha_nacimiento DATE NULL;
ALTER TABLE usuarios MODIFY COLUMN genero VARCHAR(255) NULL;
ALTER TABLE usuarios MODIFY COLUMN ciudad VARCHAR(255) NULL;
ALTER TABLE usuarios MODIFY COLUMN celular VARCHAR(255) NULL;

-- Opcional: si tienes registros con apellidos NULL y quieres permitir registros duplicados en email/rut:
ALTER TABLE usuarios MODIFY COLUMN rut VARCHAR(255) NULL UNIQUE;

-- Verificar la estructura de la tabla
DESCRIBE usuarios;
