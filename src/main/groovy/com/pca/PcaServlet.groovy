package com.pca

import java.io.StringWriter;

import groovy.servlet.GroovyServlet
import groovy.xml.MarkupBuilder
	
import javax.servlet.ServletConfig
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class PcaServlet extends GroovyServlet {

	void service(HttpServletRequest request, HttpServletResponse response) {
		StringWriter writer = new StringWriter()
		
		def build = new MarkupBuilder(writer);
		build.html{
			head{
				link(rel:'stylesheet', href:'pca.css')
			}
			body{
				h1 ("Twitter Fu Client")
				10.times { tweetNum ->
					build.div {
						p ("This is sample tweet $tweetNum .....from user number $tweetNum")
					}
				}
			}
		}
		response.getWriter().write(writer.toString())
	}
}
