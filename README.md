# Sis_Objetos_Perdidos
Sistema de Objetos Perdidos para la Universidad Peruana Unión (UPeU). Proyecto FullStack con Angular 20 y Spring Boot 3.5.5 que permite registrar objetos encontrados, gestionar solicitudes, validar entregas y administrar usuarios.
# 🧭 Sistema de Objetos Perdidos – UPeU  
### Gestión completa de objetos encontrados, reclamos, solicitudes y administración.

Este sistema permite registrar objetos encontrados en el campus, gestionarlos desde un panel de administración y brindar a los estudiantes la capacidad de buscarlos, reclamarlos y visualizar el estado de sus solicitudes.

---

## 🚀 Tecnologías Utilizadas

### **Frontend**
- ![Angular](https://img.shields.io/badge/Angular-v20-red)
- ![TypeScript](https://img.shields.io/badge/TypeScript-5-blue)
- ![CSS3](https://img.shields.io/badge/CSS3-styled-blue)
- Diseño responsivo + UI institucional UPeU

### **Backend**
- ![Spring Boot](https://img.shields.io/badge/SpringBoot-3.5.5-brightgreen)
- ![Java](https://img.shields.io/badge/Java-21-orange)
- ![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
- ![JWT](https://img.shields.io/badge/Auth-JWT-yellow)

---

## 📌 Características Principales

### 🔐 *Autenticación y Roles*
- Inicio de sesión con JWT
- Roles: **Administrador** y **Estudiante**

### 🛠 *Panel Administrador*
- Registrar objetos encontrados  
- Validar solicitudes  
- Entregar objetos  
- Historial completo  
- Gestión de usuarios (solo estudiantes)  
- KPIs con:
  - Objetos activos  
  - Solicitudes pendientes  
  - Solicitudes aprobadas  
  - Total de estudiantes

### 🎒 *Panel Estudiante*
- Buscar objetos disponibles  
- Realizar solicitudes  
- Visualizar estado: *Pendiente, Aprobada o Rechazada*  
- Perfil del estudiante  
- Nombre cargado desde `localStorage` luego del login

---
## 📡 Endpoints Principales

### 🔐 Autenticación
POST /api/auth/login  
POST /api/auth/registrar  

### 🎒 Objetos
GET /api/objetos/listar  
POST /api/objetos/registrar  
PUT /api/objetos/actualizar/{id}  

### 🧍 Estudiantes
GET /api/estudiantes/datos/{usuarioId}  
PUT /api/estudiantes/completar  

### 📄 Solicitudes
POST /api/solicitudes/crear  
GET /api/solicitudes/estudiante/{id}  
PUT /api/solicitudes/aprobar/{id}  
PUT /api/solicitudes/rechazar/{id}


## ⚙️ Instalación del Proyecto

### 📌 **Clonar el repositorio**
```bash
git clone https://github.com/Julio-Pedro-A-P/Sis_Objetos_Perdidos.git
cd Sis_Objetos_Perdidos
 

