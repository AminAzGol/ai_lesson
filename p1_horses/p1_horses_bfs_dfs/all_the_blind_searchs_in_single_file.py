from time import time

def bfs():
    print("bfs started ...")
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
        # visited.append(curr_node)
        childes = successor(curr_node, n, m)
        for child in childes:
            if check_visited_bfs(child, visited):
            # if child in visited:
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


def check_visited_bfs(node, visited):
    for n in visited:
        if node.score < n.score:
            return False
        elif node == n:
            return True
    return False


goal_horses = []
start_horses = []
w = None
h = None


def dfs():
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
            if check_visited_dfs(child):
                continue
            if child == goal:
                return 'found', child
            fringe.append(child)


def check_visited_dfs(node):
    n = node
    while n.parent:
        if node == n.parent:
            return True
        n = n.parent
    return False


def ids():
    k = 0
    while 1:
        k += 1
        msg, finalNode = dfs_for_ids(k)
        if msg == 'found':
            return msg, finalNode


def dfs_for_ids(cutoff):
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
            if check_visited_dfs(child):
                continue
            if child == goal:
                return 'found', child
            if child.dept <= cutoff:
                fringe.append(child)


def successor(father, m, n):
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
                            new_child.horses.sort()
                            new_child.parent = father
                            new_child.dept = father.dept + 1
                            if new_child not in childes:
                                    childes.append(new_child)
                                    new_child.calc_score()
    return childes


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
                newHorse = Horse(horse.x + x_c, horse.y + y_c)
                if newHorse in horses:
                    return False
    return True


def read_data_from_usr():
    global w, h
    algo = input("Select the algorithm ( 1: BFS | 2: DFS | anything-else: IDS) \n")
    if algo == '1':
        print("You have selected BFS")
    elif algo == '2':
        print("You have selected DFS")
    else:
        print("You have selected IDS")

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


class Node:
    parent = None
    horses = None
    score = 0

    def __init__(self, parent, horses, dept=0):
        self.parent = parent
        self.horses = horses
        self.dept = dept

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
        return self.score < other.score

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
tic = time()
msg, finalNode = None, None
if algo == '1':
    msg, finalNode = bfs()
elif algo == '2':
    msg, finalNode = dfs()
else:
    msg, finalNode = ids()

if msg == 'failure':
    print("failure")
    exit()
show_output(finalNode)
toc = time()
print(str(toc - tic))
