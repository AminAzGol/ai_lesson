from game_info import getStart, getGoal, get_width_height, read_data_from_usr
from time import time
from show_output import show_output
from successor import successor
from bisect import bisect_left
from check_visited import add_to_visited,check_visited


def gbfs():
    print("gbfs started ...")
    start_node = getStart()
    start_node.calc_score()
    goal = getGoal()
    n, m = get_width_height()
    if start_node == goal:
        return 'found', start_node
    fringe = [start_node]
    visited = []
    while 1:
        if not fringe:
            return 'failure', None
        curr_node = fringe.pop(0)
        if check_visited(curr_node, visited):
        # if curr_node in visited:
            continue
        if curr_node == goal:
            print( len(visited) )
            return 'found', curr_node
        add_to_visited(curr_node, visited)
        if len(visited) % 1000 == 0:
            print(f"visited: {len(visited)}")
            print(f"fringe: {len(fringe)}")
        # visited.append(curr_node)
        childes = successor(curr_node, n, m, goal)
        for child in childes:
            add_to_fringe2(child, fringe)


def add_to_fringe(node, fringe):
    for i in range(0, len(fringe)):
        if fringe[i].cost >= node.cost:
            fringe.insert(i, node)
            return
    fringe.append(node)


def add_to_fringe2(node, fringe):
    if not fringe:
        fringe.insert(0, node)
        return
    last = fringe[len(fringe) - 1]
    first = fringe[0]
    if last.cost < node.cost:
        fringe.append(node)
    elif first.cost > node.cost:
        fringe.insert(0, node)
    else:
        add_to_visited_rec(node, fringe, 0, len(fringe) - 1)


def add_to_visited_rec(node, fringe, first, last):
    if fringe[last].cost < node.cost < fringe[first].cost:
        return False
    if last - first == 1 or 0 == last - first:
        fringe.insert(last, node)
        return 1
    mid = int((first + last) / 2)
    if fringe[mid].cost == node.cost:
        fringe.insert(mid, node)
    else:
        if node.cost < fringe[mid].cost:
            add_to_visited_rec(node, fringe, first, mid)
        else:
            add_to_visited_rec(node, fringe, mid, last)

read_data_from_usr()
tic = time()
msg, finalNode = gbfs()
if msg == 'failure':
    print("failure")
    exit()
show_output(finalNode)
toc = time()
print(str(toc - tic))
