package org.Rooney.apps.utils

import java.text.SimpleDateFormat;
import java.time.Instant

class Dates {
	static SimpleDateFormat getFormat(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	}

	static long getDate(String dateStr){
		def instant=getFormat().parse(dateStr).toInstant()
		return instant.toEpochMilli()
	}
	static String getDate(long dateLong){
		def instant=Instant.now()
		instant.ofEpochMilli(dateLong)
		return getFormat().format(Date.from(instant))
	}
}
