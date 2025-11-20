package puppy.code;

public interface GotaFactory {
    Gota crearGotaBuena(float x, float y, float velocidad);
    Gota crearGotaMala(float x, float y, float velocidad);
    Gota crearGotaPower(float x, float y, float velocidad);
}
