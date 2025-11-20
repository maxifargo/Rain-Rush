package puppy.code;

public class DefaultGotaFactory implements GotaFactory {

    @Override
    public Gota crearGotaBuena(float x, float y, float velocidad) {
        return new GotaBuena(x, y, velocidad);
    }

    @Override
    public Gota crearGotaMala(float x, float y, float velocidad) {
        return new GotaMala(x, y, velocidad);
    }

    @Override
    public Gota crearGotaPower(float x, float y, float velocidad) {
        return new GotaPower(x, y, velocidad);
    }
}
