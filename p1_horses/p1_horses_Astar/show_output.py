from game_info import get_width_height, getStart, getGoal
from horse import Horse
def show_output(finalNode):
    n, m = get_width_height()
    print(str(n) + ' ' + str(m))
    print()
    print_map(getStart().horses)
    print_map(getGoal().horses)
    node = finalNode
    i = 0
    while node.parent:
        i += 1
        node = node.parent
    print(i)
    node = finalNode
    s = ''
    while node.parent:
        parent_diff = diff(node.parent.horses, node.horses)
        child_diff = diff(node.horses, node.parent.horses)
        s = str(parent_diff[0]) + " " + str(child_diff[0]) + '\n' + s
        node = node.parent
    print(s)
def diff(li1, li2):
    li_dif = [i for i in li1 if i not in li2]
    return li_dif

def print_map(horses):
    n, m = get_width_height()
    for i in range(0, m):
        s = ''
        for j in range(0, n):
            if Horse(i, j) in horses:
                s += '#'
            else:
                s += '.'
        print(s)
    print()