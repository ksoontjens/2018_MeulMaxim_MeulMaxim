package hellotvxlet;

import java.awt.Color;
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.HStaticText;
import org.havi.ui.HTextButton;
import org.havi.ui.HVisible;


public class NewClass implements Xlet {
  
    
    public void initXlet(XletContext ctx) throws XletStateChangeException {
        HScene scene = HSceneFactory.getInstance().getDefaultHScene();
        // scherm = 720 x 576
        
        HStaticText hst = new HStaticText("Wie was de eerste president van de VS?",20,100,680,100);
        // tekst, x, y, w, h
        hst.setBackgroundMode(HVisible.BACKGROUND_FILL);
        hst.setBackground(Color.BLUE);
        scene.add(hst);
        
        HTextButton button1 = new HTextButton("Bill Clinton", 20,300,320,50);
        button1.setBackgroundMode(HVisible.BACKGROUND_FILL);
        button1.setBackground(Color.BLUE);
        scene.add(button1);
        
        HTextButton button2 = new HTextButton("George Washington", 20,390,320,50);
        button2.setBackgroundMode(HVisible.BACKGROUND_FILL);
        button2.setBackground(Color.BLUE);
        scene.add(button2);
        
        HTextButton button3 = new HTextButton("Donald Trump", 370,300,320,50);
        button2.setBackgroundMode(HVisible.BACKGROUND_FILL);
        button3.setBackground(Color.BLUE);
        scene.add(button3);
        
        HTextButton button4 = new HTextButton("Ronald Reagan", 370,390,320,50);
        button4.setBackgroundMode(HVisible.BACKGROUND_FILL);
        button4.setBackground(Color.BLUE);
        scene.add(button4);
        
        
        
        
        scene.validate();
        scene.setVisible(true);
    }


    public void startXlet() throws XletStateChangeException {
    
    }

    public void pauseXlet() {
     
    }

      
    public void destroyXlet(boolean unconditional) throws XletStateChangeException {
     
    }
    

    
}

    
    



    







