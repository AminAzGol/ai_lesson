from math import inf
from game_info import read_data_from_usr,getGoal, get_width_height, getStart
from successor import successor
from show_output import show_output
from time import time


def recursive_best_first_search():
    print("rbfs started...")
    initial_node = getStart()
    goal = getGoal()
    return rbfs(initial_node, goal, inf)


def rbfs(node, goal, f_limit):
    n, m = get_width_height()
    if node == goal:
        return 'found', node

    childes = successor(node, n, m, goal)
    if not childes:
        return 'failure', inf
    for child in childes:
        child.cost = max(child.cost, node.cost)
    while 1:
        def what_is_best(some_node):
            return some_node.cost
        childes.sort(key=what_is_best)
        best = childes[0]
        if best.cost > f_limit:
            return 'failure', best.cost
        alternative = childes[1]
        result, node_or_new_cost = rbfs(best, goal, min(f_limit, alternative.cost))
        if result != 'failure':
            return result, node_or_new_cost
        best.cost = node_or_new_cost


read_data_from_usr()
tic = time()
msg, finalNode = recursive_best_first_search()
if msg == 'failure':
    print("failure")
    exit()
show_output(finalNode)
toc = time()
print(str(toc - tic))