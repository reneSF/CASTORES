# Instrucciones para Ejecutar el Proyecto Inventario

Estas instrucciones te guiarán para ejecutar el proyecto Spring Boot de inventario con Thymeleaf desde cero.

---

## 1. Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java 17** o superior
- **Maven 3.x** o Gradle (según tu proyecto)
- **Git** (opcional, si vas a clonar el repositorio)
- **Base de datos** (H2, MySQL, PostgreSQL, según configuración del proyecto)
- IDE recomendado: **IntelliJ IDEA**, **Eclipse** o **VS Code** con soporte Java

---

## 2. Clonar el proyecto (opcional)

Si trabajas desde un repositorio remoto:

```bash
git clone https://github.com/tuusuario/tu-proyecto.git
cd tu-proyecto

## 🛠️ Guía de Inicio Rápido (Spring Boot)

Siga estos pasos para configurar y ejecutar el proyecto localmente.

### 1. Requisitos Previos
Antes de comenzar, asegúrese de tener instalado:
* **Java JDK 17** o superior.
* **Maven 3.6+** (opcional, el proyecto incluye el wrapper `./mvnw`).
* **MySQL Server 8.0** en ejecución.

### 2. Configuración de la Base de Datos
Cree una base de datos en MySQL llamada `inventario_db` (o el nombre que haya definido en su script SQL). 
Luego, verifique la conexión en el archivo:
`src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nombre_tu_bd
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

## 🗄️ Modelo de Base de Datos

La persistencia de datos se gestiona en MySQL. A continuación se detalla la estructura de las tablas y sus relaciones.

### 📊 Diagrama de Entidad-Relación (ERD)
El sistema consta de 4 entidades principales que gestionan la seguridad y el flujo del inventario:
1. **Roles y Usuarios:** Gestión de accesos (RBAC).
2. **Productos:** Catálogo maestro de artículos.
3. **Movimientos:** Histórico detallado de entradas y salidas.



[Image of entity relationship diagram for inventory management system]


### 📖 Diccionario de Datos

#### 1. Tabla: `roles`
Almacena los perfiles de acceso (ej. ADMIN, ALMACENISTA).
* `id_rol`: Identificador único (PK).
* `nombre_rol`: Nombre descriptivo del permiso.

#### 2. Tabla: `usuarios`
Usuarios registrados que pueden operar el sistema.
* `id_rol`: Llave foránea que define los permisos (FK).
* `estatus`: Define si el usuario está activo para iniciar sesión.

#### 3. Tabla: `productos`
Artículos disponibles en el inventario.
* `cantidad`: Saldo actual del producto (Inicia en 0 por regla de negocio).
* `estatus`: Controla la baja lógica (activo/inactivo) sin eliminar el registro.

#### 4. Tabla: `movimientos`
Bitácora de auditoría para cada transacción.
* `tipo_movimiento`: Clasificación mediante ENUM (ENTRADA o SALIDA).
* `id_producto` / `id_usuario`: Relaciona quién hizo qué y sobre qué artículo.

---

### 📥 Script de Inicialización (Datos de Prueba)
Para que el sistema sea funcional tras la instalación, se deben ejecutar los siguientes inserts básicos:

```sql
INSERT INTO roles (nombre_rol) VALUES ('ROLE_ADMIN'), ('ROLE_ALMACENISTA');

-- Nota: Las contraseñas deben estar encriptadas con BCrypt si se insertan manualmente.
INSERT INTO usuarios (nombre, correo, password, id_rol) 
VALUES ('Administrador', 'admin@castores.com', '$2a$10$xyz...', 1);

## 📂 Estructura del Proyecto

El proyecto sigue la estructura estándar de **Maven** y el patrón de diseño **MVC** (Modelo-Vista-Controlador):

```text
src/main/java/com/castores/inventario/
├── config/                 # Configuración de Seguridad (Spring Security)
├── controller/             # Controladores Web (Rutas y Navegación)
│   ├── InventarioController.java
│   └── ViewController.java
├── model/                  # Entidades JPA (Mapeo de Base de Datos)
│   ├── Producto.java
│   ├── Usuario.java
│   ├── Movimiento.java
│   └── TipoMovimiento.java
├── repository/             # Acceso a Datos (Interfaces JpaRepository)
│   ├── ProductoRepository.java
│   ├── UsuarioRepository.java
│   └── MovimientoRepository.java
├── service/                # Interfaces de Lógica de Negocio
│   ├── InventarioService.java
│   ├── UsuarioService.java
│   └── MovimientoService.java
│   └── impl/               # Implementaciones de los Servicios (@Service)
│       ├── InventarioServiceImpl.java
│       ├── UsuarioServiceImpl.java
│       └── MovimientoServiceImpl.java
└── InventarioApplication.java # Clase Principal de Spring Boot

src/main/resources/
├── static/                 # Recursos Estáticos (CSS, JS, Imágenes)
│   ├── css/
│   └── js/
├── templates/              # Vistas HTML (Thymeleaf)
│   ├── inventario/         # Vistas de Productos (lista.html, nuevo.html)
│   ├── login.html          # Pantalla de Acceso
│   ├── salida.html         # Registro de Salidas
│   └── historico.html      # Reporte de Movimientos
└── application.properties  # Configuración (BD, Puerto, Dialecto)

## 🔐 Seguridad y Control de Acceso

La seguridad es el núcleo de la aplicación, implementada mediante **Spring Security 6**. Se utiliza un modelo de **Control de Acceso Basado en Roles (RBAC)** para garantizar que cada usuario acceda únicamente a las funciones que le corresponden.

### 🛡️ Configuración de Seguridad (`SecurityConfig`)

El sistema protege los recursos mediante una cadena de filtros (`SecurityFilterChain`) que define las siguientes reglas:

* **Acceso Público:** Las rutas de recursos estáticos (`/css/**`, `/js/**`) y la página de inicio de sesión (`/ui/login`) son accesibles para todos.
* **Filtros por Rol:**
    * **Inventario:** Tanto `ADMIN` como `ALMACENISTA` pueden visualizar y gestionar el stock.
    * **Salidas:** Ruta restringida exclusivamente al rol `ALMACENISTA`.
    * **Histórico:** Reporte de auditoría restringido exclusivamente al rol `ADMIN`.
* **Protección CSRF:** Deshabilitado (`csrf.disable()`) para simplificar el envío de formularios de inventario en este entorno de desarrollo.
* **Gestión de Sesión:** Formulario de login personalizado con redirección automática tras el éxito.



### 👥 Gestión de Usuarios y Encriptación (`UserConfig`)

Para esta versión, se ha implementado un gestor de usuarios en memoria, asegurando que el despliegue sea inmediato y funcional para el evaluador.

#### Algoritmo de Encriptación
Se utiliza **BCryptPasswordEncoder**. Este es un algoritmo de hashing fuerte que incluye un "salt" aleatorio, protegiendo las credenciales contra ataques de diccionario y tablas arcoíris.



#### Cuentas de Acceso Preconfiguradas
| Usuario | Contraseña | Rol | Alcance |
| :--- | :--- | :--- | :--- |
| `admin` | `admin123` | **ROLE_ADMIN** | Gestión total y auditoría. |
| `almacen` | `almacen123` | **ROLE_ALMACENISTA** | Operaciones de inventario y salidas. |

---

### ⚙️ Flujo de Autenticación
1. El usuario intenta acceder a una ruta protegida (ej. `/ui/inventario`).
2. Spring Security intercepta la petición y redirige a `/ui/login`.
3. El `UserDetailsService` valida las credenciales y el rol.
4. Si es exitoso, el usuario es redirigido a la vista solicitada según sus permisos.

## 🎮 Capa de Controladores (Endpoints)

Los controladores en este proyecto gestionan la lógica de navegación y las peticiones API, siguiendo una estructura organizada por funcionalidades:

### 📂 Detalle de Controladores (`com.castores.inventario.controller`)

| Controlador | Funcionalidad Principal |
| :--- | :--- |
| **`InventarioController`** | Gestión central del catálogo de productos: altas, bajas, reactivaciones y edición. |
| **`AuthController`** | Manejo de peticiones relacionadas con la autenticación y validación de sesiones. |
| **`HistoricoController`** | Gestión de la vista de auditoría para consultar el registro detallado de movimientos. |
| **`SalidaInventarioController`** | Lógica específica para el registro de egresos de mercancía y actualización de stock. |
| **`UsuarioController`** | Administración de cuentas de usuario, asignación de roles y gestión de estatus. |
| **`ViewController`** | Controlador de utilidad para mapear rutas simples hacia plantillas HTML estáticas. |
| **`HomeController`** | Punto de entrada inicial de la aplicación y redirección al tablero principal. |
| **`TestController`** | Endpoint de diagnóstico para verificar la conectividad y el estado de los servicios. |


### 🛠️ Endpoints Principales

A continuación se detallan las rutas críticas configuradas en los controladores:

#### 📦 Inventario (`/ui/inventario`)
* **GET** `/`: Lista todos los productos activos e inactivos.
* **GET** `/nuevo`: Muestra el formulario de registro de productos.
* **POST** `/guardar`: Procesa la creación o actualización de un artículo.
* **POST** `/baja/{id}`: Ejecuta la baja lógica del producto.

#### 📉 Salidas y Auditoría
* **GET** `/ui/salida`: Formulario para registrar salidas de stock.
* **GET** `/ui/historico`: Visualización de la tabla de movimientos `ENTRADA/SALIDA`.

#### 🔐 Seguridad
* **GET** `/ui/login`: Renderiza la pantalla de acceso personalizada.
* **POST** `/login`: Endpoint interno procesado por Spring Security para la validación de credenciales.

## ⚙️ Capa de Lógica y Persistencia

El sistema se apoya en una arquitectura de servicios para garantizar que las reglas de negocio de **Castores** se apliquen de forma consistente y segura.

### 🛠️ Servicios (`com.castores.inventario.service`)

Esta capa actúa como el "cerebro" de la aplicación, desacoplando la base de datos de la interfaz de usuario.

| Interfaz / Implementación | Responsabilidad Clave |
| :--- | :--- |
| **`InventarioService`** | Gestiona el ciclo de vida del producto, incluyendo la **baja lógica** y el método `buscarPorId`. |
| **`MovimientoService`** | Registra cada transacción (ENTRADA/SALIDA) asegurando la trazabilidad de quién y cuándo realizó el cambio. |
| **`UsuarioService`** | Administra los perfiles de acceso y coordina la encriptación de credenciales con Security. |



### 🗄️ Repositorios (`com.castores.inventario.repository`)

Se utiliza **Spring Data JPA** para la comunicación con MySQL. Los repositorios abstraen las consultas SQL complejas mediante métodos con nombres semánticos:

* **`ProductoRepository`**: Permite realizar operaciones CRUD y filtrado por estatus activo/inactivo.
* **`UsuarioRepository`**: Incluye métodos personalizados como `findByCorreo` para la autenticación.
* **`MovimientoRepository`**: Recupera el histórico de transacciones vinculando entidades de Producto y Usuario.

---

## 🧠 Principios de Desarrollo Aplicados

Para asegurar la calidad del código, el proyecto implementa:

1. **Inyección de Dependencias (DI):** Implementada mediante constructores, facilitando el desacoplamiento y las pruebas unitarias.
2. **Separación de Responsabilidades (SoC):** Cada capa (Controlador, Servicio, Repositorio) tiene una función única y clara.
3. **Integridad Referencial:** Uso de relaciones `@ManyToOne` y `@OneToMany` para mantener la coherencia entre productos y sus movimientos de stock.



---

### 🚀 Resumen Técnico Final
* **Backend:** Java 17 + Spring Boot 3.5.x
* **Persistencia:** Hibernate + Spring Data JPA
* **Seguridad:** Spring Security (RBAC con BCrypt)
* **Vistas:** HTML5 + Thymeleaf + Bootstrap 5

## 🗄️ Capa de Persistencia (Repositories)

La comunicación con la base de datos MySQL se realiza a través de **Spring Data JPA**. Esta capa abstrae la complejidad del SQL mediante interfaces que extienden de `JpaRepository`, permitiendo operaciones CRUD automáticas y consultas personalizadas mediante palabras clave semánticas.

### 📂 Detalle de Repositorios (`com.castores.inventario.repository`)

| Repositorio | Entidad Asociada | Funciones Clave |
| :--- | :--- | :--- |
| **`ProductoRepository`** | `Producto` | Gestión del ciclo de vida de artículos, filtrado por nombre y control de estatus (baja lógica). |
| **`UsuarioRepository`** | `Usuario` | Localización de usuarios por correo electrónico para procesos de autenticación y validación de duplicados. |
| **`MovimientoRepository`** | `Movimiento` | Registro y consulta del historial de transacciones; permite auditar entradas y salidas por producto. |


### 💡 Ventajas de esta Implementación

* **Consultas Automáticas:** Gracias a la nomenclatura de Spring Data, métodos como `findByCorreo` en `UsuarioRepository` se convierten automáticamente en consultas SQL optimizadas.
* **Integridad de Datos:** Al trabajar directamente con los objetos del modelo (`Producto`, `Usuario`, `Movimiento`), se garantiza que los tipos de datos sean correctos antes de llegar a la base de datos.
* **Paginación y Ordenamiento:** Las interfaces están preparadas para manejar grandes volúmenes de datos mediante las capacidades nativas de ordenamiento de JPA.

---

## 💾 Capa de Acceso a Datos y Lógica de Negocio

El sistema garantiza la integridad de la información y la trazabilidad de las operaciones mediante dos capas especializadas que interactúan entre sí.

### 🗄️ Repositorios (`com.castores.inventario.repository`)

Esta capa utiliza **Spring Data JPA** para gestionar la persistencia en MySQL de forma eficiente. Cada repositorio es una interfaz que extiende de `JpaRepository` para proveer operaciones CRUD y consultas personalizadas.

| Repositorio | Entidad | Funcionalidad |
| :--- | :--- | :--- |
| **`ProductoRepository`** | `Producto` | Gestión del stock y control de baja lógica. |
| **`UsuarioRepository`** | `Usuario` | Localización de usuarios para procesos de autenticación. |
| **`MovimientoRepository`** | `Movimiento` | Registro histórico de auditoría de entradas y salidas. |


### ⚙️ Capa de Servicios (`com.castores.inventario.service`)

La lógica de negocio reside en esta capa, separando las reglas operativas de la persistencia de datos. Se sigue el patrón de **Interfaz e Implementación** para facilitar el mantenimiento y las pruebas unitarias.

#### Implementaciones Principales (`/service/impl`):
* **`InventarioServiceImpl`**: Coordina las altas, ediciones y el estatus de los artículos.
* **`MovimientoServiceImpl`**: Ejecuta la lógica para registrar transacciones y actualizar saldos de inventario.
* **`UsuarioServiceImpl`**: Gestiona el registro y seguridad de las cuentas de usuario.

| Interfaz | Implementación |
| :--- | :--- |
| `InventarioService` | `InventarioServiceImpl` |
| `MovimientoService` | `MovimientoServiceImpl` |
| `UsuarioService` | `UsuarioServiceImpl` |


---

### 🚀 Clase Principal de Arranque
La aplicación es impulsada por la clase `InventarioCastoresApplication.java`, la cual inicializa el contexto de Spring Boot, activa el escaneo de componentes y configura el servidor embebido.

## 🎨 Recursos Estáticos y Vistas (Frontend)

La interfaz de usuario está desarrollada utilizando **Thymeleaf**, lo que permite una integración nativa con Spring Boot para renderizar datos dinámicos en el servidor. Todos los recursos se encuentran organizados en la carpeta `src/main/resources`.

### 📂 Estructura de Recursos (`/resources`)

#### 🛠️ Archivos Estáticos (`/static`)
Contiene los elementos visuales que no cambian y que dan identidad corporativa a la aplicación:
* **`css/castores.css`**: Estilos personalizados que definen la paleta de colores y el diseño de tablas/formularios de Castores.
* **`js/main.js`**: Funciones de JavaScript para validaciones en el cliente y manejo de eventos dinámicos.
* **`img/`**: Almacén de logotipos y recursos gráficos del proyecto.

#### 📄 Plantillas Dinámicas (`/templates`)
Vistas HTML procesadas por el motor **Thymeleaf**:
* **`login.html`**: Pantalla de acceso personalizada conectada a Spring Security.
* **`inventario/`**: Contiene `lista.html` (dashboard principal) y `nuevo.html` (formulario de registro/edición).
* **`historico.html`**: Tabla detallada para la auditoría de movimientos.
* **`salida.html`**: Interfaz optimizada para el registro rápido de egresos de mercancía.


### ⚙️ Configuración del Sistema (`application.properties`)
Ubicado en la raíz de recursos, este archivo centraliza la configuración crítica:
* **Conexión a BD**: URL de JDBC, usuario y contraseña de MySQL.
* **Puerto del Servidor**: Configurado para evitar conflictos con otros servicios (ej. `server.port=8081`).
* **Dialecto JPA**: Optimización de consultas para el motor InnoDB de MySQL.

---
