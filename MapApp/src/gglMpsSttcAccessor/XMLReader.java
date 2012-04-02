package gglMpsSttcAccessor;
/**
*read data of xml format(jac444 assignment2).
*@author: Jian hui Chen
*@version: 1.0
*@since    1.0
*/
//import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.xpath.XPathException;

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
    /**
     * Create a XMLReader for xml format stream
     * @param xmlStream The source string need to be read
     */
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
    /**
     * Move direct to the last sub root
     * @param sroot The target root
     * @param index How depth going
     */    
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
    /**
     * Get value of string
     * @param XPathString The related path string,like "abs path of computer system."
     * @return value in that path
     * @throws XPathException: Exception of wrong path.
     */    
    public String GetStringValue(String XPathString) throws XPathException
    {
    	SetReadRoot(XPathString,0);
        //=m_readroot.getChild( XPathString);        
        return m_readroot.getValue();

    }
    /**
     * Get value of integer
     * @param XPathString The related path string,like "abs path of computer system."
     * @return value in that path
     * @throws XPathException: Exception of wrong path.
     */    
    public int GetIntegerValue(String XPathString) throws XPathException
    {
        return Integer.parseInt(GetStringValue(XPathString));

    }
    /**
     * Get value of double
     * @param XPathString The related path string,like "abs path of computer system."
     * @return value in that path
     * @throws XPathException: Exception of wrong path.
     */    
    public Double GetDoubleValue(String XPathString) throws XPathException
    {
    	return Double.parseDouble(GetStringValue(XPathString));

    }

}
