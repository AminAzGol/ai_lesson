from game_info import getStart, getGoal, get_width_height,testStart,read_data_from_usr
from time  import time

from profiling import profile
from show_output import show_output
from successor import successor

@profile
def dfs():
    print("dfs started ...")
    startNode = getStart()
    goal = getGoal()
    n, m = get_width_height()
    if startNode == goal:
        return 'found', startNode
    fringe = [startNode]
    gen = 0
    while 1:
        if not fringe:
            return 'failure', None
        curr_node = fringe.pop()
        childes = successor(curr_node, n, m)
        gen += 1
        print("generation: " + str(gen))

        for child in childes:
            if check_visited(child):
                continue
            if child == goal:
                return 'found', child
            fringe.append(child)


def check_visited(node):
    n = node
    while n.parent:
        if node == n.parent:
            return True
        n = n.parent
    return False


read_data_from_usr()
tic = time()
msg, finalNode = dfs()
if msg == 'failure':
    print("failure")
    exit()
show_output(finalNode)
toc = time()
print(str(toc - tic))
