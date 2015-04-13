package org.search.groovy.service

import org.search.groovy.GroovyModel

def jsp='views/file/Files.jsp'
if(params.type=='edit'){
	def id=params.id
	if(id){
		def gm=new GroovyModel()
		gm.id=id
		gm.name='ming'+id
		gm.time=Long.valueOf(id.toString())
		request.setAttribute("gm", gm)
	}
	jsp='views/file/FileForm.jsp'
	
}else{
	def list=[]
			for(def i=0;i<10;i++){
				def gm=new GroovyModel()
				gm.id=i
				gm.name='ming'+i
				gm.time=Long.valueOf(i.toString())
				list[i]=gm
			}
	request.setAttribute("list", list)
}

forward jsp
