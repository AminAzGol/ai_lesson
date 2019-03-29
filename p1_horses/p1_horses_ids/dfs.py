from game_info import getStart, getGoal, get_width_height, read_data_from_usr
from time  import time

from profiling import profile
from show_output import show_output
from successor import successor


def dfs(cutoff):
    print("dfs started ...")
    startNode = getStart()
    goal = getGoal()
    n, m = get_width_height()
    if startNode == goal:
        return 'found', startNode
    fringe = [startNode]
    while 1:
        if not fringe:
            return 'failure', None
        curr_node = fringe.pop()
        childes = successor(curr_node, n, m)

        for child in childes:
            if check_visited(child):
                continue
            if child == goal:
                return 'found', child
            if child.dept <= cutoff:
                fringe.append(child)


def check_visited(node):
    n = node
    while n.parent:
        if node == n.parent:
            return True
        n = n.parent
    return False
