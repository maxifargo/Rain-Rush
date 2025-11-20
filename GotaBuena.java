package puppy.code;

public class GotaBuena extends Gota {

    public GotaBuena(float x, float y, float velocidad) {
        super(x, y, velocidad, SpriteManager.getInstance().gotaBuena);
    }

    @Override
    protected void reproducirSonido() {
        SpriteManager.getInstance().sonidoGotaBuena.play(0.35f);
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(10);
    }
}
