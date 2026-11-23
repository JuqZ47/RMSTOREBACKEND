# RMSTORE — microservicio de Productos

Responsable del equipo: José Marcet. Java 17 o superior, Spring Boot 3.5.7,
MySQL 8.4, JPA y Flyway. Mantiene productos e inventario de la tienda RMSTORE.

## Ejecutar

Con MySQL configurado (el ZIP completo incluye Compose y el script de inicio),
desde esta carpeta:

```bash
./mvnw -B -ntp spring-boot:run
```

Valores locales predeterminados: API `http://127.0.0.1:8081/api/productos`,
base `rmstore` en `127.0.0.1:3317`, usuario `rmstore_app`. La contraseña
de demostración y las variables compatibles se encuentran en la configuración
de aplicación y `.env.example` del proyecto completo. Puede utilizarse DB_URL,
DB_USER, DB_PASSWORD, DB_PORT, SERVER_PORT y CORS_ORIGIN para otro entorno.

La base debe existir y el usuario debe tener permisos; Flyway crea tablas,
restricciones e imágenes y carga los seis productos originales una sola vez.

## API

| Método | Ruta | Resultado |
| --- | --- | --- |
| GET | /api/productos | 200 lista |
| GET | /api/productos/{id} | 200 o 404 |
| POST | /api/productos | 201, validación 400, duplicado 409 |
| PUT | /api/productos/{id} | 200, 400 o 404 |
| DELETE | /api/productos/{id} | 204 o 404 |
| POST | /api/productos/descontar-stock | 200, 400, 404 o 409 |

El descuento recibe `items` con `id` y `cantidad`. Agrupa códigos repetidos,
bloquea las filas y valida todo antes de descontar. No tiene claves de
idempotencia. La API todavía no implementa JWT ni Swagger.

## Pruebas y cobertura

```bash
./mvnw -B -ntp clean verify
```

19 pruebas con JUnit, Mockito y MockMvc. El build exige 60 % por clase en
instrucciones, ramas, métodos y líneas. Abrir `target/site/jacoco/index.html`.
Los reportes del corte de entrega se incluyen también en `evidencias/`.

El archivo ZIP no incluye `.env`, dependencias Maven, `target` ni una base real.
Se reconstruye con el Wrapper y una instancia MySQL configurada.
