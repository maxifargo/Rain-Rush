package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Clase abstracta que representa una entidad del juego.
 * Sirve como base para objetos que pueden dibujarse, actualizarse y destruirse.
 */
public abstract class Entidad {
    protected float x, y;

    public Entidad(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract void crear();
    public abstract void actualizarMovimiento();
    public abstract void dibujar(SpriteBatch batch);
    public abstract void destruir();
}
