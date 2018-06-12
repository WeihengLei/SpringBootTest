package com.example.test.util;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.xfa.XfaForm;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.StampingProperties;

/*import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.XfaForm;*/



public class Itext7Util {


    public static final String RESOURCE_PDF = "/Users/weiheng.lei/Downloads/AOForm.pdf";
    public static final String RESOURCE_DATA = "/Users/weiheng.lei/Downloads/AOForm_data2.xml";
    public static final String RESULT = "/Users/weiheng.lei/Downloads/AOForm2.pdf";


    public static final String SRC = "/Users/weiheng.lei/Downloads/AOForm.pdf";
    public static final String XML = "/Users/weiheng.lei/Downloads/AOForm_generated.xml";
    public static final String DEST = "/Users/weiheng.lei/Downloads/AOForm_generated2.pdf";

    public static final String SRC2 = "/Users/weiheng.lei/Downloads/AOForm3.pdf";
    //public static final String DEST2 = "/Users/weiheng.lei/Downloads/AOForm4.xml";

//	public static final String SRC2 = "/Users/weiheng.lei/Downloads/AOForm_generated.pdf";
//	public static final String DEST2 = "/Users/weiheng.lei/Downloads/AOForm_generated.xml";

    //@Ignore
    public void saveFileByService() throws Exception {

        System.out.println("Done");

        //File file2 = new File(DEST2);
        //file2.getParentFile().mkdirs();

        PdfDocument pdfDoc2 = new PdfDocument(new PdfReader(SRC2));

        PdfAcroForm form2 = PdfAcroForm.getAcroForm(pdfDoc2, true);
        XfaForm xfa2 = form2.getXfaForm();

        Node node = xfa2.getDatasetsNode();
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if("data".equals(list.item(i).getLocalName())) {
                node = list.item(i);
                break;
            }
        }
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");

        //FileOutputStream os = new FileOutputStream(DEST2);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        tf.transform(new DOMSource(node), new StreamResult(os));
        pdfDoc2.close();


        File file = new File(DEST);
        file.getParentFile().mkdirs();

        PdfReader reader = new PdfReader(SRC);
        reader.setUnethicalReading(true);

        ByteArrayOutputStream os2 = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(DEST),new StampingProperties().useAppendMode());

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        XfaForm xfa = form.getXfaForm();

        //File file3 = new File(XML);
        //InputStream input = new FileInputStream(file3);
        InputStream i =new ByteArrayInputStream(os.toByteArray());
        xfa.fillXfaForm(i);
        //xfa.fillXfaForm(new FileInputStream(XML));
        xfa.write(pdfDoc);
        pdfDoc.close();


		/*PdfReader reader = new PdfReader(SRC);
		PdfStamper stamper = new PdfStamper(reader,
				new FileOutputStream(DEST), (char) 0,true);
		AcroFields form = stamper.getAcroFields();
		XfaForm xfa = form.getXfa();
		xfa.fillXfaForm(new FileInputStream(XML));
		stamper.close();
		reader.close();


		File file2 = new File(DEST2);
		file2.getParentFile().mkdirs();
		File sss = new File(SRC2);
		InputStream input = new FileInputStream(sss);

		PdfReader reader2 = new PdfReader(input);
		AcroFields form = reader2.getAcroFields();
		XfaForm xfa = form.getXfa();
		Node node = xfa.getDatasetsNode();
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if("data".equals(list.item(i).getLocalName())) {
				node = list.item(i);
				break;
			}
		}
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");*/

        //ByteArrayOutputStream os = new ByteArrayOutputStream();
        //FileOutputStream d = new FileOutputStream(DEST2);

        //tf.transform(new DOMSource(node), new StreamResult(os));



    }
}