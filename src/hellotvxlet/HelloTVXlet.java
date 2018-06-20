package hellotvxlet;

import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;
import org.bluray.ui.event.HRcEvent;
import org.davic.resources.ResourceClient;
import org.davic.resources.ResourceProxy;
import org.dvb.event.EventManager;
import org.dvb.event.UserEvent;
import org.dvb.event.UserEventListener;
import org.dvb.event.UserEventRepository;
import org.havi.ui.HBackgroundConfigTemplate;
import org.havi.ui.HBackgroundDevice;
import org.havi.ui.HBackgroundImage;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.HScreen;
import org.havi.ui.HStaticText;
import org.havi.ui.HStillImageBackgroundConfiguration;
import org.havi.ui.HVisible;
import org.havi.ui.event.HBackgroundImageEvent;
import org.havi.ui.event.HBackgroundImageListener;


public class HelloTVXlet implements Xlet, ResourceClient, HBackgroundImageListener,
UserEventListener {
    
    // C:\Program Files\TechnoTrend\TT-MHP-Browser\filio\DSMCC\0.0.3
    // Dit is de map waar de afbeeldingen zich bevinden! 
    
    // variabelen die nodig zijn, kunnen we ook bij initXlet in het begin initialiseren en declareren. 
    HScreen screenCover; 
    HBackgroundDevice hbgDevice;
    HBackgroundConfigTemplate hbgTemplate;
    HStillImageBackgroundConfiguration hbgConfiguration;

    int imageGeladen = 0;
    int huidigeImage = 0; 
    HBackgroundImage image[] = new HBackgroundImage[7];
    
    private HScene scene;
    private HStaticText infoOfAlbum; 
    private HStaticText hsText;
    
    private boolean endInfo = true; 
    private boolean visibleInfo = true;
     
    // public HelloTVXlet () {  
    //}
    
    public void initXlet(XletContext ctx) throws XletStateChangeException {
        
        screenCover = HScreen.getDefaultHScreen();
        hbgDevice = screenCover.getDefaultHBackgroundDevice();
        
        if ( hbgDevice.reserveDevice(this)) {
            System.out.println("Achtergrond device succesvol gereserveerd");
        } else {
            System.out.println("Achtergrond device niet gereserveerd");
        }       
        
        hbgTemplate = new HBackgroundConfigTemplate();
        hbgTemplate.setPreference(HBackgroundConfigTemplate.STILL_IMAGE, HBackgroundConfigTemplate.REQUIRED);
        hbgConfiguration = (HStillImageBackgroundConfiguration) hbgDevice.getBestConfiguration(hbgTemplate);
       
        // ^------ "globale" variabele
        
        try {
            hbgDevice.setBackgroundConfiguration(hbgConfiguration);
        } catch (Exception ex ) {
            ex.printStackTrace();
        }
        
        // bovenaan HBackgroundImage image[] = new HBackgroundImage[7].
        // Dit toont dat het een array is met 7 afbeeldingen, die zitten van index 0-6 
        // ophalen van de images en daarna er door lopen mbv een for lus. 
        // Images zitten in C:\Program Files\TechnoTrend\TT-MHP-Browser\filio\DSMCC\0.0.3
        image[0] = new HBackgroundImage("DAMN.png");
        image[1] = new HBackgroundImage("ROYALTY.png");
        image[2] = new HBackgroundImage("REVIVAL.png");
        image[3] = new HBackgroundImage("LEMONADE.png");
        image[4] = new HBackgroundImage("BRINGINGMADNESS.png");
        image[5] = new HBackgroundImage("BeautifulAndDamned.png");
        image[6] = new HBackgroundImage("TheEnd.png");
        
        for ( int i=0; i < 7; i++ ) {
            image[i].load(this);
        }
        
        // interface HBackgroundImageListener toevoegen aan Xlet
        
        UserEventRepository eventRepo = new UserEventRepository("name");
        eventRepo.addAllArrowKeys(); // pijltoetsen. 
        eventRepo.addKey(HRcEvent.VK_ENTER);
        EventManager.getInstance().addUserEventListener(this, eventRepo);
        
        // private HScene scene; --> genereren default scene
        scene = HSceneFactory.getInstance().getDefaultHScene();
        
        // private HStaticText hsText; --> Text toevoegen aan scene. 
        // hsText zal de prijs van het totaal aantal albums geven dat je hebt gekocht,
        // infoOfAlbum bevat de informatie over alle albums.
        // " \n" krijgen we op de volgend rij zorgt voor wat ruimte. 
        hsText = new HStaticText (" \n", 420, 65, 280, 380); // x, y, w, h 
        infoOfAlbum = new HStaticText ( " \n", 400, 320, 320, 400); // x, y, w, h 
        hsText.setHorizontalAlignment(HStaticText.HALIGN_RIGHT);
        hsText.setVerticalAlignment(HStaticText.VALIGN_TOP);
        
        scene.add(hsText); // prijs toevoegen aan de scene. 
        scene.add(infoOfAlbum); // album informatie toevoegen aan scene. 
        scene.validate();
        scene.setVisible(true);
        
    }
     
    public void imageLoaded(HBackgroundImageEvent e) {
        
        System.out.println("Image succesvol geladen");
        imageGeladen++;
        
        if ( imageGeladen == 5 ) {
            try {
                hbgConfiguration.displayImage(image[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void userEventReceived(UserEvent e) {
        
        String infoAlbums = infoOfAlbum.getTextContent(HVisible.NORMAL_STATE);
        String totaleBestelling = hsText.getTextContent(HVisible.NORMAL_STATE);
        // totale bestelling is prijs die getoont wordt door HVisible van
        // de hsText dat de totale prijs van de albums bevat. 
        
        if ( e.getType() == HRcEvent.KEY_PRESSED) {
                if ( e.getCode() == HRcEvent.VK_ENTER) {
                if ( huidigeImage == 0) {
                    totaleBestelling = totaleBestelling + "Kendrick Lamar - DAMN \n"; 
                }
                
                if ( huidigeImage == 1) {
                    totaleBestelling = totaleBestelling + "Chris brown - ROYALTY \n"; 
                }
                
                if ( huidigeImage == 2) {
                    totaleBestelling = totaleBestelling + "Eminem - REVIVAL \n "; 
                }
                
                if ( huidigeImage == 3) {
                    totaleBestelling = totaleBestelling + "Beyonce - LEMONADE \n"; 
                }
                
                if ( huidigeImage == 4) {
                    totaleBestelling = totaleBestelling + "Dimitri Vegas & Like Mike \n" +
                    " - Bringing The Madness \n"; 
                }
                
                if ( huidigeImage == 5) {
                    totaleBestelling = totaleBestelling + "G-Eazy - THE BEAUTIFUL \n " +
                    "AND DAMNED \n"; 
                }
                
                hsText.setTextContent(totaleBestelling, HVisible.NORMAL_STATE);
                hsText.repaint();
                // hsText = totale prijs van albums
                
            }
                
            if ( e.getCode() == HRcEvent.VK_UP) {
                if ( visibleInfo == true ) {
                    // switch? 
                    if( huidigeImage == 0 ) {
                        infoAlbums = "€19.99";
                        visibleInfo = false;
                    }
                    
                    if( huidigeImage == 1 ) {
                        infoAlbums = "€15.75";
                        visibleInfo = false;
                    }
                    
                    if( huidigeImage == 2 ) {
                        infoAlbums = "€21.99";
                        visibleInfo = false;
                    }
                    
                    if( huidigeImage == 3 ) {
                        infoAlbums = "€8.68";
                        visibleInfo = false;
                    }
                    
                    if( huidigeImage == 4 ) {
                        infoAlbums = "€17.99";
                        visibleInfo = false;
                    }
                    
                    if( huidigeImage == 5 ) {
                        infoAlbums = "€18.99";
                        visibleInfo = false;
                    }
                } else {
                    infoAlbums = "";
                    visibleInfo = true;
                }
                
                infoOfAlbum.setTextContent(infoAlbums, HVisible.NORMAL_STATE);
                infoOfAlbum.repaint();
            }    
                
            if ( e.getCode() == HRcEvent.VK_RIGHT) {
                huidigeImage++;
                infoAlbums = "";
                visibleInfo = true;
                
                if ( huidigeImage > 5 ) {
                    huidigeImage = 0;
                }
            }
                
            if ( e.getCode() == HRcEvent.VK_LEFT) {
                infoAlbums = "";
                visibleInfo = true;
                huidigeImage--;
                
                if ( huidigeImage < 5 ) {
                    huidigeImage = 5;
                }
            }
                
            if ( e.getCode() == HRcEvent.VK_DOWN) {
                if ( endInfo == false ) {
                    infoAlbums = "";
                    totaleBestelling = " \n"; // namen van bestellingingen leegmaken voor de eind cover. 
                    huidigeImage = 6;
                    endInfo = true;
                } else {
                    huidigeImage = 1;
                    infoAlbums = "";
                    endInfo = false;
                }
                
                hsText.setTextContent(totaleBestelling, HVisible.NORMAL_STATE);
                hsText.repaint();
            }
                
                infoOfAlbum.setTextContent(infoAlbums, HVisible.NORMAL_STATE);
                infoOfAlbum.repaint();
            
        }
        
        try {
            hbgConfiguration.displayImage( image[huidigeImage] );
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
    
    
    public void imageLoadFailed(HBackgroundImageEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void pauseXlet() {
        
    }
    
    public void startXlet() {
        
    }
    
    public boolean requestRelease(ResourceProxy proxy, Object requestData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void release(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void notifyRelease(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
  
    public void destroyXlet(boolean unconditional) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
    
   

    
    



    







