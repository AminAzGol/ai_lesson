from game_info import getStart, getGoal, get_width_height, read_data_from_usr
from time import time
from show_output import show_output
from successor import successor
from dfs import dfs
from profiling import profile


@profile
def ids():
    k = 0
    while 1:
        k += 1
        msg, finalNode = dfs(k)
        if msg == 'found':
            return msg, finalNode


read_data_from_usr()
tic = time()
msg, finalNode = ids()
if msg == 'failure':
    print("failure")
    exit()
show_output(finalNode)
toc = time()
print(str(toc - tic))
