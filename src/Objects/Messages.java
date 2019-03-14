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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author admin
 */
public class Messages {
    String userpath;
    String username;
    private int count;
    public Messages(String username, JTextArea jTextArea){
        this.username = username;
        writerFromFile(jTextArea);
        count = countOfStrInFiles();
    }
    
    //пишет данные из файла 
    private void writerFromFile(JTextArea jTextArea) {
        userpath = "src/secretfiles/"+ username + "data.txt";
        try {
            File file = new File(userpath);
            Scanner scan= new Scanner(file);
            String content = "";
            while (scan.hasNextLine()){
                content += scan.nextLine() + "\n";
            }
            jTextArea.setText(content);
        }catch(FileNotFoundException e){
            System.out.print("Данные этого пользователя кудато пропали. "
                    + "Обратитесь к дебилу которыый это писал");
        }
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
    
    public void newMessage(JTextArea jTextArea) {
            String add = getDate() + " - " 
                    + getRandomUser() + " - " + getRandomText();
            String content = jTextArea.getText() + add + "\n";
            jTextArea.setText(content);
    }
    
    
    
    
    
    
    
    
    //БЛОК ДЛЯ СОХРАНЕНИЯ ДАННЫХ В ФАЙЛ
    
    public void saveToFile(JTextArea jTextArea) {
        
        String content = jTextArea.getText();
        Path path = Paths.get("src/secretfiles/" + username + "data.txt");
        Charset charset = StandardCharsets.UTF_8;
        try {
            Files.write(path, content.getBytes(charset));
            JOptionPane.showMessageDialog(null, "Данные успешно записаны!");
        }catch (IOException e){
            System.out.print("\nsaveToFIle\nIOEXEPTION");
            System.exit(123);
        }
    }
    
    
    
    
    
    
}
