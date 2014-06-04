'''
Created on Jun 3, 2014

@author: xad
'''
from BeautifulSoup import BeautifulSoup
import urllib2
import time

requestCounter = 0
impressionCounter = 0

headers = {'User-Agent':"Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16"}

for found in xrange(0,50):
    while True:
        time.sleep(30)
        request = urllib2.Request("http://m.accuweather.com/en/us/stanhope-nj/07874/weather-forecast/339545", None, headers)
        response = urllib2.urlopen(request)
        html = response.read()
        requestCounter+=1
        print "request no : ",requestCounter
        response.close()   
        
        if "div id='xad'" not in html:
            continue
        else:
            if "IMAGE_2d67fc0e-d1c3-4cb4-b25a-f56dd55ab33a.jpeg" not in html:
                print "xAd impression without the specific banner"
                continue
            else:
                break
        
        
    impressionCounter+=1;
    print "impression counter : ",impressionCounter
    
    date_string = time.strftime("%Y-%m-%d-%H:%M:%S")
    file_object = open("Output-Specific/Impression-Details-"+date_string+".txt",'w')
    
    soup = BeautifulSoup(html)
    div = soup.find("div", {"id": "xad"})
    img = div.findAll("img")
    impression = img[1].get("src")
    
    responseImpression = urllib2.urlopen(impression)
    impressionStatus = responseImpression.info().getheader("X-RestStatus")
    responseImpression.close()
    
    landingPageUrl = soup.find("div", {"id": "xad"}).find("a").get('href')
    temp,crid = landingPageUrl.split("crid=")
    successfulImp +=1
    
    file_object.write(str(html))
    file_object.write("\nImpression Status : "+impressionStatus)
    file_object.write("\nCrid : "+crid)
    file_object.write("\n")
    file_object.close()
    
    
        
        
        
        
        
        