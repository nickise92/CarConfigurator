package univr.ing.carconfigurator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtenteTest {
    
    private Utente user;
    
    @Test
    void authenticator() {
        user = new Utente("Test", "Tizio", "Caio", "sempronio");
        
        assertTrue(user.authenticator("Test", "sempronio"));
        assertFalse(user.authenticator("Test", "password"));
    }
  
    @Test
    void checkID() {
        user = Utente.checkID("MB100V"); // Venditore
        
        assertTrue(user instanceof Venditore);
        assertFalse(user instanceof Cliente);
        assertFalse(user instanceof Impiegato);
        
        user = Utente.checkID("Nick992"); // Cliente
    
        assertFalse(user instanceof Venditore);
        assertTrue(user instanceof Cliente);
        assertFalse(user instanceof Impiegato);
        
        user = Utente.checkID("PB002A"); // Impiegato
        
        assertFalse(user instanceof Venditore);
        assertFalse(user instanceof Cliente);
        assertTrue(user instanceof Impiegato);
    }
}