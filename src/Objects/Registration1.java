package Objects;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

//TODO при авторизации записывать это все в два файла (имя юзера в users.txt, а пароль юзера + юзера в users.properties)
//TODO сделать создание файла для пользователя при регистрации

//ОТДЕЛЬНЫЙ ПЛАН ДЕЙСТВИЙ
/*
Пользователь вводит данные.
Теперь мы должны проверить эти данные. Так как если они уже присутсвуют, то нужно об этом предупредить и начать вводить данные заново TODODO вынести в оотдельный метод ввод данных
Если таких данных не было обнаружено, то мы для начала записывваем данные в файл, а только потом создаем файл пользователя и генерируем в него сообщения

ПРОБЛЕМЫ КОТОРЫЕ МЕНЯ БЕСПОКОЯТ
так как мы используем для хранения паролей файл properties, то тут возникает очень не приятная вещь как кодировка. при ввводе русских символов вв properties, мы получаем просто набор непонятных символов.
Решение этой проблемы методом фикса кодировки не катит, так как это того не стОит.
А вот например заставить пользователя вводить только латинские символы стОит
 */
public class Registration1 {

    private String username, password;
    public Registration1(){
        
    }

    private void createUserFile(){
        File file = new File("src/secretfiles/", username + "data.txt");
        Messages1 messages = new Messages1(username, password, false);
        messages.generateRandomMessagess();
    }

        private void writeDataToProperties() {
            try {
                String content = new String(Files.readAllBytes(Paths.get("secretfiles/users.properties")), StandardCharsets.UTF_8);
                content += "\n" + username + "=" + password;
                Files.write(Paths.get("secretfiles/users.properties"), content.getBytes(StandardCharsets.UTF_8));
            }catch(IOException e){
                System.exit(1488);
            }
        }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //ВВОД ЛОГИНА ПОЛЬЗОВАТЕЛЯ
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private String inputUsername(){
        String result = "11";
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        try{
        do{
            result = scanner.next();
            username = result;
            Path path = Paths.get("src/secretfiles/users.txt");
            String string = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

            if(!(new String (Files.readAllBytes(path)).contains(username))){
                string += "\n" + username;
                Files.write(path,string.getBytes(StandardCharsets.UTF_8));
                return result;
            }
            System.out.print("\n Такой пользователь уже существует. Введите другой логин: ");
        }while(flag);
        }catch (IOException e) {
            System.exit(112);
        }
        return result;
    }
}
