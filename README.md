Servicios Web CRUD de Usuarios con Spring y MySQL

DESCRIPCION DEL PROYECTO:
Este proyecto consiste en una colección de servicios web RESTful para gestionar una entidad de Usuarios y sus direcciones. La dirección se obtiene mediante la API de SEPOMEX/COPOMEX a partir del código postal, permitiendo realizar operaciones CRUD completas.

TECNOLOGIAS USADAS:

Spring Boot 3.5.4: Framework principal para desarrollo en Java.
Spring MVC: Para crear servicios web REST.
Spring Data JPA: Para gestionar la persistencia en MySQL.
MySQL: Base de datos relacional para almacenamiento.
Lombok: Para reducir código boilerplate en las entidades.
Bean Validation (javax.validation): Para validar datos, como correos electrónicos.
Springdoc OpenAPI: Para generación automática de documentación Swagger UI.
Apache Maven: Gestor de dependencias y construcción del proyecto.
Herramientas de prueba (Postman, Insomnia, curl): Opcionales para probar los endpoints.



INDICACIONES PARA DESPLIEGUE Y USO:

1.-DESCARGAR EL REPOSITORIO:
https://github.com/VirusDavinchi/serviciosUser


2.-CONFIGURACIÓN EN "application.properties":

spring.datasource.url=jdbc:mysql://<host>:<puerto>/<nombre_base_de_datos>?useSSL=false
spring.datasource.username=<tu_usuario>
spring.datasource.password=<tu_contraseña>

3.-EJECUTAR SIGUIENTE SCRIPT DE BASE DE DATOS:
Indicación: Ejecutar el script con el nombre ScriptBaseDeDatos.sql


INDICACIONES PARA HACER PETICIONES EN AWS AMAZON:
Visualizar el documento PDF DocumentacionEvaluacion.pdf , en dicho documento se visualiza las peticiones en postaman al servidor de AWS
En el archivo, JsonDatosPostman.txt, los json y las uris de peticion para el server de AWS.


FUNCIONALIDADES

Crear, Leer, Actualizar y Eliminar usuarios y direcciones por medio del código postal con Integración con API de SEPOMEX.