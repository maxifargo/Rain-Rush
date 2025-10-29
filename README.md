# Rain Rush

**Rain Rush** es un juego arcade de estilo *“atrapa las gotas”* desarrollado en **Java** utilizando el framework **LibGDX**.  
El jugador controla un tarro en la parte inferior de la pantalla, moviéndose lateralmente para recolectar gotas beneficiosas y potenciar su velocidad, mientras esquiva granizos dañinos que reducen sus vidas.

---

## Características Principales

- **Juego de Habilidad:**  
  El objetivo es maximizar la puntuación recolectando gotas buenas.

- **Sistema de Vidas y Puntuación:**  
  El jugador comienza con 3 vidas. Perderá una si es golpeado por una gota mala (granizo).  
  Cada gota buena recolectada suma puntos.

- **Potenciador de Velocidad:**  
  Un tipo especial de gota (*gotaPower*) activa un "boost" temporal que incrementa significativamente la velocidad de movimiento del jugador.

- **Dificultad Progresiva:**  
  El juego incrementa su desafío con el tiempo. A medida que el jugador sube de nivel, la velocidad de caída de las gotas aumenta y el intervalo entre su aparición disminuye.

- **Fondos Dinámicos:**  
  El escenario transiciona automáticamente a través de un ciclo de día, tarde y noche, con fundidos suaves entre cada fondo.

- **Gestión de Pantallas:**  
  La aplicación cuenta con un flujo de juego completo, incluyendo:
  - Menú principal (`MainMenuScreen`)
  - Pantalla de juego (`GameScreen`)
  - Pantalla de pausa (`PausaScreen`)
  - Pantalla de fin de juego (`GameOverScreen`)

- **Sonido y Música:**  
  Incluye música de fondo con una introducción y un bucle principal, además de efectos de sonido para:
  - Recolección de gotas
  - Daño recibido
  - Activación de potenciadores

- **Puntuación Alta (High Score):**  
  El juego guarda y muestra la puntuación más alta alcanzada por el jugador.

---

## Desglose de Clases

### `GameLluviaMenu.java`
Clase principal de la aplicación (extiende `Game`).  
Inicializa los recursos globales como `SpriteBatch` y `BitmapFont`, gestiona la puntuación más alta (`higherScore`) y establece la pantalla inicial (`MainMenuScreen`).

### `MainMenuScreen.java`
Muestra la pantalla de inicio del juego con una imagen de fondo.  
Espera a que el usuario toque la pantalla para comenzar la partida, momento en el que cambia a `GameScreen`.

### `GameScreen.java`
Núcleo del juego.  
Gestiona el ciclo de renderizado, actualiza la lógica del jugador (`Tarro`) y de la lluvia (`Lluvia`), dibuja el HUD (vidas, puntos, nivel), y maneja las transiciones de los fondos dinámicos y la música.

### `Tarro.java`
Representa al jugador.  
Gestiona su posición, movimiento horizontal y estados (normal, herido o con boost de velocidad).  
También almacena las vidas y los puntos actuales.

### `Lluvia.java`
Controla la lógica de los objetos que caen.  
Genera gotas en posiciones aleatorias, determina su tipo (buena, mala o potenciador), detecta colisiones con el `Tarro` y aplica los efectos correspondientes (sumar puntos, restar vida, activar boost).

### `PausaScreen.java`
Pantalla simple que se activa al presionar *ESC* durante el juego, deteniendo la lógica de la partida.

### `GameOverScreen.java`
Se muestra cuando el jugador pierde todas sus vidas.  
Presenta una imagen de "Game Over" y permite reiniciar la partida tocando la pantalla.

---

## Assets Requeridos

Para que el juego funcione correctamente, la carpeta `assets` debe contener los siguientes archivos (referenciados en el código):

### Imágenes
- `menu_background.png`
- `gameover.png`
- `bucket.png` (Jugador)
- `drop.png` (Gota buena)
- `dropBad.png` (Gota mala / Granizo)
- `gotaPower.jpg` (Potenciador)
- `background_day.png`
- `background_evening.png`
- `background_night.png`
- `nube1.png`
- `nube2.png`

### Sonidos y Música
- `hurt.ogg` (Sonido de daño)
- `drop.wav` (Sonido de recolección)
- `powerup.mp3` (Sonido de potenciador)
- `musicintro.ogg` (Música de introducción)
- `musicloop.ogg` (Música en bucle)
- `rain.mp3` (Sonido ambiente de lluvia)

---

## Requisitos del Sistema

- **Lenguaje:** Java 8 o superior  
- **Framework:** LibGDX  
- **Herramienta de compilación:** Gradle o IntelliJ IDEA  
- **Sistema operativo:** Windows, Linux o macOS  

---

## Ejecución del Proyecto

1. Clonar el repositorio:  
   ```
   bash
   git clone https://github.com/usuario/rain-rush.git
2. Abrir el proyecto en tu IDE preferido (por ejemplo, IntelliJ IDEA o Eclipse).

3. Ejecutar la clase principal:
GameLluviaMenu.java

4. Disfruta del juego.
