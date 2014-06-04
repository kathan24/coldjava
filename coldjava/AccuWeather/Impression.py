'''
Created on May 31, 2014

@author: xad
'''

from BeautifulSoup import BeautifulSoup
import urllib2
import time

impressionCounter = 0
successfulImp = 0
requestCounter = 0

#date_string = time.strftime("%Y-%m-%d-%H:%M")
#file_object = open("details-"+date_string+".txt",'w')

headers = {'User-Agent':"Mozilla/5.0 (Linux; Android 4.4.2; LGMS323 Build/KOT49I.MS32310b) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.138 Mobile Safari/537.36"}
#headers = {'User-Agent':"Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16"}
#headers = {'User-Agent':"Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3"}


for impressionCounter in xrange(0,100):
    while True:
        time.sleep(30)
        #request = urllib2.Request("http://m.accuweather.com/en/us/wood-ridge-nj/07075/weather-forecast/349525", None, headers)
        #request = urllib2.Request("http://m.accuweather.com/en/us/asbury-park-nj/07712/weather-forecast/334504", None, headers)
        request = urllib2.Request("http://m.accuweather.com/en/us/stanhope-nj/07874/weather-forecast/339545", None, headers)
        response = urllib2.urlopen(request)
        html = response.read()
        requestCounter+=1
        print "request no : ",requestCounter
        response.close()    
    
        if 'div id="xad"' not in html:
            continue 
        else:
            break

    impressionCounter+=1;
    print "impression counter : ",impressionCounter
    
    date_string = time.strftime("%Y-%m-%d-%H:%M:%S")
    file_object = open("Output/Impression-Details-"+date_string+".txt",'w')
    
    soup = BeautifulSoup(html)
    div = soup.find("div", {"id": "xad"})
    img = div.findAll("img")
    
    
    if img == None:
        file_object.write("img tag not found\n")
        file_object.write(str(html))
        continue

    if len(img)==1:
        impression = img[0].get("src")
    elif len(img)==2:
        impression = img[1].get("src")
               
    if impression:
        responseImpression = urllib2.urlopen(impression)
        impressionStatus = responseImpression.info().getheader("X-RestStatus")
        responseImpression.close()
        if impressionStatus == "NOK":
            landingPageUrl = soup.find("div", {"id": "xad"}).find("a").get('href')
            temp,crid = landingPageUrl.split("crid=")
            successfulImp +=1

    else:
        file_object.write("impression not found\n")
        file_object.write(str(html))
        continue
    
    file_object.write(str(html))
    file_object.write("\nImpression Status : "+impressionStatus)
    file_object.write("\nCrid : "+crid)
    file_object.write("\n")
    file_object.close()
        