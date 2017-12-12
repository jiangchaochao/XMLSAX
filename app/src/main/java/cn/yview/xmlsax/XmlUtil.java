package cn.yview.xmlsax;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/12/12.
 */

public class XmlUtil {
    private static String TAG = "XmlUtil";
    public static void setValueToSp(File file, SharedPreferences sp) throws Exception {
        if (file == null || !file.exists())
            return;
        if (sp == null)
            return;
        final HashMap<String, Object> hashMap = new HashMap<>();
        final ArrayDeque<String> deque = new ArrayDeque();
        Xml.parse(new FileInputStream(file), Xml.Encoding.UTF_8, new ContentHandler() {
            Pattern mPattern = Pattern.compile("[1-9]{1,1000}");
            int index_key_string;

            @Override
            public void setDocumentLocator(Locator locator) {

            }

            @Override
            public void startDocument() throws SAXException {

            }

            @Override
            public void endDocument() throws SAXException {

            }

            @Override
            public void startPrefixMapping(String prefix, String uri) throws SAXException {

            }

            @Override
            public void endPrefixMapping(String prefix) throws SAXException {

            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
                //localname  qname <string></string> <boolean/>
                if (atts == null || atts.getLength() == 0)
                    return;
                switch (localName.trim()) {
                    case "string":
                        deque.offer(atts.getValue(0));
                        break;
                }
            }


            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {

            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                String s = new String(ch, start, length).trim();
                if (TextUtils.isEmpty(s))
                    return;
                hashMap.put(deque.peekLast(), s);
            }

            @Override
            public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

            }

            @Override
            public void processingInstruction(String target, String data) throws SAXException {

            }

            @Override
            public void skippedEntity(String name) throws SAXException {

            }
        });
        sp.edit().clear().commit();
        for (Map.Entry<String, Object> stringObjectEntry : hashMap.entrySet()) {
            String key = stringObjectEntry.getKey();
            Object object = stringObjectEntry.getValue();
            sp.edit().putString(key, String.valueOf(object)).commit();
        }
    }
}
