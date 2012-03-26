package gglMpsSttcAccessor;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.util.URIUtil;
import org.xml.sax.InputSource;

public class WebRequest extends HttpClient {
	
	//protected HttpWebRequest m_Request=null;
   // protected HttpWebResponse m_Response=null;
    protected String m_ReceiveStream=null;
    protected InputSource m_Encode=null;
    protected InputStreamReader m_ReadStream=null;

    protected GetMethod get;// = new GetMethod(uri);
    protected void Reader(){}

    /*public bool webRequest(String strUI)
    {
        //"http://maps.google.com/maps/api/directions/xml?origin=43,holmbush crescent scarborough,ON&destination=52 petworth crescent scarborough,ON&waypoints=Charlestown,MA|Lexington,MA&sensor=false"
        m_strHttpUI = strUI;
        m_Request = (HttpWebRequest)WebRequest.Create(m_strHttpUI);
        m_Request.KeepAlive = false;
        m_Response = (HttpWebResponse)m_Request.GetResponse();

        m_ReceiveStream = m_Response.GetResponseStream();
        if (m_ReceiveStream == null) return false;
        return true;
    }*/
    
    public boolean webRequest(String strUI) throws HttpException, IOException{
    	
    	//get = new GetMethod(strUI);
    	get = new GetMethod("http://maps.google.com/maps/api/geocode/xml");
    	String strPara= "address="+strUI+"&sensor=false";
    	get.setQueryString(URIUtil.encodeQuery(strPara));

    	try {
    			int statusCode = executeMethod(get);
    			if(statusCode == HttpStatus.SC_OK){
    				/*byte[] respBuf = get.getResponseBody();
    				String readBuf = new String(respBuf);*/
    				m_ReceiveStream = get.getResponseBodyAsString();//.getResponseBodyAsStream();
    			}
    	}
    	finally {
    		get.releaseConnection();
    	}
    	return true;
    }

    /*public void Encoding() throws UnsupportedEncodingException //default to 'utf-8'
    {
        if (m_ReceiveStream == null) return;
        
        m_ReadStream = new InputStreamReader(m_ReceiveStream,"UTF-8");
        m_Encode = new InputSource(m_ReadStream);
        m_Encode.setEncoding("UTF-8");
    }
    
    public void Test()
    {
        m_Encode = System.Text.Encoding.GetEncoding("utf-8");
        // Pipes the stream to a higher level stream reader with the required encoding format. 
        StreamReader m_ReadStream = new StreamReader(m_ReceiveStream, m_Encode);

        while (!m_ReadStream.EndOfStream)
        {
            Debug.WriteLine(m_ReadStream.ReadLine());
        }

        m_ReadStream.Close();
    }*/
    public void QuitRequest()
    {
        if (get!=null)
        {
        	get.releaseConnection();
        }
        
    }
}
