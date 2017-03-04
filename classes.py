try:
  from urllib.request import urlopen
except ImportError:
  from urllib2 import urlopen
from bs4 import BeautifulSoup

def begin():
  url = "http://schedules.caltech.edu/SP2016-17.html"
  page = urlopen(url)
  soup = BeautifulSoup(page, 'html.parser')
  table = soup.find_all('table')

  code = None
  time = None
  first = True
  finalText = ""
  for mytable in table:
    if mytable.find('td', {"width" : "113"}) != code and \
       mytable.find('td', {"width" : "113"}) is not None and first:
      code = mytable.find('td', {"width" : "113"})
      finalText = code.text
      first = False

    elif mytable.find('td', {"width" : "113"}) != code and \
         mytable.find('td', {"width" : "113"}) is not None:
      print finalText
      print
      code = mytable.find('td', {"width" : "113"})
      finalText = code.text

    if mytable.find('td', {"width" : "135"}) is not None:
      time = mytable.find_all('td', {"width" : "135"})
      for t in time:
        finalText = finalText + " " + t.text

begin()
