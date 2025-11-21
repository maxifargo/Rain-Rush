package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Tarro extends Entidad implements Colisionable {

    private Texture imagen;
    private Rectangle area;
    private int vidas;
    private int puntos;

    private boolean herido = false;
    private long tiempoHeridoInicio = 0L;
    private static final long DURACION_HERIDA = 1_000_000_000L;

    private float velocidad = 300f;
    private float gravedad = -900f;
    private float velocidadY = 0f;
    private boolean enSuelo = true;
    private float rotacion = 0f;
    private float rotacionObjetivo = 0f;

    private boolean boostActivo = false;
    private long tiempoBoostInicio = 0L;
    private static final long DURACION_BOOST = 5_000_000_000L;


    // -------------------------------------------------------------
    // USANDO SINGLETON (no se pasan recursos por parámetro)
    // -------------------------------------------------------------
    public Tarro() {
        super(0, 0);

        this.imagen = SpriteManager.getInstance().tarro;
        this.vidas = 3;
        this.puntos = 0;

        float ancho = 48;
        float alto = 56;
        this.area = new Rectangle(800 / 2f - ancho / 2f, 20, ancho, alto);
    }


    @Override
    public void crear() {
        area.x = 800 / 2f - area.width / 2f;
        area.y = 20;
        velocidadY = 0f;
        enSuelo = true;
        vidas = 3;
        puntos = 0;
        herido = false;
        boostActivo = false;
    }


    @Override
    public void actualizarMovimiento() {
        float delta = Gdx.graphics.getDeltaTime();

        if (herido && (System.nanoTime() - tiempoHeridoInicio > DURACION_HERIDA))
            herido = false;

        if (boostActivo && (System.nanoTime() - tiempoBoostInicio > DURACION_BOOST)) {
            boostActivo = false;
            velocidad = 300f;
        }

        boolean moviendo = false;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            area.x -= velocidad * delta;
            rotacionObjetivo = 10f;
            moviendo = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            area.x += velocidad * delta;
            rotacionObjetivo = -10f;
            moviendo = true;
        }
        if (!moviendo)
            rotacionObjetivo = 0f;

        rotacion += (rotacionObjetivo - rotacion) * 5f * delta;

        if (area.x < 0) area.x = 0;
        if (area.x > 800 - area.width) area.x = 800 - area.width;

        this.x = area.x;
        this.y = area.y;

        if (!enSuelo) {
            velocidadY += gravedad * delta;
            area.y += velocidadY * delta;
        }

        if (area.y <= 20) {
            area.y = 20;
            velocidadY = 0;
            enSuelo = true;
        }
    }


    @Override
    public void dibujar(SpriteBatch batch) {

        if (herido) {
            float t = (System.nanoTime() / 150_000_000) % 2;
            batch.setColor(t == 0 ? Color.RED : Color.WHITE);
        } else if (boostActivo) {
            batch.setColor(Color.CYAN);
        } else {
            batch.setColor(Color.WHITE);
        }

        float offsetX = (area.width - 64) / 2f;
        float offsetY = (area.height - 64) / 2f;

        batch.draw(imagen, area.x + offsetX, area.y + offsetY,
                32, 32, 64, 64,
                1f, 1f, rotacion,
                0, 0, 64, 64,
                false, false);

        batch.setColor(Color.WHITE);
    }


    // ----- INTERFAZ -----
    @Override
    public Rectangle getArea() { return area; }

    @Override
    public void onColision(Colisionable otro) { // El tarro no reacciona directamente;
        										// las gotas manejan la lógica de colisión.
    }
 


    // ----- Lógica propia -----
    public void dañar() {
        if (!herido) {
            herido = true;
            tiempoHeridoInicio = System.nanoTime();
            vidas--;
            SpriteManager.getInstance().sonidoGotaMala.play(0.35f);
        }
    }


    public void restarVida() { dañar(); }

    public void activarBoostVelocidad() {
        boostActivo = true;
        tiempoBoostInicio = System.nanoTime();
        velocidad = 550f;
    }
    
    @Override
    public void destruir() {
        // El tarro no destruye texturas (SpriteManager las maneja)
    }


    public int getVidas() { return vidas; }
    public int getPuntos() { return puntos; }
    public void sumarPuntos(int cantidad) { puntos += cantidad; }
}
