__author__="Ryan Le"
__email__="r9le@ucsd.edu"
__credits__=["Ryan Le", "Martin Perez"]


try:
  from urllib.request import urlopen
except ImportError:
  from urllib2 import urlopen
from bs4 import BeautifulSoup

#Method to write the info form the schedule to a file
def begin():
  #loading html from url
  url = "http://schedules.caltech.edu/SP2016-17.html"
  page = urlopen(url)
  soup = BeautifulSoup(page, 'html.parser')

  #retrieving tables from html
  table = soup.find_all('table')

  #Variables to hold class codes and times 
  code = None
  time = None
  first = True
  codeText = ""
  timeText = ""

  #Boolean to check if times exist for course
  hasTime = True

  #file to write information
  f = open("catalog.txt", "a+")

  #Loop to run through the tables 
  for mytable in table:
    #Condition for the first code
    if mytable.find('td', {"width" : "113"}) != code and \
       mytable.find('td', {"width" : "113"}) is not None and first:
      code = mytable.find('td', {"width" : "113"})
      codeText = code.text
      first = False

    #Finds the course code if exists
    elif mytable.find('td', {"width" : "113", "rowspan" : 2}) \
         is not None:

      #Appends previous course info to file if has times
      if hasTime and codeText != "":
        f.write(codeText.encode('utf-8') + "\n")
        f.write(timeText.encode('utf-8') + "\n")

      #Sets new code from current table
      code = mytable.find('td', {"width" : "113", "rowspan" : "2"})
      timeText = ""
      hasTime = True
      codeText = code.text

    #Finds the course time if exists
    if mytable.find('td', {"width" : "135"}) is not None:
      time = mytable.find_all('td', {"width" : "135"})

      #Runs through all times found 
      for t in time:
        #Sets boolean false if no time given
        if 'A' in t.text:
          hasTime = False
        #Concatenates times to timeText string
        timeText = timeText + " " + t.text + " "

#calls begin function
begin()
