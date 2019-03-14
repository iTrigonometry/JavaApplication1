package Objects;
//TODO сделать выгрузку сообщений из стека в документ по желанию пользователя
//TODO сделать загрузку сообщений из файла в стек
//TODO реализовать систему Вам пришло новое сообщение, то есть оно записывается в стек и выводится на экран
//TODO вывод сообщений из стека когда этого захочет пользователь
//TODODO сделать регистрацию (регистрация перенесена в отдельный класс)
//TODODO определять читаемый файл в конструкторе класса(по какимто неведомым причинам это так не работает поэтому он определяется каждый раз когда это нужно)
//TODODO сделать рандомное создание сообщений (приготовить данные, сгенерировать их)
//TODODO создать список пользователей чтобы можно было их рандомно выбирать при генерации сообщений
//TODODO изменить seed рандома, вдруг я выяснил что Randem генерирует псевдослучайные числа

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Messages1 {
    private String username, pass;
    private FileInputStream fis;
    private Properties properties;
    private String userpath ;
    private int count;
    private Stack<String> stack = new Stack<String>();
    private Stack<String> tempStack;
    private Iterator iterator;
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //КОНСТРУКТОР
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public Messages1(String username, String pass, boolean flag) {
        this.username = username;
        this.pass = pass;
        //count = countOfStrInFiles();
//        if(flag)
//        addToStack();
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //ДЕЙСТВИЯ СО СТЭКОМ
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //сохраняет данные из стека в файл
    public void saveToFile() {
        iterator = stack.iterator();
        String content = "";
        Path path = Paths.get("secretfiles/" + username + "data.txt");
        Charset charset = StandardCharsets.UTF_8;

        while (iterator.hasNext()) {
            content += iterator.next() + "\n";
        }
        try {
            Files.write(path, content.getBytes(charset));
            System.out.print("\nДанные успешно записаны.");
        }catch (IOException e){
            System.out.print("\nsaveToFIle\nIOEXEPTION");
            System.exit(123);
        }
    }

    //добавляет данные из файла в стэк
    private void addToStack() {
        try {
            File file = new File("secretfiles/" + username + "data.txt");
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                stack.add(scanner.nextLine());
            }
//            scanner.close();
        }catch (FileNotFoundException e){
            System.out.print("\naddToStack\n File not found");
            System.exit(1337);
        }
    }
    //создает новое сообщение и добавляет его в стэк
    public void newMessage() {
            String add = getDate() + " - " + getRandomUser() + " - " + getRandomText();
            stack.add(add);
            writerFromFile();
    }

    //вывод того что есть в стеке
    public void whatInStack(){
        tempStack = stack;
        while(!stack.empty()){
            System.out.print("\n" + stack.pop());
        }
        stack = tempStack;
    }


    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //ПРОВЕРКА ПРАВИЛЬНОСТИ ВВЕДЕННЫХ ДАННЫХ
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public boolean checkAuthorithationData(String username, String pass) {
        try {
            fis = new FileInputStream("secretfiles/users.properties");
            properties = new Properties();
            properties.load(fis);
            if(pass.equals(properties.get(username)))
                return true;
            else return false;
        }catch(FileNotFoundException e){
            System.out.print("\nФайла не существует.");
            System.exit(0);
        }catch(IOException e){
            System.out.print("Непредвиденная оошибка.(не понятно зачем она лол)");
            System.exit(0);
        }
        return true;
    }


    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //ВЫВОД ДАННЫХ ИЗ ФАЙЛА (СООБЩЕНИЙ)
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //читает данные из файла
    public void writerFromFile() {
        userpath = "src/secretfiles/" + username + "data.txt";
        try {
            File file = new File(userpath);
            Scanner scan= new Scanner(file);
            System.out.println();
            while (scan.hasNextLine()){
                System.out.print(scan.nextLine());
                System.out.println();
            }
        }catch(FileNotFoundException e){
            System.out.print("Данные этого пользователя кудато пропали. Обратитесь к дебилу которыый это писал");
        }
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //ВСЕ ЧТО НУЖНО ДЛЯ ГЕНЕРАЦИИ СООБЩЕНИЙ И ЗАПИСИ ИХ В ФАЙЛ
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //генерирует сообщения и запмсывает из в файл, который у каждого узера отдельный
    public void generateRandomMessagess(){
        try{
            int countt = 0;
            Path path = Paths.get("secretfiles/" + username + "data.txt");
            Charset charset = StandardCharsets.UTF_8;
            String content = getDate() + " - " + getRandomUser() + " - " + getRandomText();
            System.out.print("Подождите немного...\nИдет создание вашего документа...\n");
            for (int i = 1;i<10;i++){
                System.out.print(".");
                TimeUnit.SECONDS.sleep(1);
                content += "\n" + getDate() + " - " + getRandomUser() + " - " + getRandomText();
            }
            Files.write(path, content.getBytes(charset));
            System.out.print("\nФайл успешно создан.");
        }catch(IOException e){
            System.out.print("\nGenerateRandomMessagess IOEXEPTION");
            System.exit(5);
        }catch (InterruptedException e){
            System.out.print("\nGenerateRandomMessagess Interupted Exeption");
            System.exit(1488);
        }
    }


    //возвращает текущую дату (используется для создания файла с сообщениями
    private String getDate(){
        return new Date().toString();
    }

    //случайно выбирает пользователя который отправил сообщение (используется для генерации сообщений)
    private String getRandomUser(){
        try {
            Random random = new Random(System.currentTimeMillis());
            String content = Files.readAllLines(Paths.get("secretfiles/users.txt")).get(random.nextInt(count));
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
            String content = Files.readAllLines(Paths.get("secretfiles/textmessages.txt")).get(random.nextInt(18));
            return content;
        }catch (IOException e){
            System.out.print("\ngetRandomText IOExeption");
        }
        return "null";
    }

    //считает строчки в файле
    private int countOfStrInFiles(){
        userpath = "secretfiles/users.txt";
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

}
