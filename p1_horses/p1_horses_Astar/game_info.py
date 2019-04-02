from horse import Horse
from node import Node

goal_horses = []
start_horses = []
w = None
h = None
def read_data_from_usr():
    global w, h
    a = input("give me the map \n")
    [width, height] = a.split(' ')
    w = int(width)
    h = int(height)
    print()
    for i in range(0, int(w)):
        b = input()
        b = b.split()
        for j in range(0, int(h)):
            if b[j] == '#':
                start_horses.append(Horse(i, j))

    for i in range(0, int(w)):
        b = input()
        b = b.split()
        for j in range(0, int(h)):
            if b[j] == '#':
                goal_horses.append(Horse(i, j))


def getStart():
    return Node(None, start_horses)
def testStart():
    return Node(None, [
        Horse(0, 2),
        Horse(1, 3),
        Horse(4, 4)
    ])
def getGoal():
    return Node(None, goal_horses)

def get_width_height():
    return w, h