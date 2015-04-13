
import org.waps.groovy.entities.WapsDoc;
import org.waps.groovy.service.WapsDocService;
import org.waps.groovy.service.WapsDocServiceImpl




void queryAll(){
	WapsDocService service=new WapsDocServiceImpl()
	request.setAttribute("list", service.findAll())
	forward 'views/doc/WapsDoc.jsp'
}

void query(){
	def docName=params.docName
	def sd=params.sd
	def ed=params.ed
	
	request.setAttribute("list", service.find())
	forward 'views/doc/WapsDoc.jsp'
}


void edit(){
	String id=params.id
	if(id){
		WapsDocService service=new WapsDocServiceImpl()
		request.setAttribute('doc',service.findOnebyId(id))
	}
	forward 'views/doc/WapsDocForm.jsp'
}


this."${params.method}"()


