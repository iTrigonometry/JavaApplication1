/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class RegistrationClass {
    private String username;
    private int count;
    private String userpath;
    public RegistrationClass(String username){
        this.username = username;
        count = countOfStrInFiles();
    }
    
    
    private int countOfStrInFiles(){
        userpath = "src/secretfiles/users.txt";
        try {
            File file = new File(userpath);
 LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
            count = 0;
            while (null != lineNumberReader.readLine()){
                count++;
            }
            return count;
        }catch (FileNotFoundException e){
            System.out.print("\ncountOfStrInFiles\nFileNotFound");
        }catch(IOException e){
            System.out.print("\ncountOfStrInFiles\nIOExeption");
        }
        return 0;
    }
    
    public boolean isEmptyUsername(){
        
        try{
        Path path = Paths.get("src/secretfiles/users.txt");
            String string = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

            if(!(new String (Files.readAllBytes(path)).contains(username))){
                string += "\n" + username;
                Files.write(path,string.getBytes(StandardCharsets.UTF_8));
                return true;
    }
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "IOEXEPTION IS EMPTY USERNAME");
            return false;
        }
        return false;
    }
    
    public void createUserFile(){
        File file = new File("src/secretfiles/", username + "data.txt");
        
        String result = "";
        for(int i = 0; i<10; i++){
         result += getDate() + " - " + getRandomUser() + " - " + getRandomText() + "\n";   
        }
        try{
        Files.write(Paths.get(file.getAbsolutePath()), result.getBytes(StandardCharsets.UTF_8));
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "123");
        }
    }
        private String getDate(){
        return new Date().toString();
    }

    //случайно выбирает пользователя который отправил 
    //сообщение (используется для генерации сообщений)
    private String getRandomUser(){
        try {
            Random random = new Random(System.currentTimeMillis());
            String content = Files.readAllLines
        (Paths.get("src/secretfiles/users.txt")).get(random.nextInt(count));
            return content;
        }catch (IOException e){
            System.out.print("\ngetRandomUser IOExeption");
        }
        return "null";
    }
    public void writeDataToProperties(String password) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/secretfiles/users.properties")), StandardCharsets.UTF_8);
            content += "\n" + username + "=" + password;
            Files.write(Paths.get("src/secretfiles/users.properties"), content.getBytes(StandardCharsets.UTF_8));
        }catch(IOException e){
            System.exit(1488);
        }
    }

    //возвращает рандомный текст сообщения из заранее заратовленных
    private String getRandomText(){
        try {
            Random random = new Random(System.currentTimeMillis());
            String content = Files.readAllLines
        (Paths.get("src/secretfiles/textmessages.txt")).get(random.nextInt(18));
            return content;
        }catch (IOException e){
            System.out.print("\ngetRandomText IOExeption");
        }
        return "null";
    }
    
    
}
