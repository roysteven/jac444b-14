package gglMpsSttcAccessor;

import java.io.IOException;

//import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.methods.GetMethod;

//import Task.Support.CoreSupport.ByteBuffer;

public class GoogleMapDirections extends GoogleMapAppBase {

	public  void InitRequestCommand()
    {
        if (m_strUIprvOutput==null) m_strUIprvOutput=strUIprvOutputFMT0;
        m_strUI = strUIprv + constStrDirection + m_strUIprvOutput;
    }
    public void AppendWaypoint(String strNewPoint)
    {            
        if (m_strUIWaypoints == null) m_strUIWaypoints = strNewPoint;
        m_strUIWaypoints = m_strUIWaypoints + "|" + strNewPoint;

    }
    //success means only it responses something, call function IsOkey() to make sure the response is OK in the status.
    public boolean MapDirections(String strOrigin, String strDest) throws HttpException, IOException
    {
        if (m_strUI == null) return false;
        m_strUI += strUIOrigin + strOrigin +
            "&" + strUIDestination  + strDest+ "&";
        if (m_strUIWaypoints != null)
            m_strUI = m_strUI + strUIWaypoints + m_strUIWaypoints + "&";
        m_strUI = m_strUI + strUISensor;

        return  webRequest(m_strUI);
    }
   
    /*public String GetClosetWayInfo()
    {
        if (m_bOkeyFlag)
            return m_xmlReader.GetStringValue(constStrClosetWayInfo_duration) +
                "   " + m_xmlReader.GetStringValue(constStrClosetWayInfo_distance);
        return null;
    }*/
   
}
