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
  codeText = ""
  timeText = ""
  hasTime = True

  f = open("catalog.txt", "a+")

  for mytable in table:
    if mytable.find('td', {"width" : "113"}) != code and \
       mytable.find('td', {"width" : "113"}) is not None and first:
      code = mytable.find('td', {"width" : "113"})
      codeText = code.text
      first = False

    elif mytable.find('td', {"width" : "113", "rowspan" : 2}) \
         is not None:

      if hasTime and codeText != "":
        f.write(codeText.encode('utf-8') + "\n")
        f.write(timeText.encode('utf-8') + "\n")

      code = mytable.find('td', {"width" : "113", "rowspan" : "2"})
      timeText = ""
      hasTime = True
      codeText = code.text

    if mytable.find('td', {"width" : "135"}) is not None:
      time = mytable.find_all('td', {"width" : "135"})

      for t in time:
        if 'A' in t.text:
          hasTime = False
        timeText = timeText + " " + t.text + " "

begin()
