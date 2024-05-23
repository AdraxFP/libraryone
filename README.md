# Library Project

Este proyecto es una aplicación de biblioteca construida con Spring Boot. Permite gestionar libros y autores, y obtiene información de libros utilizando la API de Gutendex.

## Descripción

La aplicación permite:
- Mostrar todos los libros.
- Agregar nuevos libros.
- Listar todos los autores.
- Listar autores vivos en un año determinado.
- Mostrar estadísticas de libros por idioma.

## Herramientas Utilizadas

- **Spring Boot**: Framework para la creación de aplicaciones Java.
- **Spring Data JPA**: Para la persistencia de datos utilizando JPA.
- **PostgreSQL**: Base de datos relacional.
- **Jackson**: Biblioteca para trabajar con JSON.
- **Maven**: Herramienta de gestión y construcción de proyectos.
- **Gutendex API**: Para obtener información de libros.

## Nota sobre JPA

En algunas configuraciones, **Spring Data JPA** puede experimentar problemas, como errores en la resolución de dependencias. Asegúrate de tener las dependencias correctas en tu archivo `pom.xml` y de actualizar y reconstruir tu proyecto si encuentras problemas.

## Dependencias

- `spring-boot-starter-data-jpa`
- `spring-boot-starter-web`
- `spring-boot-devtools`
- `postgresql`
- `spring-boot-starter-test`
