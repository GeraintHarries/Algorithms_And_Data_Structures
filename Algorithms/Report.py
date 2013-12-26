import time
import csv

li = []

def random(i):
    global li
    li = []
    import random
    for increase in range(0,i):
        li.append(random.randint(1,1000000000000))

def sort(i):
    global li
    li = []
    for increase in range (0, i):
        li.append(i)

def backwards(i):
    global li
    li = []
    for decrease in range(0, i, -1):
        li.append(i)
    
def BubbleSort():
#http://www.codecodex.com/wiki/Bubble_sort#Python
    global li
    lst = list(li)
    for passesLeft in range(len(lst)-1, 0, -1):
        for i in range(passesLeft):
            if lst[i] < lst[i + 1]:
                lst[i], lst[i + 1] = lst[i + 1], lst[i]
    return lst

##http://en.literateprograms.org/Quicksort_(Python)

def partition(list, l, e, g):
    while list != []:
        head = list.pop(0)
        if head < e[0]:
            l = [head] + l
        elif head > e[0]:
            g = [head] + g
        else:
            e = [head] + e
    return (l, e, g)

def qsort(list):
    
    if list == []: 
        return []
    else:
        pivot = list[len(list) -1]
        lesser, equal, greater = partition(list[1:], [], [pivot], [])
        return qsort(lesser) + equal + qsort(greater)
def RunBestQuick(i):
    f = open('PyOuputQuiBest.csv', 'wt')
    theTime = 0
    try:
        writer = csv.writer(f)
        writer.writerow( ('n', 'Time') )
        for increase in range (0, i, 10):
            for j in range(0, 1000):
                sort(increase)
                start = time.time()
                quick = qsort(li)
                theTime += time.time() - start
            content = str(increase) + "|" +  str(theTime/1000)
            print "%s" % (content)
            writer.writerow(content.split("|"))
    finally:
        f.close()
    
def RunAvQuick(i):
    f = open('PyOuputQuiAv.csv', 'wt')
    theTime = 0
    try:
        writer = csv.writer(f)
        writer.writerow( ('n', 'Time') )
        for increase in range (0, i, 10):
            for j in range(0, 1000):
                random(increase)
                start = time.time()
                quick = qsort(li)
                theTime += time.time() - start
            content = str(increase) + "|" +  str(theTime/1000)
            print "%s" % (content)
            writer.writerow(content.split("|"))
    finally:
        f.close()

def RunWorstQuick(i):
    f = open('PyOuputQuiWors.csv', 'wt')
    theTime = 0
    try:
        writer = csv.writer(f)
        writer.writerow( ('n', 'Time') )
        for increase in range (0, i, 10):
            for j in range(0, 1000):
                backwards(increase)
                start = time.time()
                quick = qsort(li)
                theTime += time.time() - start
            content = str(increase) + "|" +  str(theTime/1000)
            print "%s" % (content)
            writer.writerow(content.split("|"))
    finally:
        f.close()

def RunBestBubble(i):
    global li
    f = open('PyOuputBubBest.csv', 'wt')
    theTime = 0  
    try:
        writer = csv.writer(f)
        writer.writerow( ('n', 'Time') )
        for increase in range (0, i, 10):
            for j in range(0, 1000):
                backwards(increase)
                start = time.time()
                bubble = BubbleSort()
                theTime += time.time() - start
            content = str(increase) + "|" +  str(theTime/1000)
            print "%s" % (content)
            writer.writerow(content.split("|"))
    finally:
        f.close()

def RunAvBubble(i):
    f = open('PyOuputBub.csv', 'wt')
    theTime = 0
    try:
        writer = csv.writer(f)
        writer.writerow( ('n', 'Time') )
        for increase in range (0, i, 10):
            for j in range(0, 1000):
                random(increase)
                start = time.time()
                bubble = BubbleSort()
                theTime += time.time() - start
            content = str(increase) + "|" +  str(theTime/1000)
            print "%s" % (content)
            writer.writerow(content.split("|"))
    finally:
        f.close()    

def RunWorstBubble(i):
    global li
    f = open('PyOuputBubWors.csv', 'wt')
    theTime = 0  
    try:
        writer = csv.writer(f)
        writer.writerow( ('n', 'Time') )
        for increase in range (0, i, 10):
            for j in range(0, 1000):
                sort(increase)
                start = time.time()
                bubble = BubbleSort()
                theTime += time.time() - start
            content = str(increase) + "|" +  str(theTime/1000)
            print "%s" % (content)
            writer.writerow(content.split("|"))
    finally:
        f.close() 
