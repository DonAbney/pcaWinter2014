@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')
@Grab(group='oauth.signpost', module='signpost-core', version='1.2.1.2')
@Grab(group='oauth.signpost', module='signpost-commonshttp4', version='1.2.1.2')
 
import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*
 
def twitter = new RESTClient( 'https://api.twitter.com/1.1/statuses/' )
// twitter auth omitted
 
try { // expect an exception from a 404 response:
    twitter.head path : 'public_timeline'
    assert false, 'Expected exception'
}
// The exception is used for flow control but has access to the response as well:
catch( ex ) { assert ex.response.status == 404 }
 
assert twitter.head( path : 'home_timeline.json' ).status == 200





@Grab(group='com.github.groovy-wslite', module='groovy-wslite', version='0.8.0')
import wslite.rest.*

def client = new RESTClient("http://api.twitter.com/1/")
def response = client.get(path:'/users/show.json',
                          query:[screen_name:'gJigsaw', include_entities:true])
assert 200 == response.statusCode
assert "Jason Green" == response.json.name
