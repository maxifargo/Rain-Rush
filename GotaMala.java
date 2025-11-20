package puppy.code;

public class GotaMala extends Gota {

    public GotaMala(float x, float y, float velocidad) {
        super(x, y, velocidad, SpriteManager.getInstance().gotaMala);
    }

    @Override
    protected void reproducirSonido() {
        SpriteManager.getInstance().sonidoGotaMala.play(0.35f);
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.restarVida();
    }
}
