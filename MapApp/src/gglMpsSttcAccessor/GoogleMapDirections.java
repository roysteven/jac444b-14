package gglMpsSttcAccessor;
/**
*to construct the communicating(query) command in prior send command.
*@author: Jian hui Chen
*@version: 1.0
*@since    1.0
*/
import java.io.IOException;

//import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.methods.GetMethod;

//import Task.Support.CoreSupport.ByteBuffer;

public class GoogleMapDirections extends GoogleMapAppBase {
	/**
	 * prepare the query string for xml format only
	 */
	public  void InitRequestCommand()
    {
        if (m_strUIprvOutput==null) m_strUIprvOutput=strUIprvOutputFMT0;
        m_strUI = strUIprv + constStrDirection + m_strUIprvOutput;
    }
	/**
	 * It is used to add second location to query string
	 * in case you need to get the direction in two locations.
	 * @param strNewPoint The second address
	 */
    public void AppendWaypoint(String strNewPoint)
    {            
        if (m_strUIWaypoints == null) m_strUIWaypoints = strNewPoint;
        m_strUIWaypoints = m_strUIWaypoints + "|" + strNewPoint;

    }
    /**
     * Get direction from strOrigin to strDest
     * success means only it responses something, 
     * function IsOkey() should be call to make sure the response is OK in the status.
     * @param strOrigin The address of start point
     * @param strDest The address of end point
     * @return always true
     * @throws HttpException Error of http communication
     * @throws IOException Error of io in http communication
     */
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
