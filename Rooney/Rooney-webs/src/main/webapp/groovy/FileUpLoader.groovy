

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory
import org.apache.commons.fileupload.servlet.ServletFileUpload
import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.util.StringUtil;
import org.waps.groovy.service.WapsDocService;
import org.waps.groovy.service.WapsDocServiceImpl;
import org.waps.groovy.util.Dates;
import org.waps.groovy.util.GroovyPop;
import org.bson.Document
import org.bson.types.ObjectId;

def prop=GroovyPop.INSTANCE.get()
def fileBaseDir=prop.getProperty('filePath')
def contextPath=context.getRealPath('/')
def baseDir=contextPath+fileBaseDir //文件存放文件夹
def tmpDir=contextPath+prop.getProperty('groovyTmp')//缓存文件夹
File folder=new File(baseDir)
if(!folder.exists()){
	folder.mkdirs()
}
File tmp=new File(tmpDir)
if(!tmp.exists()){
	tmp.mkdirs()
}

DiskFileItemFactory diskFactory = new DiskFileItemFactory()
diskFactory.sizeThreshold=Integer.valueOf(prop.getProperty('sizeThreshold','4096'))
diskFactory.repository=new File(tmpDir)
ServletFileUpload upload = new ServletFileUpload(diskFactory)
upload.sizeMax=Integer.valueOf(prop.getProperty('sizeMax','4194304'))
def fileItems = upload.parseRequest(request)
Document doc=new Document()
fileItems.each {item->
	if(item.isFormField()){
		if(item.fieldName=='finalUpdateTime'){
			doc.append(item.fieldName,Dates.parse(item.string, Dates.WAPS_FORMAT))
		}else if(item.fieldName=='id'){
			String id=item.string
			if(id){
				doc.append('_id',new ObjectId(id))
			}
		}
		else{
			doc.append(item.fieldName,item.getString("UTF-8"))
		}
	}else{
		if(StringUtils.isNotBlank(item.name)){
			File uploadedFile = new File(baseDir,item.name)
			//没有对文件名处理，需要完善
			doc.append('path',item.name)
			item.write(uploadedFile);
		}
	}
}

WapsDocService service=new WapsDocServiceImpl()
service.saveOne(doc)

forward 'WapsDocController.groovy?method=queryAll'
