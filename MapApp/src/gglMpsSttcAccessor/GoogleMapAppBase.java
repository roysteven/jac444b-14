package gglMpsSttcAccessor;

import javax.xml.xpath.XPathException;

public class GoogleMapAppBase extends WebRequest {
	protected   String strUIprv = "http://maps.google.com/maps/api/";
    protected   String strUIprvOutputFMT0 = "xml?";//default
    protected   String strUIprvOutputFMT1 = "json?";
    protected   String strUISensor = "sensor=false";//not for moving devs;

    protected String m_strUI = null;
    protected String m_strUIprvOutput = null;

    //Directions parts
    protected   String constStrDirection = "directions/";
    protected   String constStrClosetWayInfo_duration = "/DirectionsResponse/route/leg/duration/text";
    protected   String constStrClosetWayInfo_distance = "/DirectionsResponse/route/leg/distance";       
    //params div by '&';
    protected   String strUIOrigin = "origin=";
    protected   String strUIDestination = "destination=";
    protected   String strUIWaypoints = "waypoints=";//div by '|';

    //Geocoding parts
    protected   String constStrGeocoding = "geocode/";
    protected   String strUIAddress = "address=";
    protected   String constStrGeometry = "result/geometry/location/";
    protected   String constStrGeometry_lat = "lat" ;
    protected   String constStrGeometry_lng = "lng";
    protected   AddressInfo m_AddressInfo;

    protected String m_strUIWaypoints = null;        

    protected XMLReader m_xmlReader = null;
    protected String m_strStatus = null;
    protected boolean m_bOkeyFlag = false;
    
    public boolean IsOkey() throws XPathException
    {
        m_xmlReader = new XMLReader(m_ReceiveStream);
        m_strStatus = m_xmlReader.GetStringValue("status");
        m_strStatus.toUpperCase();//.ToUpper();
        m_bOkeyFlag = false;
        
        if (m_strStatus.compareToIgnoreCase("OK")/*.CompareTo("OK")*/ == 0)
        {
            m_bOkeyFlag = true;
        }else{
            m_xmlReader = null;
        }
        return m_bOkeyFlag;
    }
    
    public void QuitGoogle()
    {
        m_strUI = null;
        m_bOkeyFlag = false;
        m_xmlReader = null;
        m_strStatus = null;
    }
    
}
