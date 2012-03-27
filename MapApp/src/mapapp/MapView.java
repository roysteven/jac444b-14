/*
 * MapView.java
 */

package mapapp;

import gglMpsSttcAccessor.GoogleMapGeocoding;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;

import org.apache.commons.httpclient.HttpException;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.xml.xpath.XPathException;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdesktop.swingx.mapviewer.WaypointRenderer;
import org.jdesktop.swingx.mapviewer.wms.WMSService;
import org.jdesktop.swingx.mapviewer.wms.WMSTileFactory;

//import components.FileChooserDemo;

/**
 * The application's main frame.
 */
public class MapView extends FrameView {

    public MapView(SingleFrameApplication app) {
        super(app);

        initComponents();
        
        //WMSService wms = new WMSService();
        //wms.setBaseUrl("http://132.156.10.87/cgi-bin/atlaswms_en?REQUEST=GetCapabilities");
//        wms.setLayer();
        //jXMapKit1.setTileFactory(new WMSTileFactory(wms));

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox(ActionEvent e) {
        if (aboutBox == null) {
            JFrame mainFrame = MapApp.getApplication().getMainFrame();
            aboutBox = new MapAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        MapApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jXMapKit1 = new org.jdesktop.swingx.JXMapKit();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        
        // New component of drop down list for the location searching
        //2012.03.24
        locationList = new javax.swing.JComboBox();
        String[] locationExamples = {
                "Toronto, Canada",
                "Chicago, USA"
        };
        locationList = new JComboBox(locationExamples);
        // 2012.03.25 Initial Component - FileChooser
        file_chooser = new JFileChooser();
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        //2012。03。06
        locationEdit = new javax.swing.JTextField();
//        jButton3 = new javax.swing.JButton(); // For the zoom Out
//        jButton4 = new javax.swing.JButton(); // For the zoom In       
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem imageSaveItem = new javax.swing.JMenuItem();// 2012.03.15 Create image save item        
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jXMapKit1.setDefaultProvider(org.jdesktop.swingx.JXMapKit.DefaultProviders.OpenStreetMaps);
        jXMapKit1.setDataProviderCreditShown(true);
        jXMapKit1.setName("jXMapKit1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(mapapp.MapApp.class).getContext().getActionMap(MapView.class, this);
        jButton1.setAction(actionMap.get("goChicago")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setAction(actionMap.get("addWaypoint")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        
        // Add the new JComBox to the UI
        //2012.03.24
        locationList.setAction(actionMap.get("geogList"));
        locationList.setName("jComBox1");
        
        // Add the new JEditBox to the UI
        //2012.03.24
        locationEdit.setAction(actionMap.get("editAct"));
        locationEdit.setName("jEditBox");

//        jButton3.setAction(actionMap.get("zoomOut")); // NOI18N
//        jButton3.setName("jButton3"); // NOI18N        
//
//        jButton4.setAction(actionMap.get("zoomIn")); // NOI18N
//        jButton4.setName("jButton4"); // NOI18N 
        
        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .add(locationList)  //2012.03.24
                .add(locationEdit)  //2012.03.26
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton2)
                .add(183, 183, 183))
            .add(jXMapKit1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, mainPanelLayout.createSequentialGroup()
                .add(jXMapKit1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(locationList)  //2012.03.24
                    .add(locationEdit)  //2012.03.24
                    .add(jButton2)))
        );

        menuBar.setName("menuBar"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(mapapp.MapApp.class).getContext().getResourceMap(MapView.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);
        
        //2012.03.25 Create a image save item
        imageSaveItem.setAction(actionMap.get("imageSave"));
        imageSaveItem.setName("ImageSave");
        fileMenu.add(imageSaveItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
            .add(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(statusMessageLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 228, Short.MAX_VALUE)
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(statusMessageLabel)
                    .add(statusAnimationLabel)
                    .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    @org.jdesktop.application.Action
    public void goChicago() {
        // put your action code here
        //jXMapKit1.setCenterPosition(new GeoPosition(41.881944,-87.627778));
        jXMapKit1.setAddressLocation(new GeoPosition(41.881944,-87.627778));
    }
    
    //This is the new method for the location drop down list
    //This method will perform the selection action from UI
    // and change the map to the specification location(lat, lon)
    //2012.03.24
    @org.jdesktop.application.Action
    public void geogList() throws HttpException, IOException, XPathException {
    	//modify by chen jian hui for the feather to get lat/lng from geo-google api by the name of address;
    	String saddress = (String)locationList.getSelectedItem();
        GoogleMapGeocoding gmg = new GoogleMapGeocoding();
        gmg.InitRequestCommand();
        if (gmg.Geocoding(saddress) && gmg.IsOkey())
        {
            gmg.GetAddressInfo();
            jXMapKit1.setAddressLocation(new GeoPosition(gmg.GetLat(),gmg.GetLng()));
            jXMapKit1.setZoom(4);
            //gmg.QuitGoogle();//modify by chen jian hui: Don't need to disconnect to google anymore, 
            				   //it automatically disconnect by itself when the task is done well.
        }
        //if ( loc.equalsIgnoreCase("Chicago, USA")){
        //	jXMapKit1.setAddressLocation(new GeoPosition(41.881944,-87.627778));
       /* }
        if ( loc.equalsIgnoreCase("Toronto, Canada")){
        	jXMapKit1.setAddressLocation(new GeoPosition(43.670906,-79.393331));
        }	*/               
    }
    
    //This is the new method for the address enter by detail of particular location
    //then,click on the 'ok' button to get the map
    //2012.03.26
    @org.jdesktop.application.Action
    public void editAct() throws HttpException, IOException, XPathException {
    	//modify by chen jian hui for the feather to get lat/lng from geo-google api by the name of address;
    	String saddress = locationEdit.getText();
    	if(saddress.isEmpty())
    		saddress = locationEdit.getSelectedText();
        GoogleMapGeocoding gmg = new GoogleMapGeocoding();
        gmg.InitRequestCommand();
        if (gmg.Geocoding(saddress) && gmg.IsOkey())
        {
            gmg.GetAddressInfo();
            jXMapKit1.setAddressLocation(new GeoPosition(gmg.GetLat(),gmg.GetLng()));
            jXMapKit1.setZoom(4);
            locationList.insertItemAt(saddress, 0);//add by chen jian hui for collecting the data to combox list.2012.03.27
            //gmg.QuitGoogle();//modify by chen jian hui: Don't need to disconnect to google anymore, 
            				   //it automatically disconnect by itself when the task is done well.
        }               
    }
    //This is the function to save the graphic of google map
    @org.jdesktop.application.Action
    public void imageSave(){
    	int  fc_return_value = file_chooser.showSaveDialog(null);
    	
        if (fc_return_value == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();
            
            JXMapViewer map = jXMapKit1.getMainMap();
            int height = map.getBounds().height;
            int width = map.getBounds().width;
            
            BufferedImage buf = map.getGraphicsConfiguration().createCompatibleImage(width, height);
            if (buf == null) buf = new BufferedImage( width , height, BufferedImage.TYPE_INT_RGB);
                 
            try{
            	ImageIO.write( buf, null, file );
            }catch( Exception e )
            { 
            	System.out.println("File save failed!");
            }  
            
//            Graphics2D g = buf.createGraphics();
//            jXMapKit1.paintAll(g);
//            g.drawImage( map.createImage( width, height) , 0, 0, null);           
        }
    }
//    @org.jdesktop.application.Action
//    public void zoomOut() {
//   		i = i - 1;
//   		jXMapKit1.setZoom(i);
//    }
//    @org.jdesktop.application.Action
//    public void zoomIn() {
//   		i = i + 1;
//   		jXMapKit1.setZoom(i);
//    }    

    @org.jdesktop.application.Action
    public void addWaypoint() {
        Set<Waypoint> waypoints = new HashSet<Waypoint>();
        waypoints.add(new Waypoint(41.881944,-87.627778));
        waypoints.add(new Waypoint(40.716667,-74));
        
        WaypointPainter painter = new WaypointPainter();
        painter.setWaypoints(waypoints);
        painter.setRenderer(new WaypointRenderer() {
            public boolean paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint wp) {
                g.setColor(Color.RED);
                g.drawLine(-5,-5,+5,+5);
                g.drawLine(-5,+5,+5,-5);
                return true;
            }
        });
        
        jXMapKit1.getMainMap().setOverlayPainter(painter);
        // put your action code here
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
/*    private javax.swing.JButton jButton3; // New button for zoom 
    private javax.swing.JButton jButton4; // New button for zoom    
    private int i = 9; // zoom default
*/  
    //2012.03.24
    private JComboBox locationList;
    //2012。03。26
    private JTextField locationEdit;
    //2012/03.25
    // Create a file chooser
    private JFileChooser file_chooser;
    private JTextArea log;

    private org.jdesktop.swingx.JXMapKit jXMapKit1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
