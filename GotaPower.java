package puppy.code;

public class GotaPower extends Gota {

    public GotaPower(float x, float y, float velocidad) {
        super(x, y, velocidad, SpriteManager.getInstance().gotaPower);
    }

    @Override
    protected void reproducirSonido() {
        SpriteManager.getInstance().sonidoGotaPower.play(0.35f);
    }

    @Override
    protected void aplicarEfecto(Tarro tarro) {
        tarro.activarBoostVelocidad();
    }
}
