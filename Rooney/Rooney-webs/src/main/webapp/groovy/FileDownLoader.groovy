
import org.waps.groovy.entities.WapsDoc;
import org.waps.groovy.service.WapsDocService;
import org.waps.groovy.service.WapsDocServiceImpl;
import org.waps.groovy.util.GroovyPop;

WapsDocService service=new WapsDocServiceImpl()
WapsDoc wd=service.findOnebyId(params.id)
def fileName=wd.path
response.contentType='application/octet-stream'
response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.bytes,'ISO-8859-1'))
def fileBaseDir=GroovyPop.INSTANCE.get().getProperty('filePath')
def baseDir=context.getRealPath('/')+fileBaseDir
def out=new File(baseDir,wd.path).newInputStream()
sout<<out
out?.close()
sout?.close()
