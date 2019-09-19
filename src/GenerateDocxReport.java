import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.ClassPathImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import model.Developer;
import model.Project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class GenerateDocxReport  {
    public static void main(String[] args) throws IOException, XDocReportException {
        InputStream in = GenerateDocxReport.class
                .getResourceAsStream("project.docx");
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,
                TemplateEngineKind.Velocity);

        // 2) Create fields metadata to manage lazy loop (#forech velocity)
        // for table row.
        // 1) Create FieldsMetadata by setting Velocity as template engine
        FieldsMetadata fieldsMetadata = report.createFieldsMetadata();
        // 2) Load fields metadata from Java Class
        fieldsMetadata.load("project", Project.class);
        IImageProvider logo = new ClassPathImageProvider(
                GenerateDocxReport.class, "img/logo.png");

         // Here load is called with true because model is a list of Developer.
        fieldsMetadata.load("developers", Developer.class, true);
        fieldsMetadata.addFieldAsList("developers.Name");
        fieldsMetadata.addFieldAsList("developers.LastName");
        fieldsMetadata.addFieldAsList("developers.Mail");

        // 3) Create context Java model
        IContext context = report.createContext();
        Project project = new Project("ProjectName");
        context.put("project", project);
        context.put("logo", logo);
        // Register developers list
       List<Developer> developers = new ArrayList<Developer>();
       developers
                 .add(new Developer("ZERR", "Angelo", "angelo.zerr@gmail.com", logo));
        developers.add(new Developer("Leclercq", "Pascal",
               "pascal.leclercq@gmail.com", logo));
        developers.add(new Developer("Leclercq", "Pascal",
                "pascal.leclercq@gmail.com", logo));

        context.put("developers", developers);

        // 4) Generate report by merging Java model with the Docx
        OutputStream out = new FileOutputStream(new File( "project_out.docx"));
        report.process(context, out);
    }

}
