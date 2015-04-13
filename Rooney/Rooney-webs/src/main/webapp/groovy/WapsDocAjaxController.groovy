package org.waps.groovy

import org.bson.Document
import org.waps.groovy.service.WapsDocService
import org.waps.groovy.service.WapsDocServiceImpl
import org.bson.types.ObjectId;

def delete(){
	WapsDocService service=new WapsDocServiceImpl()
	Document doc=new Document()
	def id=params.id
	doc.append('_id',new ObjectId(id))
	println service.delete(doc)
}

response.setContentType("application/json;charset=UTF-8")
this."${params.method}"()