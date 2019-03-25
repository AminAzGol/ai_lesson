from game_info import getStart, getGoal, get_width_height, read_data_from_usr
from time import time
from show_output import show_output
from successor import successor


# @profile
def bfs():
    print("bfs started ...")
    start_node = getStart()
    goal = getGoal()
    n, m = get_width_height()
    if start_node == goal:
        return 'found', start_node
    fringe = [start_node]
    gen = 0
    visited = []
    while 1:
        if not fringe:
            return 'failure', None
        curr_node = fringe.pop()
        add_to_visited(curr_node, visited)
        childes = successor(curr_node, n, m)
        for child in childes:
            if check_visited(child, visited):
                continue
            if child == goal:
                return 'found', child
            fringe.insert(0, child)


def add_to_visited(node, visited):
    for i in range(0, len(visited)):
        if visited[i].score <= node.score:
            visited.insert(i, node)
            return
    visited.insert(-1, node)

def check_visited(node, visited):
    for n in visited:
        if node.score < n.score:
            return False
        elif node == n:
            return True
    return False


read_data_from_usr()
tic = time()
msg, finalNode = bfs()
if msg == 'failure':
    print("failure")
    exit()
show_output(finalNode)
toc = time()
print(str(toc - tic))
