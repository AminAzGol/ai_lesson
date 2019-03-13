from horse import Horse
from node  import Node
def successor(father, m, n):
    childs = []
    for h in father.horses:
        for i in range(0, 3):
            'first move'
            '0:top 1:right 2:bottom 3:left'
            for j in [-1, 1]:
                for t in [1, 2]:
                    yCoef = None
                    xCoef = None
                    if (i == 0):
                        yCoef = 1
                        xCoef = j
                    if (i == 1):
                        xCoef = 1
                        yCoef = j
                    if(i == 2):
                        yCoef = -1
                        xCoef = j
                    if i == 3:
                        xCoef = -1
                        yCoef = j
                    if t == 1:
                        yCoef *= 2
                    else:
                        xCoef *= 2
                    newHorse = Horse(h.x + xCoef, h.y + yCoef)
                    if checkForBound(newHorse, m, n):
                        newChild = Node(None,None)
                        newChild.horses = father.horses.copy()
                        newChild.horses.remove(h)
                        newChild.horses.append(newHorse)
                        newChild.parent = father
                        if newChild not in childs:
                            if checkForTreat(newHorse,newChild.horses):
                                childs.append(newChild)
    return childs

def checkForBound(horse, m, n):
    'bargam'
    return m > horse.x >= 0 <= horse.y < n

# def checkForTreat(horses):
#     for h in horses:
#         for h2 in horses:
#             if h != h2 and h.x == h2.x and h.y == h2.y:
#                 return False
#             return True
def checkForTreat(horse, horses):
    for i in range(0, 3):
        'first move'
        '0:top 1:right 2:bottom 3:left'
        for j in [-1, 1]:
            for t in [1, 2]:
                yCoef = None
                xCoef = None
                if i == 0:
                    yCoef = 1
                    xCoef = j
                if (i == 1):
                    xCoef = 1
                    yCoef = j
                if (i == 2):
                    yCoef = -1
                    xCoef = j
                if i == 3:
                    xCoef = -1
                    yCoef = j
                if t == 1:
                    yCoef *= 2
                else:
                    xCoef *= 2
                newHorse = Horse(horse.x + xCoef, horse.y + yCoef)

                if newHorse in horses:
                    return False
    return True