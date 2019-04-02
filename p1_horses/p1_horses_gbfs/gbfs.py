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
        curr_node = fringe.pop()
        # a = check_visited(curr_node, visited)
        if curr_node in visited:
            continue
        if curr_node == goal:
            return 'found', curr_node
        # add_to_visited(curr_node, visited)
        visited.append(curr_node)
        childes = successor(curr_node, n, m, goal)
        for child in childes:
            add_to_fringe(child, fringe)


def add_to_fringe(node, fringe):
    for i in range(0, len(fringe)):
        if fringe[i].cost >= node.cost:
            fringe.insert(i, node)
            return
    fringe.append(node)


def add_visited2(node, visited):
    for i in range(0, len(visited)):
        if visited[i].score >= node.score:
            visited.insert(i, node)
            return
    visited.insert(-1, node)


def check_visited2(node, visited):
    # if not visited:
    #     return False
    # return find_rec(visited, node, 0, len(visited) - 1)
    for i in range(0, len(visited)):
        if visited[i].score > node.score:
            return False
        elif visited[i] == node:
            return True
    return False


def find_rec(the_list, element, start, end):
    if the_list[start] == element or the_list[end] == element:
        return True
    elif start == end:
        return False
    else:
        mid = int((start + end) / 2)
        if the_list[mid] == element:
            return True
        elif the_list[mid].score > element.score:
            find_rec(the_list, element, start, mid)
        else:
            find_rec(the_list, element, mid, end)


read_data_from_usr()
tic = time()
msg, finalNode = gbfs()
if msg == 'failure':
    print("failure")
    exit()
show_output(finalNode)
toc = time()
print(str(toc - tic))
