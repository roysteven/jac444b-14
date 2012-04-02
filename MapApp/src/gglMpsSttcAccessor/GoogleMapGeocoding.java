package gglMpsSttcAccessor;
/**
*Handle the communicating with google by geocoding mode.
*@author: Jian hui Chen
*@version: 1.0
*@since    1.0
*/
import java.io.IOException;

import javax.xml.xpath.XPathException;

import org.apache.commons.httpclient.HttpException;

public class GoogleMapGeocoding extends GoogleMapAppBase {
	/**
	 * Init query string.
	 */	
	public void InitRequestCommand()
    {
        if (m_strUIprvOutput == null) m_strUIprvOutput = strUIprvOutputFMT0;
        m_strUI = strUIprv + constStrGeocoding + m_strUIprvOutput + strUIAddress;
    }
	/**
	 * Query google the location information by geocoding.
	 * @param strAddress The location in detail
	 * @return always true
	 * @throws HttpException Error with the http query
	 * @throws IOException Error of io
	 */
    public boolean Geocoding(String strAddress) throws HttpException, IOException
    {
        if (m_strUI == null) return false;
        m_strUI += strAddress +"&"+ strUISensor;
        boolean result= webRequest(strAddress);
        m_AddressInfo = new AddressInfo();
        m_AddressInfo.strAddress = strAddress;
        return result;
    }
    /**
     * Read the response information
     * Store the resule into AddressInfo.
     * @return if the query answered by "ok" return true,otherwise false.
     * @throws XPathException Path search error.
     */
    public boolean GetAddressInfo() throws XPathException
    {
        if (m_bOkeyFlag)
        {
            String str = constStrGeometry + constStrGeometry_lat;
            
            m_AddressInfo._lat = m_xmlReader.GetDoubleValue(str);
            str = constStrGeometry + constStrGeometry_lng;
            m_AddressInfo._lng = m_xmlReader.GetDoubleValue(str);
        }
        return m_bOkeyFlag;
    }
    /**
     * get the lat
     * @return lat
     */
    public Double GetLat()
    {
        return m_AddressInfo._lat;
    }
    /**
     * get the lng
     * @return lng
     */
    public Double GetLng()
    {
        return m_AddressInfo._lng;
    }
    //mar 26 2012 modify by chen jian hui
    /**
     * get the address information in detail
     * @return address in detail
     */
    public String GetAddress()
    {
        return m_AddressInfo.strAddress;
    }

}
