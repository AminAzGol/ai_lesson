from game_info import getStart, getGoal, get_width_height, read_data_from_usr
from time import time
from show_output import show_output
from successor import successor
from check_visited import check_visited, add_to_visited


def gbfs():
    print("gbfs started ...")
    start_node = getStart()
    goal = getGoal()
    n, m = get_width_height()
    if start_node == goal:
        return 'found', start_node
    fringe = [start_node]
    visited = []
    while 1:
        if not fringe:
            return 'failure', None
        curr_node = fringe.pop()
        add_to_visited(curr_node, visited)
        childes = successor(curr_node, n, m, goal)
        for child in childes:
            a = check_visited(child, visited)
            if a:
                continue
            if child == goal:
                return 'found', child
            fringe.insert(0, child)


def add_to_fringe(node, fringe):
    for i in range(0, len(fringe)):
        if fringe[i].cost <= node.cost:
            fringe.insert(i, node)
            return
    fringe.insert(-1, node)


read_data_from_usr()
tic = time()
msg, finalNode = gbfs()
if msg == 'failure':
    print("failure")
    exit()
show_output(finalNode)
toc = time()
print(str(toc - tic))
