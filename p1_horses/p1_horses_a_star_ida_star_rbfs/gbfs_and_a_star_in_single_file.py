from time import time


def a_star():
    print("a_star started ...")
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
            continue
        if curr_node == goal:
            print( len(visited) )
            return 'found', curr_node
        add_to_visited(curr_node, visited)
        if len(visited) % 1000 == 0:
            print(f"visited: {len(visited)}")
            print(f"fringe: {len(fringe)}")
        childes = successor(curr_node, n, m, goal)
        for child in childes:
            add_to_fringe(child, fringe)


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
            add_to_fringe(child, fringe)


def successor(father, m, n, goal):
    childes = []
    for h in father.horses:
        for i in [-1, 1]:
            'first move'
            '0:top 2:bottom'
            for j in [-1, 1]:
                for t in [1, 2]:
                    y_c = i
                    x_c = j
                    if t == 1:
                        y_c *= 2
                    else:
                        x_c *= 2
                    new_horse = Horse(h.x + x_c, h.y + y_c)
                    if check_for_bound(new_horse, m, n):
                        new_child = Node(None, None)
                        new_child.horses = father.horses.copy()
                        new_child.horses.remove(h)
                        if check_for_treat(new_horse, new_child.horses):
                            new_child.horses.append(new_horse)
                            new_child.parent = father
                            if new_child not in childes:
                                childes.append(new_child)
                                new_child.calc_score()
                                new_child.dept = new_child.parent.dept + 1
                                if algo == '1':
                                    new_child.cost = evaluation_gbfs(new_child, goal)
                                else:
                                    new_child.cost = evaluation_a_star(new_child, goal)

    return childes


def evaluation_a_star(node, goal):
    return hirustic(node, goal) + node.parent.dept + 1


def evaluation_gbfs(node, goal):
    return hirustic(node, goal)

def hirustic(node, goal):
    h = 0
    unassigned_horses = node.horses.copy()
    assigned = [None] * len(node.horses)
    assigned_costs = [10000] * len(node.horses)
    while unassigned_horses:
        horse = unassigned_horses.pop(0)
        cost_list = [None] * len(goal.horses)
        for i in range(0, len(goal.horses)):
            goal_horse = goal.horses[i]
            cost = abs(horse.x - goal_horse.x) + abs(horse.y - goal_horse.y)
            cost_list[i] = cost
        while 1:
            min_cost = min(cost_list)
            min_index = cost_list.index(min_cost)
            if assigned[min_index]:
                if assigned_costs[min_index] > min_cost:
                    unassigned_horses.append(assigned[min_index])
                    assigned[min_index] = horse
                    assigned_costs[min_index] = min_cost
                    break
                else:
                    cost_list[min_index] = 10000
            else:
                assigned[min_index] = horse
                assigned_costs[min_index] = min_cost
                break
    tot = 0
    for a in assigned_costs:
        if a == 0:
            continue
        b = a / 3
        if b < 1:
            b += 2
        tot += b
    return tot


def check_for_bound(horse, m, n):
    ret = m > horse.x >= 0 <= horse.y < n
    return ret


def check_for_treat(horse, horses):
    for i in [-1, 1]:
        for j in [-1, 1]:
            for t in [1, 2]:
                y_c = i
                x_c = j
                if t == 1:
                    y_c *= 2
                else:
                    x_c *= 2
                new_horse = Horse(horse.x + x_c, horse.y + y_c)
                if new_horse in horses:
                    return False
    return True



def add_to_fringe(node, fringe):
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
        add_to_fringe_rec(node, fringe, 0, len(fringe) - 1)


def add_to_fringe_rec(node, fringe, first, last):
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
            add_to_fringe_rec(node, fringe, first, mid)
        else:
            add_to_fringe_rec(node, fringe, mid, last)


def add_to_visited(node, visited):
    if not visited:
        visited.insert(0, node)
        return
    last = visited[len(visited) - 1]
    first = visited[0]
    if last.score < node.score:
        visited.append(node)
    elif first.score > node.score:
        visited.insert(0, node)
    else:
        add_to_visited_rec(node, visited, 0, len(visited) - 1)


def add_to_visited_rec(node, visited, first, last):
    if visited[last].score < node.score < visited[first].score:
        return False
    if last - first == 1 or 0 == last - first:
        visited.insert(last, node)
        return 1
    mid = int((first + last) / 2)
    if visited[mid].score == node.score:
        visited.insert(mid, node)
    else:
        if node.score < visited[mid].score:
            add_to_visited_rec(node, visited, first, mid)
        else:
            add_to_visited_rec(node, visited, mid, last)


def check_visited(node, visited):
    if not visited:
        return False
    else:
        return check_visited_rec(node, visited, 0, len(visited) - 1)


def check_visited_rec(node, visited, first, last):
    if visited[last].score < node.score < visited[first].score:
        return False
    if last - first == 1 or 0 == last - first:
        a = visited[first] == node or visited[last] == node
        if a:
            return True
        else:
            return False
    mid = int((first + last) / 2)
    if visited[mid] == node:
        return True
    else:
        ret = False
        if visited[mid].score <= node.score:
            ret = check_visited_rec(node, visited, mid, last)
        if visited[mid].score >= node.score and not ret:
            ret = check_visited_rec(node, visited, first, mid)
        return ret


goal_horses = []
start_horses = []
w = None
h = None


def read_data_from_usr():
    global w, h
    algo = input("Select the algorithm: ( 1: GBFS | anything-else: A* ) \n")
    if algo == '1':
        print("You have selected GBFS")
    else:
        print("You have selected A*")
    try:
        a = input("give me the map \n")
        [width, height] = a.split(' ')
        w = int(width)
        h = int(height)
        print()
        for i in range(0, int(w)):
            b = input()
            b = b.split()
            for j in range(0, int(h)):
                if b[j] == '#':
                    start_horses.append(Horse(i, j))

        for i in range(0, int(w)):
            b = input()
            b = b.split()
            for j in range(0, int(h)):
                if b[j] == '#':
                    goal_horses.append(Horse(i, j))
    except:
        print("ERROR: Wrong input format try again \n")
        return read_data_from_usr()

    return algo

def getStart():
    return Node(None, start_horses)
def testStart():
    return Node(None, [
        Horse(0, 2),
        Horse(1, 3),
        Horse(4, 4)
    ])
def getGoal():
    return Node(None, goal_horses)

def get_width_height():
    return w, h

class Horse:
    x = -1
    y = -1

    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __eq__(self, other):
        return other.x == self.x and self.y == other.y

    def __str__(self):
        return str(self.x + 1) + ' ' + str(self.y + 1)

    def __repr__(self):
        return '(' + str(self.x) + ' ' + str(self.y) + ')'

    def __lt__(self, other):
        if self.y < other.y:
            return True
        elif self.y == other.y:
            return self.x < other.x
        return False


import game_info


class Node:
    parent = None
    horses = None
    score = 0
    cost = 0
    dept = 0

    def __init__(self, parent, horses):
        self.parent = parent
        self.horses = horses

    def __repr__(self):
        return str(self.horses)

    def __eq__(self, other):
        for h in self.horses:
            if h not in other.horses:
                return False
        return True

    def __hash__(self):
        return hash(self.horses)

    def __lt__(self, other):
        return self.cost < other.cost

    def calc_score(self):
        width, height = get_width_height()
        m = 0
        for h in self.horses:
            m += h.x + h.y * height
        self.score = m


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
    for i in range(0, n):
        s = ''
        for j in range(0, m):
            if Horse(i, j) in horses:
                s += '#'
            else:
                s += '.'
        print(s)
    print()


algo = read_data_from_usr()
msg, finalNode = None, None
tic = time()
if algo == '1':
    msg, finalNode = gbfs()
else:
    msg, finalNode = a_star()
if msg == 'failure':
    print("failure")
    exit()
show_output(finalNode)
toc = time()
print(str(toc - tic))
