# Eventia Backend (API REST)

Backend desarrollado con Spring Boot para la plataforma de gestión de eventos **Eventia**.

## Requisitos Previos

Antes de empezar, asegúrate de tener instalado en tu VS Code:
1. **Java 21** o superior.
2. **Extension Pack for Java** (Microsoft).
3. **Spring Boot Extension Pack** (VMware).

## Configuración de la Base de Datos (Oracle Cloud)

Este proyecto usa una base de datos Oracle Autónoma en la nube. Para conectarte, necesitas la **Wallet** de seguridad.

### Pasos para configurar tu entorno local:

1. **Pídeme el archivo de la Wallet**: No está en este repositorio por seguridad. Te pasaré el archivo `Wallet_EventiaDB.zip`.
2. **Descomprime la Wallet**: Guarda la carpeta descomprimida en una ruta segura y sencilla, por ejemplo: `C:\OracleWallets\EventiaDB`.
3. **Configura el proyecto**:
   - Abre el archivo `src/main/resources/application.properties`.
   - Busca la línea `spring.datasource.url`.
   - **CAMBIA LA RUTA** de `TNS_ADMIN` para que apunte a donde TÚ guardaste la carpeta.

   **Ejemplo:**
   Si tu carpeta está en `C:\MisCosas\Wallet`, la línea debe quedar así:
   ```properties
   spring.datasource.url=jdbc:oracle:thin:@eventiadb_tp?TNS_ADMIN=C:/MisCosas/Wallet