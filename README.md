# Eventia Backend (API REST)

Backend desarrollado con Spring Boot para la plataforma de gestión de eventos **Eventia**.

---

## Flujo de Trabajo (Git Workflow)

Para mantener el código ordenado y evitar conflictos entre nosotros, sigan estos pasos **SIEMPRE** que vayan a trabajar.

### 1. Antes de empezar a programar (Sincronizar)
Cada vez que te sientes a trabajar, asegúrate de tener los últimos cambios que hayan subido los demás a `dev`.

```bash
# 1. Cámbiate a la rama dev
git checkout dev

# 2. Descarga lo último de la nube (para tener el código de todos)
git pull origin dev

# 3. Vuelve a tu rama personal (ej: feature/David, feature/Deya, etc.)
git checkout feature/TU_NOMBRE

# 4. Mezcla lo nuevo de dev en tu rama (para actualizar tu código sin borrar lo tuyo)
git merge dev
```

### 2. Guardar tus avances (Commit)
Trabaja tranquilo en tu código. Cuando termines una parte funcional:

```bash
# Agrega los archivos modificados
git add .

# Guarda los cambios con un mensaje claro
git commit -m "Descripción clara de lo que hiciste"
```

### 3. Subir tus cambios (Push)
Nunca subas directo a `dev` o `main`. Sube siempre a tu rama personal.

```bash
# Sube tu rama a GitHub
git push origin feature/TU_NOMBRE
```

### 4. Unir tu trabajo al proyecto (Pull Request)
Cuando hayas terminado tu tarea y quieras que tu código se una al de todos:

1. Ve al repositorio en **GitHub**.
2. Verás un aviso amarillo/verde que dice **"Compare & pull request"**. Dale clic.
3. **¡IMPORTANTE!**: Asegúrate de que la flecha apunte hacia **`dev`** (base: dev), **NUNCA** a main.
4. Escribe un título y descripción.
5. Dale al botón verde **"Create Pull Request"**.
6. Avisa por el grupo para que revisemos y aprobemos la unión.

---

## Requisitos Previos

Antes de empezar, asegúrate de tener instalado en tu VS Code:
1. **Java 21** o superior.
2. **Extension Pack for Java** (Microsoft).
3. **Spring Boot Extension Pack** (VMware).

---

## Configuración de la Base de Datos (Oracle Cloud)

Este proyecto usa una base de datos Oracle Autónoma en la nube. Para conectarte, necesitas la **Wallet** de seguridad.

### Pasos para configurar tu entorno local:

1. **Descarga la Wallet**: [Enlace a Google Drive](https://drive.google.com/drive/folders/1qWYx6BgXeug1cbAEWXH2EbekB95FDn4s?usp=drive_link)
2. **Descomprime**: Guarda la carpeta descomprimida en una ruta segura y sencilla, por ejemplo: `C:\OracleWallets\EventiaDB`.
3. **Configura el proyecto**:
   - Abre el archivo `src/main/resources/application.properties`.
   - Busca la línea `spring.datasource.url`.
   - **CAMBIA LA RUTA** de `TNS_ADMIN` para que apunte a donde TÚ guardaste la carpeta.

   **Ejemplo:**
   Si tu carpeta está en `C:\MisCosas\Wallet`, la línea debe quedar así (nota las barras normales `/`):
   ```properties
   spring.datasource.url=jdbc:oracle:thin:@eventiadb_tp?TNS_ADMIN=C:/MisCosas/Wallet
   ```

---

## Cómo ejecutar el proyecto

1. Abre una terminal en la carpeta del proyecto.
2. Ejecuta el siguiente comando para limpiar y arrancar:
   ```bash
   .\mvnw clean spring-boot:run
   ```
3. Espera a ver el mensaje: `Started EventiaBackendApplication`.
4. El servidor corre en: `http://localhost:8081`