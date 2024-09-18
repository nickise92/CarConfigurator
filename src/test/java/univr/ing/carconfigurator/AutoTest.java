package univr.ing.carconfigurator;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AutoTest {
    
    Auto auto;
    
    
    @Test
    void getImgPath() {
        auto = new Auto("Toyota", "Yaris");
        
        assertEquals("img/Toyota/Yaris/0.jpg", auto.getImgPath(0));
    }
    
    @Test
    void getOptionalCount() {
        auto = new Auto("Toyota", "Yaris");
        int count = auto.getOptionalCount();
    
        assertEquals(0, count);
        
        auto.setSensor(new Optional("Sensori di parcheggio", OptTypes.SENSORI));
        
        count = auto.getOptionalCount();
        assertEquals(1, count);
        
    }
}