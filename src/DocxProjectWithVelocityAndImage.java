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

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DocxProjectWithVelocityAndImage {

    public static void main(String[] args) throws IOException, XDocReportException {
        InputStream in = DocxProjectWithVelocityAndImage.class
                .getResourceAsStream("img.docx");
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
                in, TemplateEngineKind.Velocity);

        // 2) Create fields metadata to manage image
        FieldsMetadata metadata = new FieldsMetadata();
        metadata.addFieldAsImage("logo");
        report.setFieldsMetadata(metadata);
        metadata.load("developers", Developer.class, true);
        metadata.addFieldAsList("developers.Name");
        metadata.addFieldAsList("developers.LastName");
        metadata.addFieldAsList("developers.Mail");
        metadata.addFieldAsList("developers.logo");

        // 3) Create context Java model
        IContext context = report.createContext();
        Project project = new Project("XDocReport");
        context.put("project", project);
        File img=new File("C:\\Users\\admin\\Desktop\\echart-demo\\a.png");
        IImageProvider logo = new FileImageProvider(
             img,false);
        context.put("logo", logo);
        List<Developer> developers = new ArrayList<Developer>();
        developers
                .add(new Developer("ZERR", "Angelo", "angelo.zerr@gmail.com", logo));
        developers.add(new Developer("Leclercq", "Pascal",
                "pascal.leclercq@gmail.com", logo));
        developers.add(new Developer("Leclercq123", "Pascal",
                "pascal.leclercq@gmail.com", logo));
        developers.add(new Developer("Leclercq435", "Pascal",
                "pascal.leclercq@gmail.com", logo));
        context.put("developers", developers);
        // 4) Generate report by merging Java model with the Docx
        OutputStream out = new FileOutputStream(new File(
                System.currentTimeMillis()+"DocxProjectWithVelocityAndImage_Out.docx"));

        report.process(context, out);
    }}