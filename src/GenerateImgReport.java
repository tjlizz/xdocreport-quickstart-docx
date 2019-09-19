import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.ClassPathImageProvider;
import fr.opensagres.xdocreport.document.images.FileImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import model.Developer;
import model.Project;
import model.User;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenerateImgReport {
    public static void main(String[] args) throws IOException, XDocReportException {

        InputStream in = DocxProjectWithVelocityAndImage.class.getResourceAsStream("project.docx");

        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

        FieldsMetadata metadata = new FieldsMetadata();

        metadata.addFieldAsImage("avatar");//注册图片所在的书签

        report.setFieldsMetadata(metadata);

        metadata.load("developers", Developer.class, true);

        metadata.addFieldAsList("developers.Name");

        metadata.addFieldAsList("developers.LastName");

        metadata.addFieldAsList("developers.Mail");

        metadata.addFieldAsList("developers.logo");
        IContext context = report.createContext();

        Project project = new Project("XDocReport");

        context.put("project", project);

        File img=new File("C:\\Users\\admin\\Desktop\\echart-demo\\a.png");

        IImageProvider avatar = new FileImageProvider( img);

        context.put("avatar", avatar); //图片

        List<Developer> developers = new ArrayList<Developer>();

        developers .add(new Developer("ZERR", "Angelo", "angelo.zerr@gmail.com", avatar));

        developers.add(new Developer("Leclercq", "Pascal","pascal.leclercq@gmail.com", avatar));

        developers.add(new Developer("Leclercq123", "Pascal","pascal.leclercq@gmail.com", avatar));

        developers.add(new Developer("Leclercq435", "Pascal","pascal.leclercq@gmail.com", avatar));

        context.put("developers", developers);

        OutputStream out = new FileOutputStream(new File("C:\\Users\\admin\\Desktop\\echart-demo\\"+System.currentTimeMillis()+"project_out.docx"));
        report.process(context, out);
    }}
