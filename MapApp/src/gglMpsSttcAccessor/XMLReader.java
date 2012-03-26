package gglMpsSttcAccessor;

//import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class XMLReader {
	private String m_inputStream = null;
    //private XPath m_xpath = null;
    private InputSource m_inputSource=null;
    private SAXBuilder m_sb = null;
    private Document m_doc = null;
    Element m_root = null;
    Element   m_readroot = null;
    StringReader m_reader = null;

    public XMLReader(String xmlStream)
    {
    	//m_xpath = XPathFactory.newInstance().newXPath();
    	m_inputStream = xmlStream;
    	m_reader = new StringReader(m_inputStream);
    	m_inputSource = new InputSource(m_reader);
    	m_sb = new SAXBuilder();
    	try {
			m_doc = m_sb.build(m_inputSource);
			m_root = m_doc.getRootElement();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void SetReadRoot(String sroot,int index){
    	if(!sroot.isEmpty()){
    		if(index==0) m_readroot =m_root;
    		int nlen=sroot.length();
    		String subRoot=null;
    		if(nlen>0){
    			int i;
    			
    			boolean bFound = false;
    			for(i=0;i<nlen;i++)
    				if(sroot.charAt(i)=='/'){
    					subRoot = sroot.substring(0, i);
    					if(subRoot.length()==0){
    						i=-1;
    						sroot = sroot.substring(1);
    						continue;
    					}
    					sroot = sroot.substring(i+1);
    					bFound = true;
    					break;
    				}
    			if(!bFound){
    				subRoot = sroot;
    				sroot=sroot.substring(nlen);
    			}
    			m_readroot =m_readroot.getChild(subRoot);
    			SetReadRoot(sroot,++index);
    		}
    	}
    }
    public String GetStringValue(String XPathString) throws XPathException
    {
    	SetReadRoot(XPathString,0);
        //=m_readroot.getChild( XPathString);        
        return m_readroot.getValue();

    }
    public int GetIntegerValue(String XPathString) throws XPathException
    {
        return Integer.parseInt(GetStringValue(XPathString));

    }
    public Double GetDoubleValue(String XPathString) throws XPathException
    {
    	return Double.parseDouble(GetStringValue(XPathString));

    }

}
