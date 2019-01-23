package das;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WindowSupper {
    private boolean statusGame;
    private short hieght=10,width=10,mine=10,countMine=mine;
    private JFrame window=new JFrame("Сапер");
    private JMenuBar bar=new JMenuBar();
    private JMenu file=new JMenu("File");
    private JPanel playArea=new JPanel();
    private MineButtons[][]gameGrid;
    private JTextField tf_time,tf_mine;
    private short second=0;
    private Timer time;
    private JButton start;
    
    private short counterWin;
    
    //Инициализация игрового окна    
    private WindowSupper() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        bar.add(file);
        window.setJMenuBar(bar);
        //пункты меню 
        JCheckBoxMenuItem low=new JCheckBoxMenuItem("Новичек");
        file.add(low);
        JCheckBoxMenuItem normal=new JCheckBoxMenuItem("Средний");
        file.add(normal);
        JCheckBoxMenuItem hard=new JCheckBoxMenuItem("Сложный");
        file.add(hard);
        JCheckBoxMenuItem custom=new JCheckBoxMenuItem("Настраевыймый");
        file.add(custom);
        JMenuItem exit=new JMenuItem("Выход");
        file.add(exit);
        start=new JButton("Start");       
        
        //Группировка кнопок
        ButtonGroup bg =new ButtonGroup();
        bg.add(low);
        bg.add(normal);
        bg.add(hard);
        bg.add(custom);
        low.setSelected(true);
        
        tf_time=new JTextField("0",3);
        tf_time.setEditable(false);
        tf_time.setFont(new Font("DigitalFont.tft",Font.BOLD,16));
        tf_time.setBackground(Color.black);
        tf_time.setForeground(Color.red);
        tf_time.setBorder(BorderFactory.createLoweredBevelBorder());
        tf_mine=new JTextField(""+countMine,3);        
        tf_mine.setEditable(false);
        tf_mine.setFont(new Font("DigitalFont.tft",Font.BOLD,16));
        tf_mine.setBackground(Color.black);
        tf_mine.setForeground(Color.red);
        tf_mine.setBorder(BorderFactory.createLoweredBevelBorder());
        
        low.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                hieght=10;
                width=10;
                mine=10;
                refrash();                
            }
        });
        normal.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent actionEvent) {
                hieght=20;
                width=20;
                mine=100;
                refrash();                    
            }
        });
        hard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent) {
                hieght=30;
                width=30;
                mine=200;
                refrash();
            }
        });        
        custom.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SetCustom();
            }
        });
        
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        
        //Обработка нажатия кнопки мыши по кнопке старта игры
        start.addActionListener((ActionEvent evt) -> refrash());
        
        window.setLayout(new FlowLayout());
        window.add(tf_mine);
        window.add(start);
        window.add(tf_time);
        window.add(playArea);
        
        time=new Timer(1000,new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    second++;
                    tf_time.setText(""+second);                    
                }
            });        
        refrash();      
    }

    public static void main(String[] args) {
        WindowSupper saper = new WindowSupper();
    }    

    //Обвновления игровой области
    private void refrash(){
        time.stop();
        second=0;
        tf_time.setText(""+0);
        tf_mine.setText(""+mine);
        countMine=mine;
        start.setText("Start");
        counterWin = (short) ((hieght * width) - mine);  
        gameGrid=new MineButtons[hieght][width];
        statusGame=false;        
        playArea.removeAll();
        playArea.setBackground(Color.white);
        playArea.setLayout(new GridLayout(hieght, width));        
        for (short i=0;i<hieght;i++){
            for (short j=0; j < width; j++){                
                gameGrid[i][j]=new MineButtons();  
                gameGrid[i][j].setPreferredSize(new Dimension(20, 20));
                gameGrid[i][j].addMouseListener(new MouseAdapter(){
                    public void mousePressed(MouseEvent ev){
                        if (ev.getButton()==MouseEvent.BUTTON3){  
                            //Устанавливаем флаг и уменьшаем счетчик мин, если игра запущена                            
                            if(statusGame==false){
                                if (!time.isRunning()){
                                    time.start();                                    
                                }
                            for (short i=0;i<hieght;i++){
                                for (short j=0;j<width;j++){
                                    if (ev.getSource()==gameGrid[i][j]){
                                        if (gameGrid[i][j].getIcon()==null){
                                            gameGrid[i][j].setIcon(new ImageIcon("flag.gif"));
                                            gameGrid[i][j].setFlag(true);
                                            countMine--;
                                            tf_mine.setText(""+countMine);                        
                                        }
                                        else {
                                            if (gameGrid[i][j].isStatus()==false){
                                            gameGrid[i][j].setIcon(null);
                                            countMine++;
                                            tf_mine.setText(""+countMine);
                                            gameGrid[i][j].setFlag(false);
                                            }
                                        }
                                    }
                            }}}
                        }
                        if (ev.getButton()==MouseEvent.BUTTON1){
                            //Обрабатываем нажатие левой кнопки мыши на игровом поле 
                            if (statusGame==false){
                                if (!time.isRunning()){
                                    time.start();
                                }                                    
                            for (short i=0;i<hieght;i++){
                                for (short j=0;j<width;j++){
                                    if (ev.getSource()==gameGrid[i][j]){
                                        if (gameGrid[i][j].getFlag()==false){
                                        openCell(i,j);
                                        gameGrid[i][j].setStatus(true);                   
                                        }
                                    }
                            }}}                            
                        }
                    }
                });                
                playArea.add(gameGrid[i][j]);               
            }
        }
        setMine();
        window.setSize(width*20+10,hieght*20+90);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false);
    }   
    
    //Установка мин на игровом поле
    private void setMine(){ 
        //Расстановка мин
        for (short k=0;k<mine;k++){            
                short i = (short) Math.floor((Math.random() * hieght ));
                short j = (short) Math.floor((Math.random() * width ));
            if (gameGrid[i][j].getCell()!=10)    
                gameGrid[i][j].setCell((byte)10);
                else k--;            
            }
        //Расстановка цифр
        for (short i=0;i<hieght;i++){
            for(short j=0;j<width;j++){
                if (gameGrid[i][j].getCell()!=10){  
                    gameGrid[i][j].setCell((byte)0);
                //проверяем поля вокруг на наличие мин
                for (short k = (short) (i - 1); k<i+2;k++){
                    for(short t = (short) (j - 1); t<j+2;t++){
                        if (k>-1&&k<hieght&&t>-1&&t<width){
                            if (gameGrid[k][t].getCell()==10){
                                gameGrid[i][j].setCell((byte)(gameGrid[i][j].getCell()+1));
                            }  
                        }
                    }
                }
                }
            }
        }
    }   
    
    //Открываем пустые ячейки если вокруг нет ни одной мины
    private void open(short i,short j){
    byte massi=2,massj=2;
    if (gameGrid[i][j].isStatus()==false){
         gameGrid[i][j].setStatus(true);
         gameGrid[i][j].setIcon(new ImageIcon("0.gif"));
        if ((i==0)||(i==hieght-1)){
            massi--;            
        }
        if (((j==0)||(j==width-1))){
            massj--;
        }
        if ((i<=hieght)&&(i>=1)){
            i--;
            }                         
        if ((j<=width)&&(j>=1)){
            j--;
        }
        short x = (short) (i + massi);
        short y = (short) (j + massj);
        for (short n=i;n<=x;n++){
            for (short t=j;t<=y;t++){                               
                openCell(n, t);
        }}
        }}  
    
    //Установка соответсвующих изображений для кнопок и в случае если в клетке находистя бомба то останавливаем игру
    private void openCell(short i, short j) {
        if(gameGrid[i][j].getIcon()==null){
            gameGrid[i][j].setPicture();
            if (gameGrid[i][j].getCell()==0){
                counterWin--;
                open(i,j);
            } else if(gameGrid[i][j].getCell()==10){
                start.setText("Fail!");
                time.stop();
                openMine();
                statusGame=true;     
            } else{
                counterWin--;
                }            
            
            if (counterWin==0){
                start.setText("Win!");
                if (time.isRunning()){
                    time.stop();    
                }                
                statusGame=true;
            }
        }  
        
    }   
    
    //Окно с кастомными настройками 
    private void SetCustom(){
        JFrame custome =new JFrame("Кастомизация");      
        custome.setLayout(new GridLayout(4, 2, 2, 2)); 
        
        JTextField CustomeHieght=new JTextField("10");
        custome.add(new JLabel("Ячеек в высоту"),BorderLayout.WEST);        
        custome.add(CustomeHieght);
        
        JTextField CustomeLenght=new JTextField("10");
        custome.add(new JLabel("Ячеек в ширину"),BorderLayout.WEST);
        custome.add(CustomeLenght);
        JTextField CustomeMine=new JTextField("10");
        custome.add(new JLabel("Количество мин"),BorderLayout.WEST);
        custome.add(CustomeMine);
        
        //Кнопка Отмена
        JButton cancel= new JButton("Отмена");        
        custome.add(cancel);         
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                custome.dispose();
            }
        });        
        
        //Кнопка Ок
        JButton ok =new JButton("Принять");
        custome.add(ok);        
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Обработка ошибок ввода не числовых значений.
                try {
                    short temp_hieght = (short) Integer.parseInt(CustomeHieght.getText());
                    short temp_width = (short) Integer.parseInt(CustomeLenght.getText());
                    short temp_mine = (short) Integer.parseInt(CustomeMine.getText());              
                    //Количество мин должно быть больше 0
                    //колличество строк и колонок должно быть больше 1
                    if (temp_mine>0||temp_hieght>1||temp_width>1) {
                        if (temp_mine<(temp_hieght*temp_width-1)){
                            hieght = temp_hieght;
                            width = temp_width;
                            mine = temp_mine;
                            refrash();
                            custome.dispose();                    
                            }
                        else{
                            JOptionPane.showMessageDialog(null,"Колличество мин должно быть меньше чем колличество клеток");
                            }
                        }
                    else JOptionPane.showMessageDialog(null,"Некорректно задано колличесство строк колонок или мин");
                    } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Введено некоректное значение в одном из полей");
                    }
                }
            });
        
        custome.setSize(200,150);
        custome.setLocationRelativeTo(null);
        custome.setVisible(true);
        custome.requestFocusInWindow();
    }
    //Выполняем откытие мин в случае проигрыша игрока
    private void openMine() {
        for( int i=0; i< gameGrid.length ; i++){
            for (int j=0 ;j<gameGrid.length;j++){
                if (gameGrid[i][j].getCell()==10){
                    gameGrid[i][j].setIcon(new ImageIcon("mine.gif"));
                }
            }            
        }
    }
}
