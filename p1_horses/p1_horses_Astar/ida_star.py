from game_info import getStart, getGoal, get_width_height, read_data_from_usr
from time import time
from show_output import show_output
from successor import successor
from bisect import bisect_left
from check_visited import add_to_visited,check_visited


def a_star():
    print("a_star started ...")
    start_node = getStart()
    start_node.calc_score()
    goal = getGoal()
    n, m = get_width_height()
    if start_node == goal:
        return 'found', start_node
    cuttoff = 4
    while 1:
        min_cost = 1000000
        visited = []
        fringe = [start_node]
        print(f"cuttoff: {cuttoff}")
        while 1:
            if not fringe:
                cuttoff = min_cost
                break
            curr_node = fringe.pop(0)
            if curr_node.cost > cuttoff:
                if curr_node.cost < min_cost:
                    min_cost = curr_node.cost
                continue
            if check_visited(curr_node, visited):
                continue
            if curr_node == goal:
                return 'found', curr_node
            add_to_visited(curr_node, visited)
            if len(visited) % 1000 == 0:
                print(f"visited: {len(visited)}")
                print(f"fringe: {len(fringe)}")
            childes = successor(curr_node, n, m, goal)
            for child in childes:
                fringe.insert(0, child)

read_data_from_usr()
tic = time()
msg, finalNode = a_star()
if msg == 'failure':
    print("failure")
    exit()
show_output(finalNode)
toc = time()
print(str(toc - tic))
