package das;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MineButtons extends JButton {
    @SuppressWarnings("compatibility:1944188491891568663")
    private static final long serialVersionUID = 1L;
    //Признак установлен ли флаг на поле
    private boolean flag;
    //Признак является ли поле открытым
    private boolean statusCell;
    //
    private byte cell;

    public void setStatus(boolean status) {
        this.statusCell = status;
    }

    public boolean isStatus() {
        return statusCell;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag() {
        return flag;
    }


    public void setCell(byte cell) {
        this.cell = cell;
    }

    public byte getCell() {
        return cell;
    }
    
    //Процедура изменяет иконку кнопки в зависимости от значнения cell
    public void setPicture(){       
        switch(cell){
        case 1:this.setIcon(new ImageIcon("1.gif"));
            statusCell= true;
        break;
        case 2:this.setIcon(new ImageIcon("2.gif"));
            statusCell= true;
            break;
        case 3:this.setIcon(new ImageIcon("3.gif"));
            statusCell= true;
            break;
        case 4:this.setIcon(new ImageIcon("4.gif"));
            statusCell= true;
            break;
        case 5:this.setIcon(new ImageIcon("5.gif"));
            statusCell= true;
            break;
        case 6:this.setIcon(new ImageIcon("6.gif"));
            statusCell= true;
            break;
        case 7:this.setIcon(new ImageIcon("7.gif"));
            statusCell= true;
            break;
        case 8:this.setIcon(new ImageIcon("8.gif"));
            statusCell= true;
            break;
        case 9:this.setIcon(new ImageIcon("9.gif"));
            statusCell= true;
            break;
        case 10:this.setIcon(new ImageIcon("mine.gif"));
            break;
        }      
    }
}
