/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Objects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author admin
 */

public class LogInClass {
    FileInputStream fis;
    Properties properties;
    public boolean checkAuthorithationData(String username, String pass) {
        try {
            fis = new FileInputStream("src/secretfiles/users.properties");
            properties = new Properties();
            properties.load(fis);
            if(pass.equals(properties.get(username)))
                return true;
            else return false;
        }catch(FileNotFoundException e){
            System.out.print("\nФайла не существует.");
            return false;
        }catch(IOException e){
            System.out.print("Непредвиденная оошибка.(не понятно зачем она лол)");
            return false;
        }
    }
    
}
