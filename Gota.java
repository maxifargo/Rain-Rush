package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Gota implements Colisionable {

    protected float x, y;
    protected float velocidadCaida;
    protected Texture imagen;
    protected Rectangle area;

    public Gota(float x, float y, float velocidadCaida, Texture imagen) {
        this.x = x;
        this.y = y;
        this.velocidadCaida = velocidadCaida;
        this.imagen = imagen;
        this.area = new Rectangle(x, y, 48, 48);
    }

    // =====================================================
    //  TEMPLATE METHOD  (NO SE SOBREESCRIBE)
    // =====================================================
    @Override
    public final void onColision(Colisionable otro) {
        if (otro instanceof Tarro) {
            reproducirSonido();
            aplicarEfecto((Tarro) otro);
        }
    }

    // MÉTODOS ABSTRACTOS DEL TEMPLATE
    protected abstract void reproducirSonido();
    protected abstract void aplicarEfecto(Tarro tarro);

    // =====================================================

    public void mover(float delta) {
        y -= velocidadCaida * delta;
        area.y = y;
        area.x = x; 
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(imagen, x, y, 48, 48);
    }

    public boolean fueraPantalla() {
        return y + 48 < 0;
    }

    @Override
    public Rectangle getArea() {
        return area;
    }
}
