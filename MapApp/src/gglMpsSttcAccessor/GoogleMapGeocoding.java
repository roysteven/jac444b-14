package gglMpsSttcAccessor;

import java.io.IOException;

import javax.xml.xpath.XPathException;

import org.apache.commons.httpclient.HttpException;

public class GoogleMapGeocoding extends GoogleMapAppBase {
	
	public void InitRequestCommand()
    {
        if (m_strUIprvOutput == null) m_strUIprvOutput = strUIprvOutputFMT0;
        m_strUI = strUIprv + constStrGeocoding + m_strUIprvOutput + strUIAddress;
    }
    public boolean Geocoding(String strAddress) throws HttpException, IOException
    {
        if (m_strUI == null) return false;
        m_strUI += strAddress +"&"+ strUISensor;
        boolean result= webRequest(strAddress);
        m_AddressInfo = new AddressInfo();
        m_AddressInfo.strAddress = strAddress;
        return result;
    }
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
    public Double GetLat()
    {
        return m_AddressInfo._lat;
    }
    public Double GetLng()
    {
        return m_AddressInfo._lng;
    }
    //mar 26 2012 modify by chen jian hui
    public String GetAddress()
    {
        return m_AddressInfo.strAddress;
    }

}
