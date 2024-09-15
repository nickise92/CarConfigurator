package univr.ing.carconfigurator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtenteTest {
    
    @Test
    void authenticator() {
        Utente test = new Utente("PB002A");
        
        assertTrue(test.authenticator(test.getUserID(), "admin02"));
        assertFalse(test.authenticator(test.getUserID(), "admin01"));
    }
    
    @Test
    void checkID() {
        
        assertTrue(Utente.checkID("MB100V") instanceof Venditore);
        assertTrue(Utente.checkID("Nick992") instanceof Cliente);
        assertTrue(Utente.checkID("PB002A") instanceof Impiegato);
        assertFalse(Utente.checkID("Nick992") instanceof Venditore);
    }
    
    @Test
    void getUserInfo() {
        // MB100V,Mario,Bianchi,vendor01,Verona,
        Utente test = new Utente("MB100V");
        
        assertEquals(test.getUserID(), "MB100V");
        assertEquals(test.getUserName(), "Mario");
        assertEquals(test.getUserLastName(), "Bianchi");
        assertEquals(test.getUserPsw(), "vendor01");
    }
    
    @Test
    void getShop() {
        Utente test = new Venditore("MB100V");
        
        assertEquals(((Venditore) test).getShop(), "Verona");
    }
}