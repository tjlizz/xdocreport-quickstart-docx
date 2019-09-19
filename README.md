# XDocReport Tools
     链接: https://pan.baidu.com/s/1ot-dQ_e1cM-CT5IGiZHajQ 提取码: 9f9i 
     
解压后会看到三个文件夹
* bin
* lib  
* macro 
# Java Project quickstart
## Create xdocreport-quickstart-docx
把`lib`文件夹中的`jar`粘贴到项目中，并添加引用。
## Model
```
public class Project {
    private final String name;

    public Project(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```
```

public class Developer {
    private final String name;
    private final String lastName;
    private final String mail;
    private final IImageProvider logo;

    public Developer(String name, String lastName, String mail, IImageProvider logo) {
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public IImageProvider getLogo() {
        return logo;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }
}

```
# Generate XML fields
```
public class GenerateXMLFields {
    public static void main(String[] args) throws XDocReportException, IOException {

        FieldsMetadata fieldsMetadata = new FieldsMetadata(TemplateEngineKind.Velocity.name());

        fieldsMetadata.load("project", Project.class);

        fieldsMetadata.load("developers", Developer.class, true);

        File xmlFieldsFile = new File("project.fields.xml");

        fieldsMetadata.saveXML(new FileOutputStream(xmlFieldsFile), true);

    }
}

```
执行之后会得到`project.fields.xml`文件，内容如下
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<fields templateEngineKind="Velocity" >
	<description><![CDATA[]]></description>
	<field name="project.Name" list="false" imageName="" syntaxKind="">
		<description><![CDATA[]]></description>
	</field>
	<field name="developers.LastName" list="true" imageName="" syntaxKind="">
		<description><![CDATA[]]></description>
	</field>
	<field name="developers.Logo" list="true" imageName="developers.Logo" syntaxKind="">
		<description><![CDATA[]]></description>
	</field>
	<field name="developers.Mail" list="true" imageName="" syntaxKind="">
		<description><![CDATA[]]></description>
	</field>
	<field name="developers.Name" list="true" imageName="" syntaxKind="">
		<description><![CDATA[]]></description>
	</field>
</fields>
```
# XDocReport.dotm
 运行`macro`文件夹中的`XDocReport.dotm`
 ![](https://user-gold-cdn.xitu.io/2019/9/19/16d477c903e50daa?w=1325&h=170&f=png&s=26614)
 会看到顶部工具栏中多了`XDocReport`选项，现在使用这个来设置`word`模板
 
![](https://user-gold-cdn.xitu.io/2019/9/19/16d477ed9fa79aec?w=1329&h=607&f=png&s=69040)
如上图在`XML Fields`中选择`project.fields.xml`所在的目录

![](https://user-gold-cdn.xitu.io/2019/9/19/16d4786e1dcc0012?w=1114&h=699&f=pn)
如上图在指定位置插入对应的属性

![](https://user-gold-cdn.xitu.io/2019/9/19/16d47888c6558453?w=745&h=545&f=png&s=26119)

此时已经完成了一个简单的模板配置，保存到项目目录中（`project.docx`）。
# GenerateDocxReport 
```
public class GenerateDocxReport  {
    public static void main(String[] args) throws IOException, XDocReportException {

        InputStream in = GenerateDocxReport.class.getResourceAsStream("project.docx");

        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Velocity);

        FieldsMetadata fieldsMetadata = report.createFieldsMetadata();

        fieldsMetadata.load("project", Project.class);

        IContext context = report.createContext();

        Project project = new Project("ProjectName");

        context.put("project", project);

        OutputStream out = new FileOutputStream(new File("project_out.docx"));

        report.process(context, out);

    }
}

```
 运行后可在项目目录中看到`project_out.docx`，没有错误的话会看到如下图
 
![](https://user-gold-cdn.xitu.io/2019/9/19/16d4794ca8055dc5?w=547&h=221&f=png&s=4726)

# Table
 如果想要生成`Table`,我们只需在`word`模板中插入一个表格，在单元格中标记对应的字段即可，如下图
 
![](https://user-gold-cdn.xitu.io/2019/9/19/16d47a01bb5e8fbe?w=1127&h=539&f=png&s=29563)
设置完成之后保存模板，下面来编写代码
```
public class GenerateDocxReport  {
    public static void main(String[] args) throws IOException, XDocReportException {

        InputStream in = GenerateDocxReport.class.getResourceAsStream("project.docx");

        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Velocity);

        fieldsMetadata.load("project", Project.class);

        IImageProvider logo = new ClassPathImageProvider(GenerateDocxReport.class, "img/logo.png"); 

        fieldsMetadata.load("developers", Developer.class, true);

        fieldsMetadata.addFieldAsList("developers.Name");

        fieldsMetadata.addFieldAsList("developers.LastName");

        fieldsMetadata.addFieldAsList("developers.Mail");

        IContext context = report.createContext();

        Project project = new Project("ProjectName");

        context.put("project", project); 

       List<Developer> developers = new ArrayList<Developer>();

       developers .add(new Developer("ZERR", "Angelo", "angelo.zerr@gmail.com", logo));

        developers.add(new Developer("Leclercq", "Pascal","pascal.leclercq@gmail.com", logo));

        developers.add(new Developer("Leclercq", "Pascal", "pascal.leclercq@gmail.com", logo)); 

        context.put("developers", developers);

        OutputStream out = new FileOutputStream(new File( "project_out.docx"));

        report.process(context, out);
    }

}

```
       fieldsMetadata.addFieldAsList('')//标记该字段是在列表中展示

运行结果如下

![](https://user-gold-cdn.xitu.io/2019/9/19/16d47a8097582dc4?w=921&h=219&f=png&s=14104)
 
 # Image
 >图片需要在模板中插入图片模板,并且配合书签一起使用，可插入任何图片做模板。
 
## WordTemplate
![](https://user-gold-cdn.xitu.io/2019/9/19/16d48060b24e53c6?w=1440&h=748&f=png&s=152869)
```
在模板中插入图片，并且选中图片在图片上插入书签，和实体类的属性保持一致，一定要在图片上插入书签。
```
 ## Code
 ```
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

        OutputStream out = new FileOutputStream(new File("project_out.docx"));
        report.process(context, out);
    }}

 ```
   > 列表中插入图片也可按照这种方式，上面代码也可实现，`developers`对象中也有图片显示。
   # Insert List
   
![](https://user-gold-cdn.xitu.io/2019/9/19/16d482d1bdfd78e3?w=1389&h=715&f=png&s=99782)
按照上述操作之后显示如下

![](https://user-gold-cdn.xitu.io/2019/9/19/16d482e4b6d4c062?w=431&h=158&f=png&s=4517)

保持模板之后

![](https://user-gold-cdn.xitu.io/2019/9/19/16d482e4b6d4c062?w=431&h=158&f=png&s=4517)

再次执行代码

![](https://user-gold-cdn.xitu.io/2019/9/19/16d483035f2042bb?w=889&h=415&f=png&s=48229)

# 其他
   [源码链接](https://github.com/opensagres/xdocreport)
   
   [实例代码](https://github.com/lizeze/xdocreport-quickstart-docx)
