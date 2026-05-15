# Sistema de Alerta por Mensajería — Instrucciones

## Requisitos
- IntelliJ IDEA + JDK 21 o superior

## Cómo probarlo

**1. Abrir el proyecto en IntelliJ** y esperar a que Gradle sincronice.

**2. Ejecutar el servidor:** clic derecho sobre `Main.kt` → Run 'MainKt'

**3. Ejecutar el cliente:** clic derecho sobre `Client.kt` → Run 'ClientKt'

**4. Escribir mensajes en la consola del cliente**, por ejemplo:
- `hay un incendio` → Alerta BAJA
- `hay un accidente y necesitamos ayuda` → Alerta MEDIA
- `hay disparos, heridos, necesitamos ambulancia` → Alerta ALTA
- `!stats` → Estadísticas en tiempo real

**5. Telegram:** para recibir las notificaciones, entrar en [t.me/SistemaAlertaImportante_bot](https://t.me/SistemaAlertaImportante_bot) y pulsar START antes de ejecutar.

## Lo que demuestra el proyecto
- **Hilos** — múltiples clientes simultáneos
- **Corrutinas** — notificación a Telegram de forma asíncrona
- **Sealed classes + when exhaustivo** — tipado de respuestas del servidor
- **Extension functions** — normalización y detección de keywords
- **ProcessBuilder** — logs de seguridad mediante procesos del sistema
