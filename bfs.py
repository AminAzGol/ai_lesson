from game_info import getStart, getGoal, get_width_height,testStart,read_data_from_usr
from fringe import Fringe
from horse import Horse
from time  import time
from show_output import show_output
from successor import successor
def bfs():
    print("bfs started ...")
    startNode = getStart()
    goal = getGoal()
    n, m = get_width_height()
    if(startNode == goal):
        return 'found', startNode
    fringe = [startNode]
    gen = 0
    visited = []
    while(1):
        if not fringe:
            return 'failure', None
        curr_node = fringe.pop()
        visited.append(curr_node)
        childes = successor(curr_node, n, m)
        gen += 1
        if gen % 1000 == 0:
            print("generation: " + str(gen))
        for child in childes:
            if child in visited:
                continue
            if child == goal:
                return 'found', child
            fringe.insert(0,child)

read_data_from_usr()
tic = time()
msg, finalNode = bfs()
if msg == 'failure':
    print("failure")
    exit()
show_output(finalNode)
toc = time()
print(str(toc - tic))
