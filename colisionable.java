package puppy.code;

import com.badlogic.gdx.math.Rectangle;

/**
 * Interfaz para objetos que pueden colisionar en el juego.
 */
public interface Colisionable {
    Rectangle getArea();
    void onColision(Colisionable otro); 
}
