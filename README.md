
# RESTfulWS
Evidencia de Computación avanzada en Java

## Instalación
Descarga el archivo WAR
Instálalo en un Tomcat compatible (8 en adelante)

## Uso

### Index

 - **/api/v1/**
	 - **GET**: Lista los recursos disponibles.
	 - **OPTIONS**: Documentación del recurso.

### File

 - **/api/v1/file**
	 -  **GET**: Descarga un archivo mediante el parámetro path.
	 `api/v1/file/?path=`
	 -   **POST**: Sube un archivo mediante los parámetros name, dir y file.
	 `
api/v1/file/ name="test.jpg" dir="/temp" file@/temp/test2.png --form
`
	- **DELETE**: Elimina un archivo mediante el parámetro path.
`api/v1/file/?path=
`
- **OPTIONS**: Documentación del recurso.

### Directory
- **api/v1/directory**
	- **GET**: Lista los archivos de un directorio mediante el parámetro dir.
	`api/v1/directory/?dir=
`
	- **OPTIONS**: Documentación del recurso.
### Notify
- **api/v1/notify**
	- **GET**: Lista las notificaciones enviadas.
	- **POST**: Envía una notificación mediante los parámetros subject, message, toAddress y ccAddress.
	- **OPTIONS**: Documentación del recurso.
### User
- **api/v1/user**
	- **GET**: Lista los usuarios.
	- **POST**: Crea un usuario mediante los parámetros username, password y fullName.
	- **OPTIONS**: Documentación del recurso.
- **api/v1/user/{username}**
	- **GET**: Muestra la información del usuario.
	- **PUT**: Actualiza la información del usuario mediante los parámetros username, password y fullName.
	- **DELETE**: Elimina al usuario.
	-  **OPTIONS**: Documentación del recurso.
